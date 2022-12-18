from bson import ObjectId

import requests

from . import drivers, structures as struct
from utils.errors import AlreadyActivatedDeviceError


class MongoDriver:
    def __init__(self, host: str, port: int, db_name: str):
        self._addrdriver = drivers.AddressDriver(host, port, db_name)
        self._devdriver = drivers.DeviceDriver(host, port, db_name)
        self._histdriver = drivers.HistoryDriver(host, port, db_name)
        self._netdriver = drivers.NetworkDriver(host, port, db_name)
        self._schdriver = drivers.ScheduleDriver(host, port, db_name)

    # ADDRESSES
    def get_address(self, hardcoded_id: str) -> str:
        return self._addrdriver.get_field(hardcoded_id, field="IP")

    def create_address(self, data: dict) -> str:
        return self._addrdriver.create_address(data)

    # DEVICES
    def get_devices(self, filter: dict = {}) -> list[struct.MongoDevice]:
        devices = self._devdriver.get_devices(filter)
        for ind, device in enumerate(devices):
            device_ip = self._addrdriver.get_field(device.deviceHardcodedId, field="IP")
            device_response = requests.get(f"http://{device_ip}:30101/api/arduino/info")
            if device_response.status_code == 200:
                device_info = device_response.json()
                self._devdriver.update_device(device._id, {"$set": device_info})
                device_dict = device.to_dict()
                device_dict.update(device_info)
                devices[ind] = struct.MongoDevice.from_dict(device_dict)
        return devices

    def get_device(
        self, device_id: str, hardcoded_id: bool = False
    ) -> struct.MongoDevice:
        device = self._devdriver.get_device(device_id, hardcoded_id)
        if device.is_empty():
            return device

        device_ip = self._addrdriver.get_field(device.deviceHardcodedId, field="IP")
        device_response = requests.get(f"http://{device_ip}:30101/api/arduino/info")

        if device_response.status_code == 200:
            device_info = device_response.json()
            self._devdriver.update_device(device_id, {"$set": device_info})
            device_dict = device.to_dict()
            device_dict.update(device_info)
            return struct.MongoDevice.from_dict(device_dict)

        return device

    def create_device(self, data: dict) -> str:
        device = self._devdriver._get_doc(
            {"deviceHardcodedId": data["deviceHardcodedId"]}
        )
        if device:
            raise AlreadyActivatedDeviceError()

        device_id = self._addrdriver.get_field(data["deviceHardcodedId"], field="_id")
        schedule = self._schdriver.get_schedule(data["parentScheduleId"])

        self._devdriver.create_device(
            {"_id": ObjectId(device_id), "parentNetworkId": schedule.networkId, **data}
        )
        self._netdriver.update_network(
            schedule.networkId,
            {"$push": {"deviceIds": device_id}},
        )
        self._schdriver.update_schedule(
            schedule._id,
            {"$push": {"deviceIds": device_id}},
        )

        return device_id

    def update_device(self, device_id: str, data: dict) -> None:
        device = self.get_device(device)
        if device.is_empty():
            return

        new_schedule_id = data.get("parentScheduleId", device.parentScheduleId)
        if new_schedule_id and new_schedule_id != device.parentScheduleId:
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
                new_schedule._id,
                {"$push": {"deviceIds": device_id}},
            )
            self._netdriver.update_network(
                new_schedule.networkId,
                {"$push": {"deviceIds": device_id}},
            )

        self._devdriver.update_device(device_id, {"$set": data})

    def delete_device(self, device_id: str) -> None:
        device = self.get_device(device_id)
        if device.is_empty():
            return

        self._netdriver.update_network(
            device.parentNetworkId,
            {"$pull": {"deviceIds": device_id}},
        )
        self._schdriver.update_schedule(
            device.parentScheduleId,
            {"$pull": {"deviceIds": device_id}},
        )
        self._devdriver._delete_doc({"_id": ObjectId(device_id)})
        self._addrdriver._delete_doc({"_id": ObjectId(device_id)})

    # NETWORKS
    def get_networks(self, filter: dict = {}) -> list[struct.MongoNetwork]:
        return self._netdriver.get_networks(filter)

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
        self._netdriver._delete_doc({"_id": ObjectId(network_id)})

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
        self._schdriver._delete_doc({"_id": ObjectId(schedule_id)})

    # HISTORIES
    def get_histories(self, filter: dict = {}) -> list[struct.MongoHistory]:
        return self._histdriver.get_histories(filter)

    def create_history(self, data: dict) -> None:
        self._histdriver.create_history(data)
