from typing import Optional
from datetime import datetime
from pydantic import BaseModel

class Option(BaseModel):
    id: Optional[int] = None
    text: str
    is_correct: bool
    option_pic: Optional[str] = None
    question_id: int
    is_deleted: bool = False
    created_at: Optional[datetime] = None
    updated_at: Optional[datetime] = None

    class Config:
        from_attributes = True
