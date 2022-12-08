from bson import ObjectId

from .base_driver import BaseDriver
from .structures import MongoDevice


class DeviceDriver(BaseDriver):
    def __init__(self, host: str, port: str, db_name: str):
        super().__init__(
            host=host,
            port=port,
            db_name=db_name,
            coll_name="devices",
        )

    def get_devices(self, filter: dict = {}) -> list[MongoDevice]:
        device_docs = self.get_docs(filter)
        return list(map(MongoDevice.from_dict, device_docs))

    def get_device(self, device_id: str, hardcoded_id: bool) -> MongoDevice:
        device_doc = self.get_doc(
            # {("_id" if not hardcoded_id else "hardcodedId"): ObjectId(device_id)}
            {"_id": ObjectId(device_id)}
            if not hardcoded_id
            else {"deviceHardcodedId": device_id}
        )
        return MongoDevice.from_dict(device_doc)

    def create_device(self, data: dict) -> str:
        device = MongoDevice.from_dict(data)
        device_id = self.create_doc(device.to_dict())
        return device_id

    def update_device(self, device_id: str, data: dict) -> None:
        self.update_doc({"_id": ObjectId(device_id)}, data)

    # def delete_device(self, device_id: str) -> None:
    #     device = self.get_device(device_id)
    #     network_driver.update_network(
    #         device.parentNetworkId,
    #         {"$pull": {"deviceIds": device_id}},
    #     )
    #     schedule_driver.update_schedule(
    #         device.parentScheduleId,
    #         {"$pull": {"deviceIds": device_id}},
    #     )
    #     self.delete_doc({"_id": ObjectId(device_id)})
