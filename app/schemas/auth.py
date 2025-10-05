from pydantic import BaseModel, EmailStr
from typing import Optional
from app.models.user import Role

class LoginRequest(BaseModel):
    email: EmailStr
    password: str

class SignupRequest(BaseModel):
    name: str
    email: EmailStr
    password: str
    role: Role
    profile_pic: Optional[str] = None
    bio: Optional[str] = None
    education: Optional[str] = None

class TokenRequest(BaseModel):
    ref_token: str

class AuthResponse(BaseModel):
    access_token: str
    refresh_token: str
    role: str
    is_approved: Optional[bool] = None

class TokenResponse(BaseModel):
    access_token: str
    refresh_token: str
