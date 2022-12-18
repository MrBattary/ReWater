from bson import ObjectId

from .base import BaseDriver
from ..structures import MongoNetwork


class NetworkDriver(BaseDriver):
    def __init__(self, host: str, port: str, db_name: str):
        super().__init__(
            host=host,
            port=port,
            db_name=db_name,
            coll_name="networks",
        )

    def get_networks(self, filter: dict = {}) -> list[MongoNetwork]:
        network_docs = self._get_docs(filter)
        return list(map(MongoNetwork.from_dict, network_docs))

    def get_network(self, network_id: str) -> MongoNetwork:
        network_doc = self._get_doc({"_id": ObjectId(network_id)})
        return MongoNetwork.from_dict(network_doc)

    def create_network(self, data: dict) -> str:
        network_id = self._create_doc(data)
        return network_id

    def update_network(self, network_id: str, data: dict) -> None:
        self._update_doc({"_id": ObjectId(network_id)}, data)
