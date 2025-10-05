from typing import Generic, TypeVar, Optional, List
from pydantic import BaseModel

T = TypeVar('T')

class UnifiedResponse(BaseModel, Generic[T]):
    status: int
    message: str
    data: Optional[T] = None

class MessageResponse(BaseModel):
    message: str

class PageResponse(BaseModel, Generic[T]):
    content: List[T]
    page_number: int
    page_size: int
    total_elements: int
    total_pages: int
    is_last: bool
