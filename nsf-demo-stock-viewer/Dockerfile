FROM tomcat:7.0.77-jre8

#add application jar
COPY ./nsf-demo-stock-viewer/target/*.jar /opt/nsf/

#add nsf agent jar and config file
RUN mkdir -p /opt/nsf && cd /opt/nsf && \
#    wget "http://console-cust92.netease.com/download/nsf/nsf-agent-v2.6.8-c2a5e1f9-20201203-141827.jar" -O nsf-agent.jar
     wget "http://console.prod.test71.geely.pro.com/download/nsf/nsf-boot.jar" -O nsf-agent.jar
ADD ./nsf-demo-stock-viewer/nsf.yml /opt/nsf/nsf.yml

#add apm agent jar and config file
RUN mkdir -p /opt/apm && cd /opt/apm && \
    wget "http://console.prod.test71.geely.pro.com/download/napm/skywalking-napm-bin-8.5.0-latest.tar.gz" -O skywalking-napm-bin-8.5.0-latest.tar.gz && \
   tar zxvf skywalking-napm-bin-8.5.0-latest.tar.gz
ADD ./nsf-demo-stock-viewer/agent.config /opt/apm/skywalking-napm-bin-8.5.0-latest/agent/config/agent.config

#add run command
ADD ./nsf-demo-stock-viewer/run.sh  /opt/nsf/
RUN chmod a+x /opt/nsf/run.sh

CMD ["/opt/nsf/run.sh"]
