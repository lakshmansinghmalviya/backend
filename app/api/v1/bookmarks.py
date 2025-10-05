from fastapi import APIRouter, Depends, HTTPException, status, Query
from typing import List
from app.schemas.bookmark import BookmarkRequest
from app.schemas.common import UnifiedResponse
from app.models.bookmark import Bookmark
from app.middleware.auth import get_current_active_user
from app.database import get_db
from app.utils.responses import build_ok_response, build_created_response

router = APIRouter()

@router.post("/", response_model=UnifiedResponse[Bookmark])
async def create_bookmark(
    bookmark_request: BookmarkRequest,
    current_user: dict = Depends(get_current_active_user)
):
    db = get_db()

    existing = db.table("bookmarks").select("*").eq("user_id", current_user["id"]).eq("quiz_id", bookmark_request.quiz_id).eq("is_deleted", False).maybeSingle().execute()
    if existing.data:
        raise HTTPException(
            status_code=status.HTTP_409_CONFLICT,
            detail="Quiz already bookmarked"
        )

    bookmark_data = {
        **bookmark_request.dict(),
        "user_id": current_user["id"]
    }

    response = db.table("bookmarks").insert(bookmark_data).execute()
    created_bookmark = Bookmark(**response.data[0])

    return build_created_response("Bookmark added successfully", created_bookmark)

@router.get("/", response_model=UnifiedResponse[List[Bookmark]])
async def get_user_bookmarks(
    current_user: dict = Depends(get_current_active_user),
    page: int = Query(0, ge=0),
    size: int = Query(10, ge=1, le=100)
):
    db = get_db()
    offset = page * size

    response = db.table("bookmarks").select("*").eq("user_id", current_user["id"]).eq("is_deleted", False).range(offset, offset + size - 1).execute()

    bookmarks = [Bookmark(**b) for b in response.data]
    return build_ok_response("Bookmarks retrieved successfully", bookmarks)

@router.get("/{bookmark_id}", response_model=UnifiedResponse[Bookmark])
async def get_bookmark_by_id(
    bookmark_id: int,
    current_user: dict = Depends(get_current_active_user)
):
    db = get_db()
    response = db.table("bookmarks").select("*").eq("id", bookmark_id).eq("is_deleted", False).maybeSingle().execute()

    if not response.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Bookmark not found"
        )

    if response.data["user_id"] != current_user["id"]:
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Not authorized to view this bookmark"
        )

    bookmark = Bookmark(**response.data)
    return build_ok_response("Bookmark retrieved successfully", bookmark)

@router.delete("/{bookmark_id}", response_model=UnifiedResponse[dict])
async def delete_bookmark(
    bookmark_id: int,
    current_user: dict = Depends(get_current_active_user)
):
    db = get_db()

    existing = db.table("bookmarks").select("*").eq("id", bookmark_id).eq("is_deleted", False).maybeSingle().execute()
    if not existing.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Bookmark not found"
        )

    if existing.data["user_id"] != current_user["id"]:
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Not authorized to delete this bookmark"
        )

    db.table("bookmarks").update({"is_deleted": True}).eq("id", bookmark_id).execute()

    return build_ok_response("Bookmark removed successfully", {"deleted": True})

@router.delete("/quiz/{quiz_id}", response_model=UnifiedResponse[dict])
async def delete_bookmark_by_quiz(
    quiz_id: int,
    current_user: dict = Depends(get_current_active_user)
):
    db = get_db()

    existing = db.table("bookmarks").select("*").eq("user_id", current_user["id"]).eq("quiz_id", quiz_id).eq("is_deleted", False).maybeSingle().execute()
    if not existing.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Bookmark not found"
        )

    db.table("bookmarks").update({"is_deleted": True}).eq("user_id", current_user["id"]).eq("quiz_id", quiz_id).execute()

    return build_ok_response("Bookmark removed successfully", {"deleted": True})
