import uvicorn
from fastapi import FastAPI

from .endpoints import (
    devices_router,
    networks_router,
    schedules_router,
)
from . import environments as env, services
from .handlers import EXCEPTION_HANDLERS
from mongo import MongoDriver


def start_api_server() -> None:
    app = get_service_app()
    uvicorn.run(
        app,
        host="0.0.0.0",
        port=env.SERVICE_PORT,
    )


def get_service_app() -> FastAPI:
    services.mongo_driver = MongoDriver(env.MONGO_HOST, env.MONGO_PORT, env.DB_NAME)
    app = FastAPI(
        title="Control Server",
        exception_handlers=EXCEPTION_HANDLERS,
        openapi_tags=[{"name": "networks"}, {"name": "schedules"}, {"name": "devices"}],
    )
    app.include_router(devices_router)
    app.include_router(networks_router)
    app.include_router(schedules_router)

    return app
