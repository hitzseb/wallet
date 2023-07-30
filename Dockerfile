FROM amazoncorretto:17
MAINTAINER hitzseb
COPY target/billeterapp-0.0.1-SNAPSHOT.jar billeterapp
ENTRYPOINT ["java", "-jar","billeterapp"]
EXPOSE 8080