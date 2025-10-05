# Quick Start Guide

## Overview

This application has been converted from Spring Boot (Java) to FastAPI (Python). The new Python application provides the same REST API functionality with improved performance and simpler deployment.

## Prerequisites

1. Python 3.9 or higher
2. pip (Python package manager)
3. Supabase account (database is already configured)

## Installation Steps

### 1. Install Dependencies

```bash
pip install -r requirements.txt
```

Or if you're using Python 3 specifically:

```bash
pip3 install -r requirements.txt
```

### 2. Verify Environment Variables

The `.env` file is already configured with:
- Supabase connection details
- JWT secret key
- Token expiration times

No changes needed unless you want to customize settings.

### 3. Run the Application

#### Option A: Using the run script
```bash
python run.py
```

#### Option B: Using uvicorn directly
```bash
uvicorn app.main:app --host 0.0.0.0 --port 7000 --reload
```

The application will start on `http://localhost:7000`

### 4. Access API Documentation

Once running, visit:
- **Swagger UI**: http://localhost:7000/docs
- **ReDoc**: http://localhost:7000/redoc

## Project Structure Comparison

### Before (Spring Boot)
```
src/main/java/com/example/quizapp/
├── controller/          # REST endpoints
├── service/            # Business logic
├── repository/         # Database access
├── entity/            # Database entities
├── dto/               # Request/Response objects
├── config/            # Configuration
├── util/              # Utilities
└── filter/            # Security filters
```

### After (FastAPI)
```
app/
├── api/v1/            # REST endpoints (controllers)
├── models/            # Database models (entities)
├── schemas/           # Request/Response schemas (DTOs)
├── middleware/        # Authentication middleware (filters)
├── utils/             # Utility functions
├── config.py          # Configuration
├── database.py        # Supabase client
└── main.py            # Application entry point
```

## Key Differences

| Feature | Spring Boot | FastAPI |
|---------|-------------|---------|
| Language | Java | Python |
| Framework | Spring Boot | FastAPI |
| Database | MySQL + JPA | PostgreSQL + Supabase |
| Auth | Spring Security | JWT Middleware |
| Validation | Jakarta Validation | Pydantic |
| Async | No | Yes (native) |
| API Docs | SpringDoc OpenAPI | Auto-generated Swagger/ReDoc |

## Testing the API

### 1. Register a User
```bash
curl -X POST http://localhost:7000/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123",
    "role": "Student"
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:7000/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

### 3. Use Access Token
```bash
curl -X GET http://localhost:7000/api/v1/users/me \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

## Common Issues

### Issue: Module not found
**Solution**: Install dependencies with `pip install -r requirements.txt`

### Issue: Port already in use
**Solution**: Change the port in `run.py` or use a different port:
```bash
uvicorn app.main:app --host 0.0.0.0 --port 8000 --reload
```

### Issue: Database connection error
**Solution**: Verify Supabase credentials in `.env` file

## Development

### Hot Reload
The application runs with auto-reload enabled by default. Changes to Python files will automatically restart the server.

### Adding New Endpoints
1. Create endpoint function in appropriate file under `app/api/v1/`
2. Import and register router in `app/main.py` (already done for existing endpoints)

### Database Changes
Database schema is managed through Supabase. You can:
- Use Supabase Dashboard for schema changes
- Use SQL migrations
- Use the Supabase CLI

## Production Deployment

For production, consider:
1. Use a production WSGI server (e.g., Gunicorn with Uvicorn workers)
2. Set `reload=False` in production
3. Use environment-specific `.env` files
4. Enable HTTPS
5. Set up proper logging
6. Configure CORS appropriately

Example production command:
```bash
gunicorn app.main:app -w 4 -k uvicorn.workers.UvicornWorker --bind 0.0.0.0:7000
```

## Support

For issues or questions:
1. Check the API documentation at `/docs`
2. Review the detailed README in `README_FASTAPI.md`
3. Check application logs for errors
