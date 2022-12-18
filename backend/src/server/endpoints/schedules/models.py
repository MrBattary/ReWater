from pydantic import BaseModel, conint

from .. import base_models
from ..devices.models import DeviceInfo


class Period(BaseModel):
    days: conint(strict=True, ge=0)
    hours: conint(strict=True, ge=0, lt=24)
    minutes: conint(strict=True, ge=0, lt=60)


class Volume(BaseModel):
    l: conint(strict=True, ge=0)
    ml: conint(strict=True, ge=0, lt=1000)


class ScheduleInfoResponse(base_models.BaseInfo):
    period: Period
    volume: Volume
    devices: list[DeviceInfo]


class UpdateScheduleRequest(BaseModel):
    name: str
    period: Period
    volume: Volume
    deviceIds: list[str]

    def to_dict(self) -> dict:
        return {
            "name": self.name,
            "days": self.period.days,
            "hours": self.period.hours,
            "minutes": self.period.minutes,
            "liters": self.volume.l,
            "mliters": self.volume.ml,
            "deviceIds": self.deviceIds,
        }


class CreateScheduleRequest(UpdateScheduleRequest):
    networkId: str

    def to_dict(self) -> dict:
        return {
            "name": self.name,
            "networkId": self.networkId,
            "days": self.period.days,
            "hours": self.period.hours,
            "minutes": self.period.minutes,
            "liters": self.volume.l,
            "mliters": self.volume.ml,
            "deviceIds": self.deviceIds,
        }
