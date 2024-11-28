FROM maven:3.9.9-eclipse-temurin-21-jammy as builder
WORKDIR /app
COPY ./pom.xml .
RUN mvn dependency:go-offline
COPY ./src ./src
RUN mvn clean install

FROM eclipse-temurin:21.0.5_11-jre-jammy as runner
ENV CB_URL=""
ENV CB_USERNAME=""
ENV CB_PASSWORD=""
ENV CB_BUCKET=""
WORKDIR /tmp
COPY --from=builder /app/target/todoapp.jar ./todoapp.jar
ENTRYPOINT ["java", "-jar","/tmp/todoapp.jar"]
EXPOSE 8080