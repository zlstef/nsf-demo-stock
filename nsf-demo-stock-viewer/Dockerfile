FROM tomcat:7.0.77-jre8

#add application jar
ADD target/nsf*.war /usr/local/tomcat/webapps/

RUN cd /usr/local/tomcat/webapps && \
    mv nsf*.war ROOT.war && \
    rm -rf ROOT 
    
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
    echo "productId=178-prod1" >> napm-agent.properties && \
    echo "service=viewer-wasu1" >> napm-agent.properties

CMD ["catalina.sh", "run"]
