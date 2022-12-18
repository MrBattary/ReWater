from http import HTTPStatus


class BaseError(Exception):
    def __init__(self, message: str, error_code: int):
        super().__init__()
        self.message = message
        self.error_code = error_code


class AlreadyActivatedDeviceError(BaseError):
    def __init__(self):
        super().__init__(
            message="This device has been already activated",
            error_code=int(HTTPStatus.FORBIDDEN),
        )
