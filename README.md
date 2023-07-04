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
- plain-java-pub, a plain java simple App agnostic from any framework to publish in a topic.
- plain-java-sub, a plain java simple App agnostic from any framework to subscribe to a queue.
- python-pub, python publisher (later)
- spring-boot-pub, a Spring-Boot App with autoconfigure to publish in a topic/queue (with REST API).
- spring-boot-rabbit-bridge (under construction)
- spring-boot-sub, a Spring-Boot App with autoconfigure to subscribe to a queue.

More project samples can be added later on.

## Solace Administration

The Solace Admin console is accessible from [this URL](http://localhost:8090/#/login)

You can also access the SEMPv2 REST APi through: http://localhost:8090/SEMP/v2/config
(You have to provide Basic Auth admin/admin to use the local docker instance SEMP API)

A details SEMP (v2) API reference can be found [here](https://docs.solace.com/Admin/SEMP/SEMP-API-Ref.htm).

## Scripts

The `scripts` folder contains tooling and scripting for the different broker in the project.

### Rabbit Tooling

The folder `rabbit-tooling` provides two kind of tools in different folders:

- provisioning

  This folder contains bash and python scripts to create the topics and queues for the samples to be run properly.

### Solace Tooling

The folder `solace-tooling` provides two kind of tools in different folders:

- provisioning

  This folder contains bash and python scripts to create the topics and queues for the samples to be run properly.

- sdkperf-jcsmp-8.4.5.19

  The SdkPerf tool is a tool provided by Solace to enable sending or consuming sample messages in a bash. This is the java version which need a JDK to be
  installed to use it.

### Folder provisioning

[Link to specific README.md](scripts/solace-tooling/provisioning/README.md)

To summarize, the current scripts are creating 5 queues
* queue-football
* queue-hockey
* queue-basketball
* queue-handball
* queue-tennis

which each subscribes to their own topics: 

* news/sport/football
* news/sport/hockey
* news/sport/basketball
* news/sport/handball
* news/sport/tennis

### Solace Tool `sdkperf`
The official doc of the sdkperf is [here](https://docs.solace.com/API/SDKPerf/SDKPerf.htm).

Some useful command:

```bash
# Get the help
> ./sdkperf_java.sh
> ./sdkperf_java.sh -h

# Get the advanced help
> ./sdkperf_java.sh -hm

# subscribe to a topic
> ./sdkperf_java.sh -cip=localhost:55554 -cu=admin@default -cp=admin -stl="news/sports/basketball" -md -q

# subscribe to a queue
> ./sdkperf_java.sh -cip=localhost:55554 -cu=admin@default -cp=admin -sql="queue-basketball" -md -q

# publish to a topic
> ./sdkperf_java.sh -cip=localhost:55554 -cu=admin@default -cp=admin -ptl="news/sport/football" -mn=10 -mr=5 -mt=persistent -md

# publish to a topic
> ./sdkperf_java.sh -cip=localhost:55554 -cu=admin@default -cp=admin -pql="queue-basketball" -mn=10 -mr=5 -mt=persistent -md
``` 
> **_IMPORTANT NOTE:_** With the `sdkperf` tool cannot send a specific body, but you can tweak the option to send multiple message and set up a size for each message.

More information [here](https://docs.solace.com/API/SDKPerf/SDKPerf.htm). 

## Use cases: test the code 

Once you created the queues with the [provisioning](#Folder provisioning) part, you can start playing with the samples:

* In each plain-java projects you have a bash script `launcher.sh <name>` at the root to start subscribing or publishing messages.
* Spring-Boot project relies on the `solace-spring-boot-starter` which contain all the dependencies for starting publishing/subscribing to Solace.
* Spring-Boot RabbitMQ contains a Publisher and Subscriber samples for a Solace-Rabbit integration.

To summarize each project:

### Simple Java Queue Subscriber: `plain-java-sub`
Listen to the queue given as parameter to the launcher script.

### Simple Java Topic Publisher: `plain-java-pub`

Sends a "Hello world!" message in the topic given as parameter to the launcher script.

### Spring-Boot Subscriber with autoconfig

Subscribe to the queue defined as property: `subscriber.queueName` in the `application.yml`.
Launch the Spring-Boot app with maven launcher in command line or in your favorite IDE.

### A Spring-Boot Publisher with autoconfig

The App exposes a REST endpoint where you can POST a body in a given topic or queue.
Here is th curl command you can use for publishing messages in topic or queues:

```
curl --location 'http://localhost:8092/solace/spring/publisher/topic?name=news%2Fsport%2Fhandball' \
--header 'Content-Type: application/json' \
--data '{
"message": "hello from curl",
"type": "notification"
}'
```


### A Spring-Boot Rabbit Bridge

The App exposes a REST endpoint where you can POST a body in a given topic or queue.
