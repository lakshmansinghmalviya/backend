from enum import Enum
from typing import Optional
from datetime import datetime
from pydantic import BaseModel, EmailStr
from uuid import UUID

class Role(str, Enum):
    STUDENT = "Student"
    EDUCATOR = "Educator"
    ADMIN = "Admin"

class User(BaseModel):
    id: Optional[UUID] = None
    email: EmailStr
    name: str
    password: str
    role: Role
    profile_pic: Optional[str] = None
    education: Optional[str] = None
    bio: Optional[str] = None
    last_login: Optional[datetime] = None
    is_active: bool = True
    is_approved: bool = False
    is_logout: bool = False
    is_deleted: bool = False
    created_at: Optional[datetime] = None
    updated_at: Optional[datetime] = None

    class Config:
        from_attributes = True
