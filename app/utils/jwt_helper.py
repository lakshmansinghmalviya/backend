import jwt
from datetime import datetime, timedelta
from typing import Optional
from app.config import settings

class JWTHelper:
    def __init__(self):
        self.secret_key = settings.JWT_SECRET
        self.algorithm = "HS256"

    def generate_token(self, username: str, validity_ms: int) -> str:
        payload = {
            "sub": username,
            "iat": datetime.utcnow(),
            "exp": datetime.utcnow() + timedelta(milliseconds=validity_ms)
        }
        return jwt.encode(payload, self.secret_key, algorithm=self.algorithm)

    def extract_username(self, token: str) -> Optional[str]:
        try:
            payload = jwt.decode(token, self.secret_key, algorithms=[self.algorithm])
            return payload.get("sub")
        except jwt.ExpiredSignatureError:
            return None
        except jwt.InvalidTokenError:
            return None

    def is_token_valid(self, token: str) -> bool:
        try:
            jwt.decode(token, self.secret_key, algorithms=[self.algorithm])
            return True
        except jwt.ExpiredSignatureError:
            return False
        except jwt.InvalidTokenError:
            return False

jwt_helper = JWTHelper()
