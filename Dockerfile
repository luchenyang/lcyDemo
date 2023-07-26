FROM openjdk:8
ENV LANG "C.UTF-8"
ENV TZ "Asia/Shanghai"
ENV JAVA_APP_NAME "grpc-2b-user.jar"
WORKDIR /srv/apps
COPY target/${JAVA_APP_NAME} .
