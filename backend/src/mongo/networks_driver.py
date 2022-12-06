from bson import ObjectId

from .base_driver import BaseDriver
from .structures import MongoNetwork


class NetworkDriver(BaseDriver):
    def __init__(self, host: str, port: str, db_name: str):
        super().__init__(
            host=host,
            port=port,
            db_name=db_name,
            coll_name="networks",
        )

    def get_networks(self) -> list[MongoNetwork]:
        network_docs = self.get_docs()
        return list(map(MongoNetwork.from_dict, network_docs))

    def get_network(self, network_id: str) -> MongoNetwork:
        network_doc = self.get_doc({"_id": ObjectId(network_id)})
        return MongoNetwork.from_dict(network_doc)

    def create_network(self, data: dict) -> str:
        network = MongoNetwork.from_dict(data)
        network_id = self.create_doc(network.to_dict())
        return network_id

    def update_network(self, network_id: str, data: dict) -> None:
        self.update_doc({"_id": ObjectId(network_id)}, data)

    # def delete_network(self, network_id: str) -> None:
    #     network_doc = self.get_doc({"_id": ObjectId(network_id)})
    #     for device_id in network_doc["deviceIds"]:
    #         device_driver.update_device(device_id, data={"parentNetworkId": None})
    #     self.delete_doc({"_id": ObjectId(network_id)})
