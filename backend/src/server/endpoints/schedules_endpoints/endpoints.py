from typing import Optional

from fastapi import APIRouter
from fastapi.responses import JSONResponse, PlainTextResponse, Response

from . import models
from ..handlers import _service_errors_handler, ERROR_RESPONSES
from ... import services


router = APIRouter(prefix="/api/schedules", responses=ERROR_RESPONSES)


@router.get(
    "",
    response_class=JSONResponse,
    tags=["schedules"],
)
@_service_errors_handler
def get_all_schedules(network_id: Optional[str] = None):
    schedules = services.mongo_driver.get_schedules(
        {"networkId": network_id} if network_id else {}
    )
    res = []

    for schedule in schedules:
        schedule_devices = [
            services.mongo_driver.get_device(device_id, hardcoded_id=False)
            for device_id in schedule.deviceIds
        ]
        devices_info = [
            {
                "id": device.id,
                "name": device.name,
                "status": {
                    "water": device.water,
                    "battery": device.battery,
                },
            }
            for device in schedule_devices
        ]

        data = {
            "id": schedule.id,
            "name": schedule.name,
            "networkId": schedule.networkId,
            "period": {
                "days": schedule.days,
                "hours": schedule.hours,
                "minutes": schedule.minutes,
            },
            "volume": {
                "l": schedule.liters,
                "ml": schedule.mliters,
            },
            "devices": devices_info,
        }

        res.append(data)

    return JSONResponse(res)


@router.get(
    "/{schedule_id}",
    response_model=models.ScheduleInfoResponse,
    tags=["schedules"],
)
@_service_errors_handler
def get_schedule(schedule_id: str):
    schedule = services.mongo_driver.get_schedule(schedule_id)

    schedule_devices = [
        services.mongo_driver.get_device(device_id, hardcoded_id=False)
        for device_id in schedule.deviceIds
    ]
    devices_info = [
        {
            "id": device.id,
            "name": device.name,
            "status": {
                "water": device.water,
                "battery": device.battery,
            },
        }
        for device in schedule_devices
    ]

    data = {
        "id": schedule.id,
        "name": schedule.name,
        "period": {
            "days": schedule.days,
            "hours": schedule.hours,
            "minutes": schedule.minutes,
        },
        "volume": {
            "l": schedule.liters,
            "ml": schedule.mliters,
        },
        "devices": devices_info,
    }

    return JSONResponse(data)


@router.post(
    "",
    response_class=PlainTextResponse,
    tags=["schedules"],
)
def create_schedule(schedule_info: models.CreateScheduleRequest):
    schedule_id = services.mongo_driver.create_schedule(schedule_info.to_dict())
    return PlainTextResponse(schedule_id)


@router.put(
    "/{schedule_id}",
    response_class=Response,
    tags=["schedules"],
)
def edit_schedule(schedule_id: str, schedule_info: models.UpdateScheduleRequest):
    services.mongo_driver.update_schedule(schedule_id, schedule_info.to_dict())
    return Response()


@router.delete(
    "/{schedule_id}",
    response_class=Response,
    tags=["schedules"],
)
def delete_schedule(schedule_id: str):
    services.mongo_driver.delete_schedule(schedule_id)
    return Response()
