from fastapi import FastAPI
from .routers import org

app = FastAPI(title="ARCA HR-ORG Service")
app.include_router(org.router)