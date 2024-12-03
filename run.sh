#!/bin/bash

CONTAINER_NAME=hotel-occupancy-coding-challenge-container

./mvnw clean package

docker build -t hotel-occupancy .
if [ "$(docker ps -aq -f name=$CONTAINER_NAME)" ]; then
  docker rm -f $CONTAINER_NAME
fi

docker run -p 8080:8080 --name $CONTAINER_NAME hotel-occupancy
