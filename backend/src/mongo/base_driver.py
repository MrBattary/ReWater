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

    def get_docs(self) -> list[dict]:
        return self._coll.find({})

    def get_doc(self, filter) -> dict:
        return self._coll.find_one(filter)

    def create_doc(self, data) -> str:
        res = self._coll.insert_one(data)
        created_doc_id = str(res.inserted_id)
        return created_doc_id

    def update_doc(self, filter, data) -> None:
        self._coll.update_one(filter, data)

    def delete_doc(self, filter) -> None:
        self._coll.delete_one(filter)
