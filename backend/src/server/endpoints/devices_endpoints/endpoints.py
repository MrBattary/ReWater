from fastapi import APIRouter
from fastapi.responses import JSONResponse, PlainTextResponse, Response

from . import models
from ..handlers import _service_errors_handler, ERROR_RESPONSES
from ..schedules_endpoints.models import Volume
from ... import services


router = APIRouter(prefix="/api/devices", responses=ERROR_RESPONSES)


@router.get(
    "",
    response_class=JSONResponse,
    tags=["devices"],
)
@_service_errors_handler
def get_all_devices(full_info: bool, attachable: bool = True):
    devices = services.mongo_driver.get_devices(
        {"parentScheduleId": None} if attachable else {}
    )

    res = []
    for device in devices:

        device_info = {
            "id": device.id,
            "name": device.name,
        }
        if full_info:
            device_info.update(
                {
                    "parentNetwork": {"id": "", "name": ""},
                    "parentSchedule": {"id": "", "name": ""},
                    "status": {
                        "water": device.water,
                        "battery": device.battery,
                    },
                }
            )
        if device.parentNetworkId:
            network = services.mongo_driver.get_network(device.parentNetworkId)
            device_info.update(
                {
                    "parentNetwork": {
                        "id": network.id,
                        "name": network.name,
                    },
                }
            )
        if device.parentScheduleId:
            schedule = services.mongo_driver.get_schedule(device.parentScheduleId)
            device_info.update(
                {
                    "parentSchedule": {
                        "id": schedule.id,
                        "name": schedule.name,
                    }
                }
            )

        res.append(device_info)

    return JSONResponse(res)


@router.get(
    "/{device_id}",
    response_model=models.DeviceInfoResponse,
    tags=["devices"],
)
@_service_errors_handler
def get_device(device_id: str, hardcoded_id: bool):
    device = services.mongo_driver.get_device(device_id, hardcoded_id)

    device_info = {
        "id": device.id,
        "name": device.name,
        "parentNetwork": {"id": "", "name": ""},
        "parentSchedule": {"id": "", "name": ""},
        "status": {
            "water": device.water,
            "battery": device.battery,
        },
    }
    if device.parentNetworkId:
        network = services.mongo_driver.get_network(device.parentNetworkId)
        device_info.update(
            {
                "parentNetwork": {
                    "id": network.id,
                    "name": network.name,
                },
            }
        )
    if device.parentScheduleId:
        schedule = services.mongo_driver.get_schedule(device.parentScheduleId)
        device_info.update(
            {
                "parentSchedule": {
                    "id": schedule.id,
                    "name": schedule.name,
                }
            }
        )

    return models.DeviceInfoResponse(**device_info)


@router.post(
    "",
    response_class=PlainTextResponse,
    tags=["devices"],
)
@_service_errors_handler
def create_device(device_info: models.CreateDeviceRequest):
    device_id = services.mongo_driver.create_device(device_info.dict())
    return PlainTextResponse(device_id)


@router.put(
    "/{device_id}",
    response_class=Response,
    tags=["devices"],
)
@_service_errors_handler
def edit_device(device_id: str, device_info: models.EditDeviceRequest):
    services.mongo_driver.update_device(device_id, device_info.dict())
    return Response()


@router.delete(
    "/{device_id}",
    response_class=Response,
    tags=["devices"],
)
@_service_errors_handler
def delete_device(device_id: str):
    services.mongo_driver.delete_device(device_id)
    return Response()


@router.post(
    "/{device_id}",
    response_class=Response,
    tags=["devices"],
)
@_service_errors_handler
def force_watering(device_id: str, volume: Volume):
    return Response()
