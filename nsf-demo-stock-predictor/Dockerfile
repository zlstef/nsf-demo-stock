FROM tomcat:7.0.77-jre8


ADD target/nsf*.war /usr/local/tomcat/webapps/

RUN cd /usr/local/tomcat/webapps && \
    mv nsf*.war ROOT.war && \
    rm -rf ROOT 
    
RUN wget "http://dev-test.netease.com/filemanager?mode=download&path=%2Fnsf-agent-builds%2Fnsf-agent-develop-20180906-182201d7-20180908-030558.jar&time=1536390857087" -O nsf-agent.jar

CMD ["catalina.sh", "run"]
