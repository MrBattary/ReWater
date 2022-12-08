from .base_driver import BaseDriver
from .structures import MongoAddress


class AddressDriver(BaseDriver):
    def __init__(self, host: str, port: str, db_name: str):
        super().__init__(
            host=host,
            port=port,
            db_name=db_name,
            coll_name="addresses",
        )

    def create_address(self, data: dict) -> None:
        address = MongoAddress.from_dict(data)
        address_doc = self.get_doc({"deviceHardcodedId": address.deviceHardcodedId})
        if not address_doc:
            self.create_doc(address.to_dict())

    def get_address(self, hardcoded_id: str) -> str:
        address_doc = self.get_doc({"deviceHardcodedId": hardcoded_id})
        return address_doc["IP"]
