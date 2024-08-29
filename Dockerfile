# Start with a base image containing Java runtime
FROM eclipse-temurin:22-jdk-noble

# Set the working directory to /app
WORKDIR /app

# The application's jar file
ARG JAR_FILE=/target/*.jar

# Add the application's jar to the container
ADD ${JAR_FILE} /app/api.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
CMD ["java","-jar","/app/api.jar"]
