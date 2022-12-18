from pydantic import BaseModel


class BaseHistory(BaseModel):
    time: str
    status: int


class NetworkHistory(BaseHistory):
    schedule: dict


class History(NetworkHistory):
    network: dict
