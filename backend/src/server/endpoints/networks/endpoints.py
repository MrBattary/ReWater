from fastapi import APIRouter
from fastapi.responses import JSONResponse, PlainTextResponse, Response

from . import models
from ..handlers import _service_errors_handler, ERROR_RESPONSES
from ... import services


router = APIRouter(prefix="/api/networks", responses=ERROR_RESPONSES)


@router.get(
    "",
    response_class=JSONResponse,
    tags=["networks"],
)
@_service_errors_handler
def get_all_networks():
    networks = services.mongo_driver.get_networks()
    res = []
    for network in networks:
        network_devices = [
            services.mongo_driver.get_device(device_id, hardcoded_id=False)
            for device_id in network.deviceIds
        ]

        water_statuses = [device.water for device in network_devices]
        battery_statuses = [device.water for device in network_devices]

        water = (
            "DEFECT"
            if "DEFECT" in battery_statuses
            else (
                "WARNING"
                if "WARNING" in battery_statuses
                else ("OK" if "OK" in battery_statuses else "UNDEFINED")
            )
        )
        battery = (
            "DEFECT"
            if "DEFECT" in battery_statuses
            else (
                "WARNING"
                if "WARNING" in battery_statuses
                else ("OK" if "OK" in battery_statuses else "UNDEFINED")
            )
        )

        data = {
            "id": network._id,
            "name": network.name,
            "description": network.description,
            "status": {
                "water": water,
                "battery": battery,
            },
        }

        res.append(data)

    return JSONResponse(res)


@router.get(
    "/{network_id}",
    response_model=models.NetworkInfoResponse,
    tags=["networks"],
)
@_service_errors_handler
def get_network(network_id: str):
    network = services.mongo_driver.get_network(network_id)
    network_devices = [
        services.mongo_driver.get_device(device_id, hardcoded_id=False)
        for device_id in network.deviceIds
    ]

    water_statuses = [device.water for device in network_devices]
    battery_statuses = [device.water for device in network_devices]

    water = (
        "DEFECT"
        if "DEFECT" in battery_statuses
        else (
            "WARNING"
            if "WARNING" in battery_statuses
            else ("OK" if "OK" in battery_statuses else "UNDEFINED")
        )
    )
    battery = (
        "DEFECT"
        if "DEFECT" in battery_statuses
        else (
            "WARNING"
            if "WARNING" in battery_statuses
            else ("OK" if "OK" in battery_statuses else "UNDEFINED")
        )
    )

    data = {
        "id": network._id,
        "name": network.name,
        "description": network.description,
        "status": {
            "water": water,
            "battery": battery,
        },
    }

    return JSONResponse(data)


@router.post(
    "",
    response_class=PlainTextResponse,
    tags=["networks"],
)
@_service_errors_handler
def create_network(network_info: models.NetworkInfoRequest):
    network_id = services.mongo_driver.create_network(network_info.dict())
    return PlainTextResponse(network_id)


@router.put(
    "/{network_id}",
    response_class=Response,
    tags=["networks"],
)
@_service_errors_handler
def edit_network(network_id: str, network_info: models.NetworkInfoRequest):
    services.mongo_driver.update_network(network_id, network_info.dict())
    return Response()


@router.delete(
    "/{network_id}",
    response_class=Response,
    tags=["networks"],
)
@_service_errors_handler
def delete_network(network_id: str):
    services.mongo_driver.delete_network(network_id)
    return Response()
