#!/bin/sh
docker build -f Dockerfile2 -t piotrzaborowski/frostplus:2.0 --build-arg ARTIFACT_DIR=FROST-Server.MQTTP-2.0.0 .

docker push piotrzaborowski/frostplus:2.0
