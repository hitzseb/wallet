FROM amazoncorretto:17
MAINTAINER hitzseb
COPY target/wallet-0.0.1-SNAPSHOT.jar wallet
ENTRYPOINT ["java", "-jar","wallet"]
EXPOSE 8000