#!/bin/bash
cp build/libs/*.jar discovery-service.jar
java -jar discovery-service.jar --spring.profiles.active=prod &
echo $! >/home/leekyusung/service/discovery/pid.file &