from typing import Optional
from datetime import datetime
from pydantic import BaseModel
from uuid import UUID

class Bookmark(BaseModel):
    id: Optional[int] = None
    user_id: UUID
    quiz_id: int
    is_deleted: bool = False
    created_at: Optional[datetime] = None
    updated_at: Optional[datetime] = None

    class Config:
        from_attributes = True
