from pydantic import BaseModel

from .. import base_models


class NetworkInfoRequest(BaseModel):
    name: str
    description: str


class NetworkInfoResponse(base_models.BaseInfo):
    description: str
    status: base_models.Status
