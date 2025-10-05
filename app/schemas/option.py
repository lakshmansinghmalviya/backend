from pydantic import BaseModel
from typing import Optional

class OptionRequest(BaseModel):
    text: str
    is_correct: bool
    option_pic: Optional[str] = None
    question_id: int
