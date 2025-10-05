from typing import Optional
from datetime import datetime
from pydantic import BaseModel
from uuid import UUID

class Question(BaseModel):
    id: Optional[int] = None
    text: str
    question_type: str
    randomize_options: bool = False
    question_pic: Optional[str] = None
    max_score: int
    quiz_id: int
    creator_id: UUID
    is_deleted: bool = False
    created_at: Optional[datetime] = None
    updated_at: Optional[datetime] = None

    class Config:
        from_attributes = True
