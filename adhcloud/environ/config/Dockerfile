FROM openjdk:8-jdk-slim-stretch
VOLUME /tmp
RUN apt-get -qq update
RUN apt-get -qq -y install curl
ARG JAR_FILE
ARG JMX_PORT
ENV JMX_PORT_VAR=$JMX_PORT
ADD ${JAR_FILE} app.jar
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -Dcom.sun.management.jmxremote.port=$JMX_PORT_VAR -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -jar /app.jar