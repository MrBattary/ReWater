# Control Server

## Pre-setup

Install `docker` and `docker-compose`:

```sh
sudo apt-get update
sudo apt-get isntall docker docker-compose
```

## Run

Run service with command (do it in the directory with `Dockerfile`):

```sh
docker-compose up
```

To stop, `Ctrl+C` in console.

## Service Components

### Web-server

Server is deployed on `30001` port.

To check interactive API docs, `http://0.0.0.0:30001/docs`.

### MongoDB

Just a typical MongoDB deployed on `27017`.

### Mongo-express

Web interface for MongoDB deployed on `8081`.

To check it, `http://0.0.0.0/8081`.