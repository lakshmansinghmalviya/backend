from fastapi import APIRouter, Depends, HTTPException, status, Query
from typing import List
from app.schemas.feedback import FeedbackRequest
from app.schemas.common import UnifiedResponse
from app.models.feedback import Feedback
from app.middleware.auth import get_current_active_user
from app.database import get_db
from app.utils.responses import build_ok_response, build_created_response

router = APIRouter()

@router.post("/", response_model=UnifiedResponse[Feedback])
async def create_feedback(
    feedback_request: FeedbackRequest,
    current_user: dict = Depends(get_current_active_user)
):
    db = get_db()

    feedback_data = {
        **feedback_request.dict(),
        "user_id": current_user["id"]
    }

    response = db.table("feedbacks").insert(feedback_data).execute()
    created_feedback = Feedback(**response.data[0])

    return build_created_response("Feedback submitted successfully", created_feedback)

@router.get("/", response_model=UnifiedResponse[List[Feedback]])
async def get_all_feedbacks(
    page: int = Query(0, ge=0),
    size: int = Query(10, ge=1, le=100),
    question_id: int = None
):
    db = get_db()
    offset = page * size

    query = db.table("feedbacks").select("*").eq("is_deleted", False)

    if question_id:
        query = query.eq("question_id", question_id)

    response = query.range(offset, offset + size - 1).execute()

    feedbacks = [Feedback(**f) for f in response.data]
    return build_ok_response("Feedbacks retrieved successfully", feedbacks)

@router.get("/{feedback_id}", response_model=UnifiedResponse[Feedback])
async def get_feedback_by_id(feedback_id: int):
    db = get_db()
    response = db.table("feedbacks").select("*").eq("id", feedback_id).eq("is_deleted", False).maybeSingle().execute()

    if not response.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Feedback not found"
        )

    feedback = Feedback(**response.data)
    return build_ok_response("Feedback retrieved successfully", feedback)

@router.get("/question/{question_id}", response_model=UnifiedResponse[List[Feedback]])
async def get_feedbacks_by_question(
    question_id: int,
    page: int = Query(0, ge=0),
    size: int = Query(10, ge=1, le=100)
):
    db = get_db()
    offset = page * size

    response = db.table("feedbacks").select("*").eq("question_id", question_id).eq("is_deleted", False).range(offset, offset + size - 1).execute()

    feedbacks = [Feedback(**f) for f in response.data]
    return build_ok_response("Feedbacks retrieved successfully", feedbacks)

@router.put("/{feedback_id}", response_model=UnifiedResponse[Feedback])
async def update_feedback(
    feedback_id: int,
    feedback_request: FeedbackRequest,
    current_user: dict = Depends(get_current_active_user)
):
    db = get_db()

    existing = db.table("feedbacks").select("*").eq("id", feedback_id).eq("is_deleted", False).maybeSingle().execute()
    if not existing.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Feedback not found"
        )

    if existing.data["user_id"] != current_user["id"]:
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Not authorized to update this feedback"
        )

    update_data = feedback_request.dict()
    db.table("feedbacks").update(update_data).eq("id", feedback_id).execute()

    updated = db.table("feedbacks").select("*").eq("id", feedback_id).maybeSingle().execute()
    feedback = Feedback(**updated.data)

    return build_ok_response("Feedback updated successfully", feedback)

@router.delete("/{feedback_id}", response_model=UnifiedResponse[dict])
async def delete_feedback(
    feedback_id: int,
    current_user: dict = Depends(get_current_active_user)
):
    db = get_db()

    existing = db.table("feedbacks").select("*").eq("id", feedback_id).eq("is_deleted", False).maybeSingle().execute()
    if not existing.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Feedback not found"
        )

    if existing.data["user_id"] != current_user["id"] and current_user["role"] != "Admin":
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Not authorized to delete this feedback"
        )

    db.table("feedbacks").update({"is_deleted": True}).eq("id", feedback_id).execute()

    return build_ok_response("Feedback deleted successfully", {"deleted": True})
