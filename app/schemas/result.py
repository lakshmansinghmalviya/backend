from pydantic import BaseModel

class ResultRequest(BaseModel):
    score: int
    total_score: int
    total_question: int
    time_spent: int
    is_completed: bool
    correct_answers: int
    incorrect_answers: int
    times_taken: int
    quiz_id: int
