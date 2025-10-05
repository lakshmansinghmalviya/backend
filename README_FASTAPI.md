# Quiz Application - FastAPI

A comprehensive quiz application REST API built with FastAPI and Supabase, converted from Spring Boot.

## Features

- User authentication with JWT tokens (access and refresh tokens)
- Role-based access control (Student, Educator, Admin)
- Category management
- Quiz creation and management
- Question and option management
- Result tracking
- User feedback system
- Quiz bookmarking
- Soft delete pattern for data safety

## Technology Stack

- **Framework**: FastAPI
- **Database**: Supabase (PostgreSQL)
- **Authentication**: JWT with PyJWT
- **Password Hashing**: Passlib with bcrypt
- **Validation**: Pydantic

## Project Structure

```
app/
├── api/
│   └── v1/
│       ├── auth.py           # Authentication endpoints
│       ├── users.py          # User management endpoints
│       ├── categories.py     # Category endpoints
│       ├── quizzes.py        # Quiz endpoints
│       ├── questions.py      # Question endpoints
│       ├── results.py        # Result endpoints
│       ├── feedbacks.py      # Feedback endpoints
│       └── bookmarks.py      # Bookmark endpoints
├── middleware/
│   └── auth.py              # Authentication middleware
├── models/                  # Pydantic models
├── schemas/                 # Request/Response schemas
├── utils/                   # Utility functions
├── config.py               # Configuration
├── database.py             # Supabase client
└── main.py                 # Application entry point
```

## Setup

### Prerequisites

- Python 3.9+
- Supabase account and project

### Installation

1. Install dependencies:
```bash
pip install -r requirements.txt
```

2. Configure environment variables:
Copy `.env.example` to `.env` and update with your values:
```bash
API_VERSION=v1
JWT_SECRET=your-secret-key
JWT_ACCESS_EXPIRATION=600000
JWT_REFRESH_EXPIRATION=86400000
SUPABASE_URL=your-supabase-url
SUPABASE_KEY=your-supabase-anon-key
DATABASE_URL=your-database-url
```

3. Run the application:
```bash
python run.py
```

Or using uvicorn directly:
```bash
uvicorn app.main:app --host 0.0.0.0 --port 7000 --reload
```

## API Endpoints

### Authentication
- `POST /api/v1/auth/register` - Register new user
- `POST /api/v1/auth/login` - Login user
- `POST /api/v1/auth/refresh-token` - Refresh access token

### Users
- `GET /api/v1/users/me` - Get current user profile
- `PUT /api/v1/users/me` - Update current user profile
- `GET /api/v1/users/` - Get all users (Admin only)
- `GET /api/v1/users/{user_id}` - Get user by ID
- `POST /api/v1/users/{user_id}/approve` - Approve educator (Admin only)
- `DELETE /api/v1/users/{user_id}` - Delete user (Admin only)

### Categories
- `POST /api/v1/categories/` - Create category (Educator/Admin)
- `GET /api/v1/categories/` - Get all categories
- `GET /api/v1/categories/{category_id}` - Get category by ID
- `PUT /api/v1/categories/{category_id}` - Update category
- `DELETE /api/v1/categories/{category_id}` - Delete category

### Quizzes
- `POST /api/v1/quizzes/` - Create quiz (Educator/Admin)
- `GET /api/v1/quizzes/` - Get all quizzes
- `GET /api/v1/quizzes/{quiz_id}` - Get quiz by ID
- `PUT /api/v1/quizzes/{quiz_id}` - Update quiz
- `DELETE /api/v1/quizzes/{quiz_id}` - Delete quiz
- `GET /api/v1/quizzes/category/{category_id}` - Get quizzes by category
- `GET /api/v1/quizzes/creator/{creator_id}` - Get quizzes by creator

### Questions
- `POST /api/v1/questions/` - Create question (Educator/Admin)
- `GET /api/v1/questions/` - Get all questions
- `GET /api/v1/questions/{question_id}` - Get question by ID
- `GET /api/v1/questions/{question_id}/options` - Get question options
- `POST /api/v1/questions/{question_id}/options` - Add option to question
- `PUT /api/v1/questions/{question_id}` - Update question
- `DELETE /api/v1/questions/{question_id}` - Delete question
- `GET /api/v1/questions/quiz/{quiz_id}` - Get questions by quiz

### Results
- `POST /api/v1/results/` - Create result
- `GET /api/v1/results/` - Get user results
- `GET /api/v1/results/{result_id}` - Get result by ID
- `GET /api/v1/results/quiz/{quiz_id}` - Get results by quiz
- `PUT /api/v1/results/{result_id}` - Update result

### Feedbacks
- `POST /api/v1/feedbacks/` - Create feedback
- `GET /api/v1/feedbacks/` - Get all feedbacks
- `GET /api/v1/feedbacks/{feedback_id}` - Get feedback by ID
- `GET /api/v1/feedbacks/question/{question_id}` - Get feedbacks by question
- `PUT /api/v1/feedbacks/{feedback_id}` - Update feedback
- `DELETE /api/v1/feedbacks/{feedback_id}` - Delete feedback

### Bookmarks
- `POST /api/v1/bookmarks/` - Create bookmark
- `GET /api/v1/bookmarks/` - Get user bookmarks
- `GET /api/v1/bookmarks/{bookmark_id}` - Get bookmark by ID
- `DELETE /api/v1/bookmarks/{bookmark_id}` - Delete bookmark
- `DELETE /api/v1/bookmarks/quiz/{quiz_id}` - Delete bookmark by quiz

## API Documentation

Once the application is running, visit:
- Swagger UI: `http://localhost:7000/docs`
- ReDoc: `http://localhost:7000/redoc`

## Authentication

The API uses JWT tokens for authentication. After login, include the access token in the Authorization header:

```
Authorization: Bearer <access_token>
```

## Role-Based Access

- **Student**: Can view quizzes, submit results, provide feedback, and bookmark quizzes
- **Educator**: Can create and manage categories, quizzes, and questions (requires admin approval)
- **Admin**: Full access to all resources and can approve educators

## Migration from Spring Boot

This application was converted from a Spring Boot Java application to FastAPI Python. Key changes:

1. **Framework**: Spring Boot → FastAPI
2. **ORM**: JPA/Hibernate → Supabase client
3. **Database**: MySQL → PostgreSQL (Supabase)
4. **Authentication**: Spring Security → JWT middleware
5. **Password Hashing**: BCryptPasswordEncoder → Passlib bcrypt
6. **Validation**: Jakarta Validation → Pydantic
7. **Configuration**: application.properties → .env + pydantic-settings

## License

MIT License
