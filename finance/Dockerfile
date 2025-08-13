# Use an official Gradle image to build the application
FROM gradle:7.6.0-jdk19 AS build

# Set the working directory inside the container
WORKDIR /home/gradle/project

# Copy the Gradle wrapper and project files
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .
COPY src/ src/

# Ensure the Gradle wrapper is executable
RUN chmod +x gradlew

# Build the application
RUN ./gradlew --no-daemon build

# Use an official OpenJDK image to run the application
FROM openjdk:19-jdk
# Set the working directory inside the container
WORKDIR /eseralew_photo

# Copy the jar file from the build stage
COPY --from=build /home/gradle/project/build/libs/*.jar eseralew_photo.jar


# Expose the port the application runs on
EXPOSE 7005

# Run the application with the specified SSL configuration
ENTRYPOINT ["java", "-jar", "eseralew_photo.jar"]
