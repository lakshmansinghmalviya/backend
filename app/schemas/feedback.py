from pydantic import BaseModel

class FeedbackRequest(BaseModel):
    feedback_text: str
    question_id: int
