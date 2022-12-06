from typing import Optional
from dataclasses import asdict, dataclass, field


@dataclass
class MongoDevice:
    id: str
    deviceHardcodedId: str
    name: str
    parentScheduleId: Optional[str]
    parentNetworkId: Optional[str]
    water: str = "UNDEFINED"
    battery: str = "UNDEFINED"

    @classmethod
    def from_dict(cls, raw_data: dict) -> "MongoDevice":
        id = str(raw_data.pop("_id", ""))
        raw_id = str(raw_data.pop("id", ""))
        return cls(id=id, **raw_data)

    def to_dict(self) -> dict:
        return asdict(self)


@dataclass
class MongoNetwork:
    id: str
    name: str
    description: str
    deviceIds: list[str] = field(default_factory=list)

    @classmethod
    def from_dict(cls, raw_data: dict) -> "MongoDevice":
        id = str(raw_data.pop("_id", ""))
        raw_id = str(raw_data.pop("id", ""))
        return cls(id=id, **raw_data)

    def to_dict(self) -> dict:
        return asdict(self)


@dataclass
class MongoSchedule:
    id: str
    name: str
    networkId: str
    days: int
    hours: int
    minutes: int
    liters: int
    mliters: int
    deviceIds: list[str] = field(default_factory=list)

    @classmethod
    def from_dict(cls, raw_data: dict) -> "MongoSchedule":
        id = str(raw_data.pop("_id", ""))
        raw_id = str(raw_data.pop("id", ""))
        return cls(id=id, **raw_data)

    def to_dict(self) -> dict:
        return asdict(self)
