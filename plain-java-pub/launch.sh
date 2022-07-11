#!/bin/bash

if [ -z $1 ]
then
  echo "Usage: launch.sh <queueName>"
  exit
fi

java -jar target/plain-java-pub-1.0.0-SNAPSHOT-jar-with-dependencies.jar localhost:55554 admin@default admin $1
