from typing import Union
from fastapi import FastAPI
from pydantic import BaseModel
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()

# Configure allowed origins
origins = [
    "http://localhost",
    "http://localhost:8080",  # If your frontend is running on this port
    # Add other allowed origins as needed
]

# Configure CORS middleware
app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["GET", "POST"],  # Adjust allowed methods as needed
    allow_headers=["*"],  # Adjust allowed headers as needed
)


class Message(BaseModel):
    message: str

@app.post("/mensaje-enviado/")
def process_message(message: Message):
    # Process the received message
    response_message = f"Received message: {message.message}"  # Accede al campo correcto: message
    # Return a response
    return {"response": response_message}


@app.get("/")
def read_root():
    return {"ChatGPT": "¡Hola! ¿En qué puedo ayudarte?"}

@app.get("/llama/mensaje-enviado/")
def read_llama():
    return {"Llama 2": "¡Hola! Soy Llama 2. ¿En qué puedo servirte?"}
