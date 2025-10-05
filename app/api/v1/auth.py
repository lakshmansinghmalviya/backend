from fastapi import APIRouter, HTTPException, status
from datetime import datetime
from app.schemas.auth import LoginRequest, SignupRequest, TokenRequest, AuthResponse, TokenResponse
from app.schemas.common import UnifiedResponse
from app.database import get_db
from app.utils.jwt_helper import jwt_helper
from app.utils.password import hash_password, verify_password
from app.utils.responses import build_ok_response, build_created_response
from app.config import settings

router = APIRouter()

@router.post("/register", response_model=UnifiedResponse[AuthResponse])
async def register(signup_request: SignupRequest):
    db = get_db()

    existing_user = db.table("users").select("*").eq("email", signup_request.email).maybeSingle().execute()
    if existing_user.data:
        raise HTTPException(
            status_code=status.HTTP_409_CONFLICT,
            detail="User already exists. Please try with other credentials."
        )

    user_data = {
        "name": signup_request.name,
        "email": signup_request.email,
        "password": hash_password(signup_request.password),
        "role": signup_request.role.value,
        "profile_pic": signup_request.profile_pic,
        "bio": signup_request.bio,
        "education": signup_request.education,
        "is_logout": False,
        "is_deleted": False,
        "last_login": datetime.utcnow().isoformat(),
        "is_active": True,
        "is_approved": signup_request.role.value == "Student"
    }

    db.table("users").insert(user_data).execute()

    return await login(LoginRequest(email=signup_request.email, password=signup_request.password))

@router.post("/login", response_model=UnifiedResponse[AuthResponse])
async def login(login_request: LoginRequest):
    db = get_db()

    user_response = db.table("users").select("*").eq("email", login_request.email).eq("is_deleted", False).maybeSingle().execute()

    if not user_response.data:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Invalid credentials please enter the valid email and password"
        )

    user = user_response.data

    if not verify_password(login_request.password, user["password"]):
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Invalid credentials please enter the valid email and password"
        )

    if user["role"] == "Educator" and not user.get("is_approved", False):
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Approval is pending from the admin side please wait"
        )

    access_token = jwt_helper.generate_token(user["email"], settings.JWT_ACCESS_EXPIRATION)
    refresh_token = jwt_helper.generate_token(user["email"], settings.JWT_REFRESH_EXPIRATION)

    db.table("users").update({
        "last_login": datetime.utcnow().isoformat(),
        "is_logout": False
    }).eq("id", user["id"]).execute()

    auth_response = AuthResponse(
        access_token=access_token,
        refresh_token=refresh_token,
        role=user["role"],
        is_approved=user.get("is_approved")
    )

    return build_ok_response("Logged in successfully", auth_response)

@router.post("/refresh-token", response_model=UnifiedResponse[TokenResponse])
async def refresh_access_token(token_request: TokenRequest):
    refresh_token = token_request.ref_token

    if not jwt_helper.is_token_valid(refresh_token):
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Invalid or expired refresh token"
        )

    email = jwt_helper.extract_username(refresh_token)
    if not email:
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Could not extract user from token"
        )

    new_access_token = jwt_helper.generate_token(email, settings.JWT_ACCESS_EXPIRATION)

    token_response = TokenResponse(
        access_token=new_access_token,
        refresh_token=refresh_token
    )

    return build_ok_response("Token is refreshed successfully", token_response)

@router.get("/test")
async def test():
    return {"message": "Testing the app-congrats!! Running.."}
