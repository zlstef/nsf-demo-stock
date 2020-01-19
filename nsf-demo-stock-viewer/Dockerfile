FROM tomcat:7.0.77-jre8

#add application jar
ADD ./*/target/nsf*.war /usr/local/tomcat/webapps/

RUN cd /usr/local/tomcat/webapps && \
    mv nsf*.war ROOT.war && \
    rm -rf ROOT 
    
#add nsf agent jar and config file
RUN mkdir -p /opt/nsf && cd /opt/nsf && \
     wget "https://cloud-solution.nos-eastchina1.126.net/nsf-agent.jar" -O nsf-agent.jar
#    wget "http://env7.qingzhou.com/download/nsf-agent-v2.4-190613.jar" -O nsf-agent.jar

ADD ./nsf-demo-stock-viewer/nsf.yml /opt/nsf/nsf.yml

CMD ["catalina.sh", "run"]
