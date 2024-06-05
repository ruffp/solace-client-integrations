#!/bin/bash

#docker compose -f ./docker/docker-compose.yml -p solace-integrations up -d --build --force-recreate
docker compose -f ./docker/docker-compose.yml -p solace-integrations up -d --build

# this will setup queues and settings in Solace
(cd ./scripts/solace-tooling/provisioning && bash install.sh)

# this will setup queues and settings in RabbitMQ
(cd ./scripts/rabbit-tooling/provisioning && bash install.sh)

