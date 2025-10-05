from fastapi import APIRouter, Depends, HTTPException, status, Query
from typing import List, Optional
from app.schemas.quiz import QuizRequest
from app.schemas.common import UnifiedResponse
from app.models.quiz import Quiz
from app.middleware.auth import get_current_active_user, require_role
from app.database import get_db
from app.utils.responses import build_ok_response, build_created_response

router = APIRouter()

@router.post("/", response_model=UnifiedResponse[Quiz])
async def create_quiz(
    quiz_request: QuizRequest,
    current_user: dict = Depends(require_role(["Educator", "Admin"]))
):
    db = get_db()

    existing = db.table("quizzes").select("*").eq("title", quiz_request.title).eq("is_deleted", False).maybeSingle().execute()
    if existing.data:
        raise HTTPException(
            status_code=status.HTTP_409_CONFLICT,
            detail="Quiz with this title already exists"
        )

    quiz_data = {
        **quiz_request.dict(),
        "severity": quiz_request.severity.value,
        "creator_id": current_user["id"]
    }

    response = db.table("quizzes").insert(quiz_data).execute()
    created_quiz = Quiz(**response.data[0])

    return build_created_response("Quiz created successfully", created_quiz)

@router.get("/", response_model=UnifiedResponse[List[Quiz]])
async def get_all_quizzes(
    page: int = Query(0, ge=0),
    size: int = Query(10, ge=1, le=100),
    category_id: Optional[int] = None,
    severity: Optional[str] = None
):
    db = get_db()
    offset = page * size

    query = db.table("quizzes").select("*").eq("is_deleted", False)

    if category_id:
        query = query.eq("category_id", category_id)
    if severity:
        query = query.eq("severity", severity)

    response = query.range(offset, offset + size - 1).execute()

    quizzes = [Quiz(**quiz) for quiz in response.data]
    return build_ok_response("Quizzes retrieved successfully", quizzes)

@router.get("/{quiz_id}", response_model=UnifiedResponse[Quiz])
async def get_quiz_by_id(quiz_id: int):
    db = get_db()
    response = db.table("quizzes").select("*").eq("id", quiz_id).eq("is_deleted", False).maybeSingle().execute()

    if not response.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Quiz not found"
        )

    quiz = Quiz(**response.data)
    return build_ok_response("Quiz retrieved successfully", quiz)

@router.put("/{quiz_id}", response_model=UnifiedResponse[Quiz])
async def update_quiz(
    quiz_id: int,
    quiz_request: QuizRequest,
    current_user: dict = Depends(get_current_active_user)
):
    db = get_db()

    existing = db.table("quizzes").select("*").eq("id", quiz_id).eq("is_deleted", False).maybeSingle().execute()
    if not existing.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Quiz not found"
        )

    if existing.data["creator_id"] != current_user["id"] and current_user["role"] != "Admin":
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Not authorized to update this quiz"
        )

    update_data = {**quiz_request.dict(), "severity": quiz_request.severity.value}
    db.table("quizzes").update(update_data).eq("id", quiz_id).execute()

    updated = db.table("quizzes").select("*").eq("id", quiz_id).maybeSingle().execute()
    quiz = Quiz(**updated.data)

    return build_ok_response("Quiz updated successfully", quiz)

@router.delete("/{quiz_id}", response_model=UnifiedResponse[dict])
async def delete_quiz(
    quiz_id: int,
    current_user: dict = Depends(get_current_active_user)
):
    db = get_db()

    existing = db.table("quizzes").select("*").eq("id", quiz_id).eq("is_deleted", False).maybeSingle().execute()
    if not existing.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Quiz not found"
        )

    if existing.data["creator_id"] != current_user["id"] and current_user["role"] != "Admin":
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Not authorized to delete this quiz"
        )

    db.table("quizzes").update({"is_deleted": True}).eq("id", quiz_id).execute()

    return build_ok_response("Quiz deleted successfully", {"deleted": True})

@router.get("/category/{category_id}", response_model=UnifiedResponse[List[Quiz]])
async def get_quizzes_by_category(
    category_id: int,
    page: int = Query(0, ge=0),
    size: int = Query(10, ge=1, le=100)
):
    db = get_db()
    offset = page * size

    response = db.table("quizzes").select("*").eq("category_id", category_id).eq("is_deleted", False).range(offset, offset + size - 1).execute()

    quizzes = [Quiz(**quiz) for quiz in response.data]
    return build_ok_response("Quizzes retrieved successfully", quizzes)

@router.get("/creator/{creator_id}", response_model=UnifiedResponse[List[Quiz]])
async def get_quizzes_by_creator(
    creator_id: str,
    page: int = Query(0, ge=0),
    size: int = Query(10, ge=1, le=100)
):
    db = get_db()
    offset = page * size

    response = db.table("quizzes").select("*").eq("creator_id", creator_id).eq("is_deleted", False).range(offset, offset + size - 1).execute()

    quizzes = [Quiz(**quiz) for quiz in response.data]
    return build_ok_response("Quizzes retrieved successfully", quizzes)
