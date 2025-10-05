from pydantic import BaseModel
from typing import Optional

class QuestionRequest(BaseModel):
    text: str
    question_type: str
    randomize_options: bool = False
    question_pic: Optional[str] = None
    max_score: int
    quiz_id: int
