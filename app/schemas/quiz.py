from pydantic import BaseModel
from typing import Optional
from app.models.quiz import Severity

class QuizRequest(BaseModel):
    title: str
    description: str
    quiz_pic: Optional[str] = None
    time_limit: int
    randomize_questions: bool = False
    severity: Severity
    category_id: int
