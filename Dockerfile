FROM python:3.11-slim

WORKDIR /app

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY app ./app
COPY run.py .
COPY .env .

EXPOSE 7000

CMD ["python", "run.py"]
