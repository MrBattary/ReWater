from pydantic import BaseModel

from .. import base_models


class EditDeviceRequest(BaseModel):
    name: str
    parentScheduleId: str


class CreateDeviceRequest(EditDeviceRequest):
    deviceHardcodedId: str


class DeviceInfo(base_models.BaseInfo):
    status: base_models.Status


class DeviceInfoResponse(DeviceInfo):
    parentSchedule: base_models.BaseInfo
    parentNetwork: base_models.BaseInfo
