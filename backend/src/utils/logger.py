import logging


LOG_LEVEL = "INFO"
LOG_FORMAT = '[%(levelname)s] "%(message)s"'


def _create_logger() -> logging.Logger:
    handler = logging.StreamHandler()
    formatter = logging.Formatter(LOG_FORMAT)
    handler.setFormatter(formatter)

    root = logging.getLogger()
    root.addHandler(handler)

    logger = logging.getLogger(__name__)
    logger.setLevel(LOG_LEVEL)

    return logger


logger = _create_logger()
