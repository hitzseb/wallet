FROM amazoncorretto:17
MAINTAINER hitzseb
COPY target/walletWizardApi-0.0.1-SNAPSHOT.jar walletWizardApi
ENTRYPOINT ["java", "-jar","walletWizardApi"]
EXPOSE 10000