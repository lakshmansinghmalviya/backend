from fastapi import HTTPException, Security, Depends
from fastapi.security import HTTPBearer, HTTPAuthorizationCredentials
from typing import Optional
from uuid import UUID
from app.utils.jwt_helper import jwt_helper
from app.database import get_db

security = HTTPBearer()

async def get_current_user(credentials: HTTPAuthorizationCredentials = Security(security)) -> dict:
    token = credentials.credentials

    if not jwt_helper.is_token_valid(token):
        raise HTTPException(status_code=401, detail="Invalid or expired token")

    username = jwt_helper.extract_username(token)
    if not username:
        raise HTTPException(status_code=401, detail="Could not validate credentials")

    db = get_db()
    response = db.table("users").select("*").eq("email", username).eq("is_deleted", False).maybeSingle().execute()

    if not response.data:
        raise HTTPException(status_code=404, detail="User not found")

    return response.data

async def get_current_active_user(current_user: dict = Depends(get_current_user)) -> dict:
    if not current_user.get("is_active"):
        raise HTTPException(status_code=400, detail="Inactive user")
    return current_user

def require_role(required_roles: list[str]):
    async def role_checker(current_user: dict = Depends(get_current_active_user)) -> dict:
        if current_user.get("role") not in required_roles:
            raise HTTPException(status_code=403, detail="Insufficient permissions")
        return current_user
    return role_checker
