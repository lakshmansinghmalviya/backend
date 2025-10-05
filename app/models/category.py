from typing import Optional
from datetime import datetime
from pydantic import BaseModel
from uuid import UUID

class Category(BaseModel):
    id: Optional[int] = None
    name: str
    description: str
    category_pic: Optional[str] = None
    creator_id: UUID
    is_deleted: bool = False
    created_at: Optional[datetime] = None
    updated_at: Optional[datetime] = None

    class Config:
        from_attributes = True
