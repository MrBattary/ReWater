from os import environ


SERVICE_PORT = int(environ.get("SERVICE_PORT", 30001))

MONGO_HOST = environ["MONGO_HOST"]
MONGO_PORT = int(environ.get("MONGO_PORT", 27017))
DB_NAME = environ["DB_NAME"]
