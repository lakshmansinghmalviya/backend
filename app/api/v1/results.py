from fastapi import APIRouter, Depends, HTTPException, status, Query
from typing import List
from app.schemas.result import ResultRequest
from app.schemas.common import UnifiedResponse
from app.models.result import Result
from app.middleware.auth import get_current_active_user
from app.database import get_db
from app.utils.responses import build_ok_response, build_created_response

router = APIRouter()

@router.post("/", response_model=UnifiedResponse[Result])
async def create_result(
    result_request: ResultRequest,
    current_user: dict = Depends(get_current_active_user)
):
    db = get_db()

    result_data = {
        **result_request.dict(),
        "user_id": current_user["id"]
    }

    response = db.table("results").insert(result_data).execute()
    created_result = Result(**response.data[0])

    return build_created_response("Result saved successfully", created_result)

@router.get("/", response_model=UnifiedResponse[List[Result]])
async def get_user_results(
    current_user: dict = Depends(get_current_active_user),
    page: int = Query(0, ge=0),
    size: int = Query(10, ge=1, le=100)
):
    db = get_db()
    offset = page * size

    response = db.table("results").select("*").eq("user_id", current_user["id"]).eq("is_deleted", False).range(offset, offset + size - 1).execute()

    results = [Result(**r) for r in response.data]
    return build_ok_response("Results retrieved successfully", results)

@router.get("/{result_id}", response_model=UnifiedResponse[Result])
async def get_result_by_id(
    result_id: int,
    current_user: dict = Depends(get_current_active_user)
):
    db = get_db()
    response = db.table("results").select("*").eq("id", result_id).eq("is_deleted", False).maybeSingle().execute()

    if not response.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Result not found"
        )

    if response.data["user_id"] != current_user["id"] and current_user["role"] not in ["Educator", "Admin"]:
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Not authorized to view this result"
        )

    result = Result(**response.data)
    return build_ok_response("Result retrieved successfully", result)

@router.get("/quiz/{quiz_id}", response_model=UnifiedResponse[List[Result]])
async def get_results_by_quiz(
    quiz_id: int,
    current_user: dict = Depends(get_current_active_user),
    page: int = Query(0, ge=0),
    size: int = Query(10, ge=1, le=100)
):
    db = get_db()
    offset = page * size

    response = db.table("results").select("*").eq("quiz_id", quiz_id).eq("user_id", current_user["id"]).eq("is_deleted", False).range(offset, offset + size - 1).execute()

    results = [Result(**r) for r in response.data]
    return build_ok_response("Results retrieved successfully", results)

@router.put("/{result_id}", response_model=UnifiedResponse[Result])
async def update_result(
    result_id: int,
    result_request: ResultRequest,
    current_user: dict = Depends(get_current_active_user)
):
    db = get_db()

    existing = db.table("results").select("*").eq("id", result_id).eq("is_deleted", False).maybeSingle().execute()
    if not existing.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Result not found"
        )

    if existing.data["user_id"] != current_user["id"]:
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Not authorized to update this result"
        )

    update_data = result_request.dict()
    db.table("results").update(update_data).eq("id", result_id).execute()

    updated = db.table("results").select("*").eq("id", result_id).maybeSingle().execute()
    result = Result(**updated.data)

    return build_ok_response("Result updated successfully", result)
