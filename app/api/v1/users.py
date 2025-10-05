from fastapi import APIRouter, Depends, HTTPException, status
from typing import List
from app.schemas.user import UpdateUserRequest, UserResponse
from app.schemas.common import UnifiedResponse
from app.middleware.auth import get_current_active_user, require_role
from app.database import get_db
from app.utils.responses import build_ok_response

router = APIRouter()

@router.get("/me", response_model=UnifiedResponse[UserResponse])
async def get_current_user_profile(current_user: dict = Depends(get_current_active_user)):
    user_response = UserResponse(**current_user)
    return build_ok_response("User profile retrieved successfully", user_response)

@router.put("/me", response_model=UnifiedResponse[UserResponse])
async def update_current_user_profile(
    update_data: UpdateUserRequest,
    current_user: dict = Depends(get_current_active_user)
):
    db = get_db()

    update_dict = {k: v for k, v in update_data.dict(exclude_unset=True).items() if v is not None}

    if update_dict:
        response = db.table("users").update(update_dict).eq("id", current_user["id"]).execute()

        updated_user = db.table("users").select("*").eq("id", current_user["id"]).maybeSingle().execute()
        user_response = UserResponse(**updated_user.data)
        return build_ok_response("User profile updated successfully", user_response)

    return build_ok_response("No changes made", UserResponse(**current_user))

@router.get("/", response_model=UnifiedResponse[List[UserResponse]])
async def get_all_users(
    current_user: dict = Depends(require_role(["Admin"]))
):
    db = get_db()
    response = db.table("users").select("*").eq("is_deleted", False).execute()

    users = [UserResponse(**user) for user in response.data]
    return build_ok_response("Users retrieved successfully", users)

@router.get("/{user_id}", response_model=UnifiedResponse[UserResponse])
async def get_user_by_id(
    user_id: str,
    current_user: dict = Depends(get_current_active_user)
):
    db = get_db()
    response = db.table("users").select("*").eq("id", user_id).eq("is_deleted", False).maybeSingle().execute()

    if not response.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="User not found"
        )

    user_response = UserResponse(**response.data)
    return build_ok_response("User retrieved successfully", user_response)

@router.post("/{user_id}/approve", response_model=UnifiedResponse[UserResponse])
async def approve_educator(
    user_id: str,
    current_user: dict = Depends(require_role(["Admin"]))
):
    db = get_db()

    user = db.table("users").select("*").eq("id", user_id).eq("is_deleted", False).maybeSingle().execute()

    if not user.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="User not found"
        )

    if user.data["role"] != "Educator":
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Only educators can be approved"
        )

    updated = db.table("users").update({"is_approved": True}).eq("id", user_id).execute()

    updated_user = db.table("users").select("*").eq("id", user_id).maybeSingle().execute()
    user_response = UserResponse(**updated_user.data)
    return build_ok_response("Educator approved successfully", user_response)

@router.delete("/{user_id}", response_model=UnifiedResponse[dict])
async def delete_user(
    user_id: str,
    current_user: dict = Depends(require_role(["Admin"]))
):
    db = get_db()

    user = db.table("users").select("*").eq("id", user_id).eq("is_deleted", False).maybeSingle().execute()

    if not user.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="User not found"
        )

    db.table("users").update({"is_deleted": True}).eq("id", user_id).execute()

    return build_ok_response("User deleted successfully", {"deleted": True})
