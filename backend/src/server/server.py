import uvicorn
from fastapi import FastAPI

from .endpoints import addr_router, dev_router, hist_router, net_router, sch_router
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
        openapi_tags=[
            {"name": "history"},
            {"name": "service"},
            {"name": "networks"},
            {"name": "schedules"},
            {"name": "devices"},
        ],
    )
    app.include_router(addr_router)
    app.include_router(dev_router)
    app.include_router(hist_router)
    app.include_router(net_router)
    app.include_router(sch_router)

    return app
