FROM maven:3.9.1-eclipse-temurin-17 AS build

COPY . /portail-saisie-back
WORKDIR portail-saisie-back
RUN mvn clean
RUN mvn package


FROM eclipse-temurin:17-jre

WORKDIR ps-back

# Ajoute l'application
COPY --from=build /portail-saisie-back/application/target/*.jar ps_backend.jar

CMD ["java", "-jar", "ps_backend.jar"]

# Test build :
# docker build . -t ps_backend:latest --progress plain

# Run :
# docker run -it -p 8083:8083 -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/ps_db ps_backend:latest
