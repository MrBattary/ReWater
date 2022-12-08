from bson import ObjectId

from . import structures as struct
from .addresses_driver import AddressDriver
from .devices_driver import DeviceDriver
from .networks_driver import NetworkDriver
from .schedules_driver import ScheduleDriver


class MongoDriver:
    def __init__(self, host: str, port: int, db_name: str):
        self._devdriver = DeviceDriver(host, port, db_name)
        self._netdriver = NetworkDriver(host, port, db_name)
        self._schdriver = ScheduleDriver(host, port, db_name)
        self._addrdriver = AddressDriver(host, port, db_name)

    # DEVICES
    def get_devices(self, filter: dict = {}) -> list[struct.MongoDevice]:
        return self._devdriver.get_devices(filter)

    def get_device(self, device_id: str, hardcoded_id: bool) -> struct.MongoDevice:
        return self._devdriver.get_device(device_id, hardcoded_id)

    def create_device(self, data: dict) -> str:
        schedule = self._schdriver.get_schedule(data["parentScheduleId"])

        device_id = self._devdriver.create_device(
            {"parentNetworkId": schedule.networkId, **data}
        )

        self._schdriver.update_schedule(
            schedule.id,
            {"$push": {"deviceIds": device_id}},
        )
        self._netdriver.update_network(
            schedule.networkId,
            {"$push": {"deviceIds": device_id}},
        )

        return device_id

    def update_device(self, device_id: str, data: dict) -> None:
        device = self.get_device(device_id, hardcoded_id=False)

        new_schedule_id = data.get("parentScheduleId", device.parentScheduleId)
        if new_schedule_id != device.parentScheduleId:
            self._schdriver.update_schedule(
                device.parentScheduleId,
                {"$pull": {"deviceIds": device_id}},
            )
            self._netdriver.update_network(
                device.parentNetworkId,
                {"$pull": {"deviceIds": device_id}},
            )

            new_schedule = self.get_schedule(new_schedule_id)
            self._schdriver.update_schedule(
                new_schedule.id,
                {"$push": {"deviceIds": device_id}},
            )
            self._netdriver.update_network(
                new_schedule.networkId,
                {"$push": {"deviceIds": device_id}},
            )

        self._devdriver.update_device(device_id, {"$set": data})

    def delete_device(self, device_id) -> None:
        device = self.get_device(device_id, hardcoded_id=False)
        self._netdriver.update_network(
            device.parentNetworkId,
            {"$pull": {"deviceIds": device_id}},
        )
        self._schdriver.update_schedule(
            device.parentScheduleId,
            {"$pull": {"deviceIds": device_id}},
        )
        self._devdriver.delete_doc({"_id": ObjectId(device_id)})

    # NETWORKS
    def get_networks(self) -> list[struct.MongoNetwork]:
        return self._netdriver.get_networks()

    def get_network(self, network_id: str) -> struct.MongoNetwork:
        return self._netdriver.get_network(network_id)

    def create_network(self, data: dict) -> str:
        return self._netdriver.create_network(data)

    def update_network(self, network_id: str, data: dict) -> None:
        self._netdriver.update_network(network_id, {"$set": data})

    def delete_network(self, network_id: str) -> None:
        network_doc = self.get_network(network_id)
        for device_id in network_doc.deviceIds:
            self._devdriver.update_device(device_id, data={"parentNetworkId": None})
        self._netdriver.delete_doc({"_id": ObjectId(network_id)})

    # SCHEDULES
    def get_schedules(self, filter: dict = {}) -> list[struct.MongoSchedule]:
        return self._schdriver.get_schedules(filter)

    def get_schedule(self, schedule_id: str) -> struct.MongoSchedule:
        return self._schdriver.get_schedule(schedule_id)

    def create_schedule(self, data: dict) -> str:
        return self._schdriver.create_schedule(data)

    def update_schedule(self, schedule_id: str, data: dict) -> None:
        self._schdriver.update_schedule(schedule_id, {"$set": data})

    def delete_schedule(self, schedule_id: str) -> None:
        schedule = self.get_schedule(schedule_id)
        for device_id in schedule.deviceIds:
            self._devdriver.update_device(device_id, data={"parentScheduleId": None})
        self._schdriver.delete_doc({"_id": ObjectId(schedule_id)})
