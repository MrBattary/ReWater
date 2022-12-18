from pydantic import BaseModel


class ConnectionRequest(BaseModel):
    IP: str
    deviceHardcodedId: str
