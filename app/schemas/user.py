from pydantic import BaseModel, EmailStr
from typing import Optional
from uuid import UUID
from datetime import datetime
from app.models.user import Role

class UpdateUserRequest(BaseModel):
    name: Optional[str] = None
    email: Optional[EmailStr] = None
    profile_pic: Optional[str] = None
    bio: Optional[str] = None
    education: Optional[str] = None

class UserResponse(BaseModel):
    id: UUID
    name: str
    email: EmailStr
    role: Role
    profile_pic: Optional[str] = None
    bio: Optional[str] = None
    education: Optional[str] = None
    is_active: bool
    is_approved: bool
    last_login: Optional[datetime] = None
    created_at: datetime
    updated_at: datetime
