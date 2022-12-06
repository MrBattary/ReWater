FROM python:3.9.15-slim

WORKDIR /app
COPY requirements.txt requirements.txt
RUN pip install -r requirements.txt
COPY . .

WORKDIR /app/src
CMD ["python3", "start.py"]

EXPOSE 30001
