from pydantic_settings import BaseSettings
from functools import lru_cache

class Settings(BaseSettings):
    API_VERSION: str = "v1"
    JWT_SECRET: str
    JWT_ACCESS_EXPIRATION: int = 600000
    JWT_REFRESH_EXPIRATION: int = 86400000
    SUPABASE_URL: str
    SUPABASE_KEY: str
    DATABASE_URL: str

    class Config:
        env_file = ".env"
        case_sensitive = True

@lru_cache()
def get_settings() -> Settings:
    return Settings()

settings = get_settings()
