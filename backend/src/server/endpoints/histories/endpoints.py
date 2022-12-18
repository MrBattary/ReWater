from typing import Optional

from fastapi import APIRouter
from fastapi.responses import JSONResponse

from . import models
from ..handlers import _service_errors_handler, ERROR_RESPONSES
from ... import services


router = APIRouter(prefix="/api/history", responses=ERROR_RESPONSES)


@router.get(
    "/networks",
    response_class=JSONResponse,
    tags=["history"],
)
@_service_errors_handler
def get_all_history(page: Optional[int] = None, size: Optional[int] = None):
    history = services.mongo_driver.get_histories()
    if page and size:
        history = history[page * size : (page + 1) * size]

    result = []
    for hist in history:
        schedule = services.mongo_driver.get_schedule(hist.scheduleId)
        network = services.mongo_driver.get_network(hist.networkId)

        result.append(
            models.History(
                time=hist.time,
                network={"id": hist.networkId, "name": network.name},
                schedule={"id": hist.scheduleId, "name": schedule.name},
                status=hist.status,
            )
        )

    return JSONResponse(result)


@router.get(
    "/networks/{network_id}",
    response_class=JSONResponse,
    tags=["history"],
)
@_service_errors_handler
def get_network_history(
    network_id: str, page: Optional[int] = None, size: Optional[int] = None
):
    history = services.mongo_driver.get_histories({"networkId": network_id})
    if page and size:
        history = history[page * size : (page + 1) * size]

    result = []
    for hist in history:
        schedule = services.mongo_driver.get_schedule(hist.scheduleId)

        result.append(
            models.NetworkHistory(
                time=hist.time,
                schedule={"id": hist.scheduleId, "name": schedule.name},
                status=hist.status,
            )
        )

    return JSONResponse(result)


@router.get(
    "/schedules/{schedule_id}",
    response_class=JSONResponse,
    tags=["history"],
)
@_service_errors_handler
def get_schedule_history(
    schedule_id: str, page: Optional[int] = None, size: Optional[int] = None
):
    history = services.mongo_driver.get_histories({"scheduleId": schedule_id})
    if page and size:
        history = history[page * size : (page + 1) * size]

    result = []
    for hist in history:
        result.append(
            models.BaseHistory(
                time=hist.time,
                status=hist.status,
            )
        )
    return result


@router.get(
    "/devices/{device_id}",
    response_class=JSONResponse,
    tags=["history"],
)
@_service_errors_handler
def get_device_history(
    device_id: str, page: Optional[int] = None, size: Optional[int] = None
):
    history = services.mongo_driver.get_histories({"deviceId": device_id})
    if page and size:
        history = history[page * size : (page + 1) * size]

    schedule = services.mongo_driver.get_schedule(hist.scheduleId)
    network = services.mongo_driver.get_network(hist.networkId)

    result = []
    for hist in history:
        result.append(
            models.History(
                time=hist.time,
                network={"id": hist.networkId, "name": network.name},
                schedule={"id": hist.scheduleId, "name": schedule.name},
                status=hist.status,
            )
        )

    return JSONResponse(result)
