from pydantic import BaseModel

class BookmarkRequest(BaseModel):
    quiz_id: int
