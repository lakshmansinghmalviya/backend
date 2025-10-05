from typing import Optional, TypeVar
from app.schemas.common import UnifiedResponse

T = TypeVar('T')

def build_response(status: int, message: str, data: Optional[T] = None) -> UnifiedResponse[T]:
    return UnifiedResponse(status=status, message=message, data=data)

def build_ok_response(message: str, data: Optional[T] = None) -> UnifiedResponse[T]:
    return build_response(200, message, data)

def build_created_response(message: str, data: Optional[T] = None) -> UnifiedResponse[T]:
    return build_response(201, message, data)
