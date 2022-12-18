from .base import BaseDriver
from ..structures import MongoHistory


class HistoryDriver(BaseDriver):
    def __init__(self, host: str, port: str, db_name: str):
        super().__init__(
            host=host,
            port=port,
            db_name=db_name,
            coll_name="histories",
        )

    def create_history(self, data: dict) -> None:
        self._create_doc(data)

    def get_histories(self, filter: dict = {}) -> list[MongoHistory]:
        history_docs = self._get_docs(filter)
        return list(map(MongoHistory.from_dict, history_docs))
