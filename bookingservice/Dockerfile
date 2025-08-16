FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*


COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

RUN chmod +x mvnw
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT [ "java", "-jar", "app.jar" ]