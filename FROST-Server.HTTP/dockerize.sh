#!/bin/sh
cp ../Plugins/FROST-Server-PLUS/target/FROST-Server.Plugin.PLUS-2.0.0.jar target/FROST-Server.HTTP-2.0.0/WEB-INF/lib/

docker build -f Dockerfile2 -t piotrzaborowski/frostplus:2.0 --platform=linux/arm64 --build-arg ARTIFACT_DIR=FROST-Server.HTTP-2.0.0 .

docker push piotrzaborowski/frostplus:2.0
