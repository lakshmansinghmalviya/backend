from fastapi import APIRouter, Depends, HTTPException, status, Query
from typing import List
from app.schemas.question import QuestionRequest
from app.schemas.option import OptionRequest
from app.schemas.common import UnifiedResponse
from app.models.question import Question
from app.models.option import Option
from app.middleware.auth import get_current_active_user, require_role
from app.database import get_db
from app.utils.responses import build_ok_response, build_created_response

router = APIRouter()

@router.post("/", response_model=UnifiedResponse[Question])
async def create_question(
    question_request: QuestionRequest,
    current_user: dict = Depends(require_role(["Educator", "Admin"]))
):
    db = get_db()

    existing = db.table("questions").select("*").eq("text", question_request.text).eq("is_deleted", False).maybeSingle().execute()
    if existing.data:
        raise HTTPException(
            status_code=status.HTTP_409_CONFLICT,
            detail="Question with this text already exists"
        )

    question_data = {
        **question_request.dict(),
        "creator_id": current_user["id"]
    }

    response = db.table("questions").insert(question_data).execute()
    created_question = Question(**response.data[0])

    return build_created_response("Question created successfully", created_question)

@router.get("/", response_model=UnifiedResponse[List[Question]])
async def get_all_questions(
    page: int = Query(0, ge=0),
    size: int = Query(10, ge=1, le=100),
    quiz_id: int = None
):
    db = get_db()
    offset = page * size

    query = db.table("questions").select("*").eq("is_deleted", False)

    if quiz_id:
        query = query.eq("quiz_id", quiz_id)

    response = query.range(offset, offset + size - 1).execute()

    questions = [Question(**q) for q in response.data]
    return build_ok_response("Questions retrieved successfully", questions)

@router.get("/{question_id}", response_model=UnifiedResponse[Question])
async def get_question_by_id(question_id: int):
    db = get_db()
    response = db.table("questions").select("*").eq("id", question_id).eq("is_deleted", False).maybeSingle().execute()

    if not response.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Question not found"
        )

    question = Question(**response.data)
    return build_ok_response("Question retrieved successfully", question)

@router.get("/{question_id}/options", response_model=UnifiedResponse[List[Option]])
async def get_question_options(question_id: int):
    db = get_db()

    question = db.table("questions").select("*").eq("id", question_id).eq("is_deleted", False).maybeSingle().execute()
    if not question.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Question not found"
        )

    response = db.table("options").select("*").eq("question_id", question_id).eq("is_deleted", False).execute()

    options = [Option(**opt) for opt in response.data]
    return build_ok_response("Options retrieved successfully", options)

@router.post("/{question_id}/options", response_model=UnifiedResponse[Option])
async def add_option_to_question(
    question_id: int,
    option_request: OptionRequest,
    current_user: dict = Depends(require_role(["Educator", "Admin"]))
):
    db = get_db()

    question = db.table("questions").select("*").eq("id", question_id).eq("is_deleted", False).maybeSingle().execute()
    if not question.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Question not found"
        )

    if question.data["creator_id"] != current_user["id"] and current_user["role"] != "Admin":
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Not authorized to add options to this question"
        )

    option_data = {**option_request.dict(), "question_id": question_id}
    response = db.table("options").insert(option_data).execute()

    created_option = Option(**response.data[0])
    return build_created_response("Option added successfully", created_option)

@router.put("/{question_id}", response_model=UnifiedResponse[Question])
async def update_question(
    question_id: int,
    question_request: QuestionRequest,
    current_user: dict = Depends(get_current_active_user)
):
    db = get_db()

    existing = db.table("questions").select("*").eq("id", question_id).eq("is_deleted", False).maybeSingle().execute()
    if not existing.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Question not found"
        )

    if existing.data["creator_id"] != current_user["id"] and current_user["role"] != "Admin":
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Not authorized to update this question"
        )

    update_data = question_request.dict()
    db.table("questions").update(update_data).eq("id", question_id).execute()

    updated = db.table("questions").select("*").eq("id", question_id).maybeSingle().execute()
    question = Question(**updated.data)

    return build_ok_response("Question updated successfully", question)

@router.delete("/{question_id}", response_model=UnifiedResponse[dict])
async def delete_question(
    question_id: int,
    current_user: dict = Depends(get_current_active_user)
):
    db = get_db()

    existing = db.table("questions").select("*").eq("id", question_id).eq("is_deleted", False).maybeSingle().execute()
    if not existing.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Question not found"
        )

    if existing.data["creator_id"] != current_user["id"] and current_user["role"] != "Admin":
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Not authorized to delete this question"
        )

    db.table("questions").update({"is_deleted": True}).eq("id", question_id).execute()

    return build_ok_response("Question deleted successfully", {"deleted": True})

@router.get("/quiz/{quiz_id}", response_model=UnifiedResponse[List[Question]])
async def get_questions_by_quiz(
    quiz_id: int,
    page: int = Query(0, ge=0),
    size: int = Query(10, ge=1, le=100)
):
    db = get_db()
    offset = page * size

    response = db.table("questions").select("*").eq("quiz_id", quiz_id).eq("is_deleted", False).range(offset, offset + size - 1).execute()

    questions = [Question(**q) for q in response.data]
    return build_ok_response("Questions retrieved successfully", questions)
