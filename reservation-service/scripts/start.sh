#!/bin/bash

ROOT_PATH="/home/ubuntu/app"

CONTAINER="app_container"
IMAGE="app_image"

REGION="ap-northeast-2"
GROUP="ssafy-openthedoor-log-group"
STREAM="reservation-service-log-stream"

docker build -t "$IMAGE" "$ROOT_PATH"
docker run \
    --log-driver=awslogs \
    --log-opt awslogs-region="$REGION" \
    --log-opt awslogs-group="$GROUP" \
    --log-opt awslogs-stream="$STREAM" \
    -dp 80:80 --name "$CONTAINER" "$IMAGE"