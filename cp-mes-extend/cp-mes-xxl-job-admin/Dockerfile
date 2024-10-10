FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER cp_mes_xxl_job

RUN mkdir -p /cp-mes/xxljob/logs
RUN mkdir -p /cp-mes/xxljob
RUN mkdir -p /cp-mes/xxljob/temp

WORKDIR /cp-mes/xxljob

ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

EXPOSE 9100

ADD ./target/cp-mes-xxl-job-admin.jar ./app.jar

ENTRYPOINT ["java","-Dspring.profiles.active=prod", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
