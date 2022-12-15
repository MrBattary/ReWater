import functools
import traceback
from http import HTTPStatus

from fastapi import HTTPException

from utils.logger import logger


def _service_errors_handler(function):
    @functools.wraps(function)
    def wrapper(*args, **kwargs):
        try:
            return function(*args, **kwargs)
        except Exception:
            message = "Unhandled error during service work"
            logger.error(f"{message}:\n{traceback.format_exc()}")
            raise HTTPException(status_code=int(HTTPStatus.INTERNAL_SERVER_ERROR))

    return wrapper


ERROR_RESPONSES = {
    int(HTTPStatus.INTERNAL_SERVER_ERROR): {
        "content": {
            "text/plain": {
                "schema": {
                    "type": "string",
                    "default": "Error during service work",
                },
            }
        },
    }
}
