from enum import Enum
from pydantic import BaseModel


class StatusValue(str, Enum):
    UNDEFINED = "UNDEFINED"
    OK = "OK"
    WARNING = "WARNING"
    DEFECT = "DEFECT"


class Status(BaseModel):
    water: StatusValue = StatusValue.UNDEFINED
    battery: StatusValue = StatusValue.UNDEFINED

    @classmethod
    def from_dict(cls, raw_data: dict) -> "Status":
        return cls(
            water=StatusValue(raw_data["water"]),
            battery=StatusValue(raw_data["battery"]),
        )


class BaseInfo(BaseModel):
    id: str
    name: str

    @classmethod
    def from_dict(cls, raw_data: dict) -> "BaseInfo":
        return cls(id=raw_data["id"], name=raw_data["name"])
