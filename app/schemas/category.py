from pydantic import BaseModel
from typing import Optional

class CategoryRequest(BaseModel):
    name: str
    description: str
    category_pic: Optional[str] = None
