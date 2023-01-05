#!/bin/sh
docker build -f Dockerfile -t piotrzaborowski/frostplus:2.0.5p --build-arg ARTIFACT_FILE=FROST-Server.MQTTP-2.0.5+.war .

docker push piotrzaborowski/frostplus:2.0.5p