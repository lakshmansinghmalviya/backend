from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from app.config import settings
from app.api.v1 import auth, users, categories, quizzes, questions, results, feedbacks, bookmarks

app = FastAPI(
    title="Quiz Application API",
    description="A comprehensive quiz application with user management, quizzes, questions, and results tracking",
    version="1.0.0"
)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(auth.router, prefix=f"/api/{settings.API_VERSION}/auth", tags=["Authentication"])
app.include_router(users.router, prefix=f"/api/{settings.API_VERSION}/users", tags=["Users"])
app.include_router(categories.router, prefix=f"/api/{settings.API_VERSION}/categories", tags=["Categories"])
app.include_router(quizzes.router, prefix=f"/api/{settings.API_VERSION}/quizzes", tags=["Quizzes"])
app.include_router(questions.router, prefix=f"/api/{settings.API_VERSION}/questions", tags=["Questions"])
app.include_router(results.router, prefix=f"/api/{settings.API_VERSION}/results", tags=["Results"])
app.include_router(feedbacks.router, prefix=f"/api/{settings.API_VERSION}/feedbacks", tags=["Feedbacks"])
app.include_router(bookmarks.router, prefix=f"/api/{settings.API_VERSION}/bookmarks", tags=["Bookmarks"])

@app.get("/")
async def root():
    return {"message": "Quiz Application API", "version": "1.0.0"}

@app.get("/health")
async def health_check():
    return {"status": "healthy"}
