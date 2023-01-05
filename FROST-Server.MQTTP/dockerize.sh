#!/bin/sh
docker build -f Dockerfile -t piotrzaborowski/frostplus:2.0.5p --build-arg ARTIFACT_FILE=FROST-Server.MQTTP-2.0.5+.war .
#docker buildx build --platform linux/amd64,linux/arm64,linux/arm/v7 -t piotrzaborowski/frostplus:2.0.5pt --build-arg ARTIFACT_FILE=FROST-Server.MQTTP-2.0.5+.war --push .
docker push piotrzaborowski/frostplus:2.0.5p