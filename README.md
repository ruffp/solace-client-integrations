# Documentation of the clients sample for Solace integration

## Introduction
This project aims to help all clients connecting to solace with different technologies. 
We recommend to use Spring-Boot as it uses autoconfiguration but other samples can be used in case the client is not compatible with this framework.

We suggest to first keep the [Solace official developer's page](https://www.solace.dev/) as starting point. 

[This page](https://docs.solace.com/API/Developer-Tutorials.htm) has already many resources with different programming language and tutorials, e.g. 
[.NET tutorials](https://tutorials.solace.dev/dotnet).

## Docker compose
This project has a docker compose which contains the Solace broker and also a separate RabbitMQ for experimenting bridging with external broker.
In a later phase there could be other broker added in this compose.
All docker related configuration are located in the ./docker folder. Subfolder can be created to specify custom images. 

## Project list
The current project list is
- plain-java-pub
- plain-java-sub
- python-pub
- spring-boot-pub
- spring-boot-rabbit-bridge
- spring-boot-sub

More project sample can be added later on.

## Solace Administration

## Solace Tooling

### provisioning 

### Tool "sdkperf"

## Use cases



