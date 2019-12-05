FROM tomcat:7.0.77-jre8

#add application jar
ADD target/*.jar /opt/nsf/

#add nsf agent jar and config file
RUN mkdir -p /opt/nsf && cd /opt/nsf && \
    wget "http://console.v190808.163skiff.com/download/nsf-agent-v2.5.6-1daf42fe-20190916-162559.jar" -O nsf-agent.jar
ADD nsf.yml /opt/nsf/nsf.yml

#add apm agent jar and config file
RUN mkdir -p /opt/apm && cd /opt/apm && \
    wget "http://console.v190808.163skiff.com/download/napm-java-agent-v5.3.3-190825-36860798.tar.gz" -O napm-java-agent.tar.gz && \
    tar zxvf napm-java-agent.tar.gz && \
    cd ./napm-java-agent/conf && \
    echo "endpoint=http://apm-collector.v190808.163skiff.com" >> napm-agent.properties && \
    echo "productId=152-stock-zl" >> napm-agent.properties && \
    echo "service=viewer" >> napm-agent.properties

#add run command
ADD run.sh  /opt/nsf/
RUN chmod a+x /opt/nsf/run.sh

CMD ["/opt/nsf/run.sh"]
