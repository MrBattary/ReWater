from bson import ObjectId

from .base import BaseDriver
from ..structures import MongoDevice


class DeviceDriver(BaseDriver):
    def __init__(self, host: str, port: str, db_name: str):
        super().__init__(
            host=host,
            port=port,
            db_name=db_name,
            coll_name="devices",
        )

    def get_devices(self, filter: dict = {}) -> list[MongoDevice]:
        device_docs = self._get_docs(filter)
        return list(map(MongoDevice.from_dict, device_docs))

    def get_device(self, device_id: str, hardcoded_id: bool) -> MongoDevice:
        device_doc = self._get_doc(
            {"_id": ObjectId(device_id)}
            if not hardcoded_id
            else {"deviceHardcodedId": device_id}
        )
        if not device_doc:
            return MongoDevice(
                _id=None,
                deviceHardcodedId=None,
                name=None,
                parentNetworkId=None,
                parentScheduleId=None,
            )
        return MongoDevice.from_dict(device_doc)

    def create_device(self, data: dict) -> str:
        device_id = self._create_doc(data)
        return device_id

    def update_device(self, device_id: str, data: dict) -> None:
        self._update_doc({"_id": ObjectId(device_id)}, data)
