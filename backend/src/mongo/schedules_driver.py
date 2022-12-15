from bson import ObjectId

from .base_driver import BaseDriver
from .structures import MongoSchedule


class ScheduleDriver(BaseDriver):
    def __init__(self, host: str, port: str, db_name: str):
        super().__init__(
            host=host,
            port=port,
            db_name=db_name,
            coll_name="schedules",
        )

    def get_schedules(self, filter: dict = {}) -> list[MongoSchedule]:
        schedule_docs = self.get_docs(filter)
        return list(map(MongoSchedule.from_dict, schedule_docs))

    def get_schedule(self, schedule_id: str) -> MongoSchedule:
        schedule_doc = self.get_doc({"_id": ObjectId(schedule_id)})
        return MongoSchedule.from_dict(schedule_doc)

    def create_schedule(self, data: dict) -> str:
        schedule = MongoSchedule.from_dict(data)
        schedule_id = self.create_doc(schedule.to_dict())
        return schedule_id

    def update_schedule(self, schedule_id: str, data: dict) -> None:
        self.update_doc({"_id": ObjectId(schedule_id)}, data)

    # def delete_schedule(self, schedule_id: str) -> None:
    #     schedule = self.get_schedule(schedule_id)
    #     for device_id in schedule.deviceIds:
    #         device_driver.update_device(device_id, data={"parentScheduleId": None})
    #     self.delete_doc({"_id": ObjectId(schedule_id)})
