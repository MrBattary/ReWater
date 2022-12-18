from .base import BaseDriver
from utils.errors import AlreadyActivatedDeviceError


class AddressDriver(BaseDriver):
    def __init__(self, host: str, port: str, db_name: str):
        super().__init__(
            host=host,
            port=port,
            db_name=db_name,
            coll_name="addresses",
        )

    def create_address(self, data: dict) -> str:
        address_doc = self._get_doc({"deviceHardcodedId": data["deviceHardcodedId"]})
        if not address_doc:
            address_id = self._create_doc(data)
        else:
            # raise AlreadyActivatedDeviceError()
            address_id = address_doc["_id"]
            self._update_doc(
                {"deviceHardcodedId": data["deviceHardcodedId"]}, {"$set": data}
            )
        return str(address_id)

    def get_field(self, hardcoded_id: str, field: str) -> str:
        address_doc = self._get_doc({"deviceHardcodedId": hardcoded_id})
        return str(address_doc[field])
