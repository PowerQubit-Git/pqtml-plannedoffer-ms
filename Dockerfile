FROM openjdk:11
ARG JAR_FILE=pqtml-plannedoffer-ms/target/plannedoffer-ms-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} plannedoffer-ms-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/plannedoffer-ms-0.0.1-SNAPSHOT.jar"]

RUN echo "host.docker.internal:5432:postgres:postgres:Yuri34#$" > ~/.pgpass
RUN echo "host.docker.internal:5433:sae:postgres:Pocosi12" >> ~/.pgpass
RUN echo "localhost:5432:postgres:postgres:Yuri34#$" >> ~/.pgpass
RUN echo "localhost:5433:postgres:sae:Pocosi12" >> ~/.pgpass
RUN chmod 0600 ~/.pgpass
RUN apt-get update && apt-get install -y postgresql-client
EXPOSE 8080


