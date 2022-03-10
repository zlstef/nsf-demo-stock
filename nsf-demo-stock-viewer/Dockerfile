FROM tomcat:7.0.77-jre8

#add application jar
ADD ./nsf-demo-stock-viewer/target/*.war /usr/local/tomcat/webapps/

RUN cd /usr/local/tomcat/webapps && \
    mv nsf*.war ROOT.war && \
    rm -rf ROOT 
    
#add nsf agent jar and config file
RUN mkdir -p /opt/nsf && cd /opt/nsf && \
    wget "http://console.v190808.163skiff.com/download/nsf-agent-v2.5.6-1daf42fe-20190916-162559.jar" -O nsf-agent.jar
ADD nsf.yml /opt/nsf/nsf.yml
 

CMD ["catalina.sh", "run"]
