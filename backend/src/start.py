import os

from server import start_api_server
from utils.logger import logger


if __name__ == "__main__":
    try:
        start_api_server()
    except Exception:
        message = "Error during service initialization"
        logger.critical(message, exc_info=True)
        os._exit(1)
