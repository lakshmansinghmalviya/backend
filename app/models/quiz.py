from enum import Enum
from typing import Optional
from datetime import datetime
from pydantic import BaseModel
from uuid import UUID

class Severity(str, Enum):
    BEGINNER = "Beginner"
    MEDIUM = "Medium"
    HARD = "Hard"

class Quiz(BaseModel):
    id: Optional[int] = None
    title: str
    description: str
    quiz_pic: Optional[str] = None
    time_limit: int
    randomize_questions: bool = False
    severity: Severity
    category_id: int
    creator_id: UUID
    is_deleted: bool = False
    created_at: Optional[datetime] = None
    updated_at: Optional[datetime] = None

    class Config:
        from_attributes = True
