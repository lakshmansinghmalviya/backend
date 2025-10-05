from typing import Optional
from datetime import datetime
from pydantic import BaseModel
from uuid import UUID

class Result(BaseModel):
    id: Optional[int] = None
    score: int
    total_score: int
    total_question: int
    time_spent: int
    is_completed: bool
    correct_answers: int
    incorrect_answers: int
    times_taken: int
    user_id: UUID
    quiz_id: int
    is_deleted: bool = False
    created_at: Optional[datetime] = None
    updated_at: Optional[datetime] = None

    class Config:
        from_attributes = True
