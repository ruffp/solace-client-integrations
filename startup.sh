#!/bin/bash

docker compose -f ./docker/docker-compose.yml -p solace-integrations up -d --build --force-recreate

# this will setup queues and settings in Solace
./scripts/solace-tooling/provisioning/install.sh

# this will setup queues and settings in RabbitMQ
./scripts/rabbit-tooling/provisioning/install.sh

