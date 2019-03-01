#!/bin/bash
mvn clean install -DskipTests
sudo mv target/*.war /opt/tomcat-latest/webapps/service-monitor.war