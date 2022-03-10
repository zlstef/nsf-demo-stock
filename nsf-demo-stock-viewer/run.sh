#!/usr/bin/env bash
#java -javaagent:/opt/nsf/nsf-agent.jar=nsf -javaagent:/opt/apm/napm-java-agent/napm-java-rewriter.jar=conf=napm-agent.properties -jar /opt/nsf/nsf-demo-stock-viewer-1.0-SNAPSHOT.jar
java -javaagent:/opt/nsf/nsf-agent.jar=nsf -javaagent:/opt/apm/skywalking-napm-bin-8.5.0-latest/agent/skywalking-agent.jar -Dskywalking_config=/opt/apm/skywalking-napm-bin-8.5.0-latest/agent/config/agent.config -jar /opt/nsf/nsf-demo-stock-viewer-1.0-SNAPSHOT.jar
