from fastapi import HTTPException, Request
from fastapi.responses import PlainTextResponse


def _custom_http_exception_handler(request: Request, exception: HTTPException):
    return PlainTextResponse(
        content=exception.detail,
        status_code=exception.status_code,
    )


EXCEPTION_HANDLERS = {
    HTTPException: _custom_http_exception_handler,
}
