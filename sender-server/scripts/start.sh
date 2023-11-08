#!/bin/bash

ROOT_PATH="/home/ubuntu/app"
JAR="$ROOT_PATH/app.jar"

# Spring 에러 코드
CONTAINER="app_container"
IMAGE="app_image"

REGION="ap-northeast-2"
GROUP="ssafy-openthedoor-log-group"
STREAM="apigateway-server-log-stream"

docker build -t "$IMAGE" "$ROOT_PATH"
docker run \
    --log-driver=awslogs \
    --log-opt awslogs-region="$REGION" \
    --log-opt awslogs-group="$GROUP" \
    --log-opt awslogs-stream="$STREAM" \
    -dp 80:80 --name "$CONTAINER" "$IMAGE"