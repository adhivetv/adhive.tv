FROM jetbrains/teamcity-agent
MAINTAINER mikhail@getmanov.name

RUN \
  echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
  add-apt-repository -y ppa:webupd8team/java && \
  apt-get update && \
  apt-get install -y oracle-java8-installer && \
  rm -rf /var/lib/apt/lists/* && \
  rm -rf /var/cache/oracle-jdk8-installer

ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

RUN apt-get update -qq && apt-get install -qqy \
    apt-transport-https \
    ca-certificates \
    curl \
    lxc \
    iptables
RUN curl -sSL https://get.docker.com/ | sh

VOLUME /var/lib/docker
RUN wget https://services.gradle.org/distributions/gradle-4.6-bin.zip && mkdir /opt/gradle &&  unzip -d /opt/gradle gradle-4.6-bin.zip &&\
export PATH=$PATH:/opt/gradle/gradle-4.6/bin && gradle -v

ADD ./wrapdocker.sh /usr/local/bin/wrapdocker.sh
RUN mkdir /etc/docker/ && echo '{ "insecure-registries":["$NEXUS_URL"] }'>/etc/docker/daemon.json
RUN chmod +x /usr/local/bin/wrapdocker.sh

CMD ["wrapdocker.sh"]
