from enum import IntEnum

from typing import Optional
from dataclasses import asdict, dataclass, field

"""
class MongoAddress:
    _id: str
    IP: str
    deviceHardcodedId: str
"""


@dataclass
class MongoDocument:
    _id: Optional[str]

    @classmethod
    def from_dict(cls, raw_data: dict) -> "MongoDocument":
        raw_data["_id"] = str(raw_data["_id"])
        return cls(**raw_data)

    def to_dict(self) -> dict:
        return asdict(self)


@dataclass
class MongoDevice(MongoDocument):
    deviceHardcodedId: Optional[str]
    name: Optional[str]
    parentScheduleId: Optional[str]
    parentNetworkId: Optional[str]
    water: str = "UNDEFINED"
    battery: str = "UNDEFINED"

    def is_empty(self) -> bool:
        return (
            self._id is None
            and self.deviceHardcodedId is None
            and self.name is None
            and self.parentNetworkId is None
            and self.parentScheduleId is None
        )


@dataclass
class MongoNetwork(MongoDocument):
    name: str
    description: str
    deviceIds: list[str] = field(default_factory=list)


@dataclass
class MongoSchedule(MongoDocument):
    name: str
    networkId: str
    days: int
    hours: int
    minutes: int
    liters: int
    mliters: int
    deviceIds: list[str] = field(default_factory=list)


@dataclass
class MongoHistory(MongoDocument):
    time: str
    deviceId: str
    networkId: str
    scheduleId: str
    status: int
