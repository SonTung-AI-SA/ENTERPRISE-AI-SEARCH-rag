from fastapi import FastAPI, Request
import time
import uuid

app = FastAPI()

@app.post("/internal/query")
async def internal_query(request: Request):
    start_time = time.time()
    body = await request.json()

    request_id = body.get("request_id", str(uuid.uuid4()))  

    print(f"[AI] request_id: {request_id} question={body.get('question')}")
    return {
        "request_id": request_id,
        "answer": "This is a placeholder answer.",
        "confidence": 0.0,
        "latency": int(time.time() - start_time) * 1000
    }