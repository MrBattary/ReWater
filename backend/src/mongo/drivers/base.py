from pymongo import MongoClient


class BaseDriver:
    def __init__(
        self,
        host: str,
        port: str,
        db_name: str,
        coll_name: str,
    ):
        client = MongoClient(
            host=host,
            port=port,
            username="admin",
            password="admin",
        )
        db = client[db_name]
        self._coll = db[coll_name]

    def _get_docs(self, filter: dict = {}) -> list[dict]:
        return self._coll.find(filter)

    def _get_doc(self, filter) -> dict:
        return self._coll.find_one(filter)

    def _create_doc(self, data) -> str:
        res = self._coll.insert_one(data)
        created_doc_id = str(res.inserted_id)
        return created_doc_id

    def _update_doc(self, filter, data) -> None:
        self._coll.update_one(filter, data)

    def _delete_doc(self, filter) -> None:
        self._coll.delete_one(filter)
