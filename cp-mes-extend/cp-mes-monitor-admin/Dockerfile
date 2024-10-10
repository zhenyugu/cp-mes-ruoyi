FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER cp-mes

RUN mkdir -p /cp-mes/monitor/logs

WORKDIR /cp-mes/monitor

EXPOSE 9090

ADD ./target/cp-mes-monitor-admin.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
