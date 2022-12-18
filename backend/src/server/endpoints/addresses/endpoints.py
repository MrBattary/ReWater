from fastapi import APIRouter
from fastapi.responses import Response

from . import models
from ..handlers import _service_errors_handler, ERROR_RESPONSES
from ... import services
from utils.logger import logger


router = APIRouter(prefix="/api/connections", responses=ERROR_RESPONSES)


@router.post(
    "",
    response_class=Response,
    tags=["service"],
)
@_service_errors_handler
def create_connection(request: models.ConnectionRequest):
    services.mongo_driver.create_address(request.dict())
    return Response()
