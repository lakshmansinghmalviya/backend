from typing import Optional
from datetime import datetime
from pydantic import BaseModel
from uuid import UUID

class Feedback(BaseModel):
    id: Optional[int] = None
    feedback_text: str
    user_id: UUID
    question_id: int
    is_deleted: bool = False
    created_at: Optional[datetime] = None
    updated_at: Optional[datetime] = None

    class Config:
        from_attributes = True
