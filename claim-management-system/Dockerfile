
# Stage 1: Build the Spring Boot application
FROM eclipse-temurin:27-jdk AS build

WORKDIR /app

# Copy Maven wrapper and project configuration
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Give Maven wrapper execution permission
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy application source code
COPY src/ src/

# Build executable JAR
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:27-jre

WORKDIR /app

COPY --from=build /app/target/claim-management-system-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
