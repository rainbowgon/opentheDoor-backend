#!/bin/bash

STOP_LOG="$ROOT_PATH/stop.log"
CONTAINER="app_container"
IMAGE="app_image"

if sudo docker container inspect "$CONTAINER" >/dev/null 2>&1; then
    echo "container exists locally" >> $STOP_LOG
    sudo docker stop "$CONTAINER"
    sudo docker rm "$CONTAINER"
else
    echo "container does not exist locally" >> $STOP_LOG
fi
if sudo docker image inspect "$IMAGE" >/dev/null 2>&1; then
    echo "Image exists locally" >> $STOP_LOG
    sudo docker rmi "$IMAGE"
else
    echo "Image does not exist locally" >> $STOP_LOG
fi
