from bson import ObjectId

from .base import BaseDriver
from ..structures import MongoSchedule


class ScheduleDriver(BaseDriver):
    def __init__(self, host: str, port: str, db_name: str):
        super().__init__(
            host=host,
            port=port,
            db_name=db_name,
            coll_name="schedules",
        )

    def get_schedules(self, filter: dict = {}) -> list[MongoSchedule]:
        schedule_docs = self._get_docs(filter)
        return list(map(MongoSchedule.from_dict, schedule_docs))

    def get_schedule(self, schedule_id: str) -> MongoSchedule:
        schedule_doc = self._get_doc({"_id": ObjectId(schedule_id)})
        return MongoSchedule.from_dict(schedule_doc)

    def create_schedule(self, data: dict) -> str:
        schedule_id = self._create_doc(data)
        return schedule_id

    def update_schedule(self, schedule_id: str, data: dict) -> None:
        self._update_doc({"_id": ObjectId(schedule_id)}, data)
