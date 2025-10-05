from fastapi import APIRouter, Depends, HTTPException, status, Query
from typing import List, Optional
from app.schemas.category import CategoryRequest
from app.schemas.common import UnifiedResponse, PageResponse
from app.models.category import Category
from app.middleware.auth import get_current_active_user, require_role
from app.database import get_db
from app.utils.responses import build_ok_response, build_created_response

router = APIRouter()

@router.post("/", response_model=UnifiedResponse[Category])
async def create_category(
    category_request: CategoryRequest,
    current_user: dict = Depends(require_role(["Educator", "Admin"]))
):
    db = get_db()

    existing = db.table("categories").select("*").eq("name", category_request.name).eq("is_deleted", False).maybeSingle().execute()
    if existing.data:
        raise HTTPException(
            status_code=status.HTTP_409_CONFLICT,
            detail="Category with this name already exists"
        )

    category_data = {
        **category_request.dict(),
        "creator_id": current_user["id"]
    }

    response = db.table("categories").insert(category_data).execute()
    created_category = Category(**response.data[0])

    return build_created_response("Category created successfully", created_category)

@router.get("/", response_model=UnifiedResponse[List[Category]])
async def get_all_categories(
    page: int = Query(0, ge=0),
    size: int = Query(10, ge=1, le=100)
):
    db = get_db()
    offset = page * size

    response = db.table("categories").select("*").eq("is_deleted", False).range(offset, offset + size - 1).execute()

    categories = [Category(**cat) for cat in response.data]
    return build_ok_response("Categories retrieved successfully", categories)

@router.get("/{category_id}", response_model=UnifiedResponse[Category])
async def get_category_by_id(category_id: int):
    db = get_db()
    response = db.table("categories").select("*").eq("id", category_id).eq("is_deleted", False).maybeSingle().execute()

    if not response.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Category not found"
        )

    category = Category(**response.data)
    return build_ok_response("Category retrieved successfully", category)

@router.put("/{category_id}", response_model=UnifiedResponse[Category])
async def update_category(
    category_id: int,
    category_request: CategoryRequest,
    current_user: dict = Depends(get_current_active_user)
):
    db = get_db()

    existing = db.table("categories").select("*").eq("id", category_id).eq("is_deleted", False).maybeSingle().execute()
    if not existing.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Category not found"
        )

    if existing.data["creator_id"] != current_user["id"] and current_user["role"] != "Admin":
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Not authorized to update this category"
        )

    update_data = category_request.dict()
    db.table("categories").update(update_data).eq("id", category_id).execute()

    updated = db.table("categories").select("*").eq("id", category_id).maybeSingle().execute()
    category = Category(**updated.data)

    return build_ok_response("Category updated successfully", category)

@router.delete("/{category_id}", response_model=UnifiedResponse[dict])
async def delete_category(
    category_id: int,
    current_user: dict = Depends(get_current_active_user)
):
    db = get_db()

    existing = db.table("categories").select("*").eq("id", category_id).eq("is_deleted", False).maybeSingle().execute()
    if not existing.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Category not found"
        )

    if existing.data["creator_id"] != current_user["id"] and current_user["role"] != "Admin":
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Not authorized to delete this category"
        )

    db.table("categories").update({"is_deleted": True}).eq("id", category_id).execute()

    return build_ok_response("Category deleted successfully", {"deleted": True})
