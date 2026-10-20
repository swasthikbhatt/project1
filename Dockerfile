# FROM specifies the base image on which your Docker image is built. eclipse-temurin provides Java runtime images.
# The image `eclipse-temurin:25-jre` is the Java runtime base image specified in the Dockerfile.
# Docker looks for `eclipse-temurin:25-jre` in the images available locally on your computer.
# If the image is not available locally, Docker downloads it from Docker Hub, assuming the default registry configuration is being used.
# Docker uses this image as the starting point to build your application image, which includes your Spring Boot JAR and the other instructions specified in the Dockerfile.
FROM eclipse-temurin:25-jre

# WORKDIR means: set the working directory. /project1 is simply the folder name chosen by the person writing the Dockerfile. The / at the beginning means the folder is located at the root of the container's filesystem. WORKDIR /project creates and switches to /project inside the container.
WORKDIR /project1

# Source: target/project1-0.0.1-SNAPSHOT.jar — must already exist in your Docker build context.
# Destination: app.jar — Docker creates this file inside the image by copying the source file.
# Docker copies your Spring Boot JAR from your computer's Docker build context into /project1/app.jar inside the image.
COPY target/project1-0.0.1-SNAPSHOT.jar app.jar

# Your Spring Boot application.properties has this setting: server.port=8081. This means your application listens on port 8081 inside the Docker container.
# When you open http://localhost:9090 in your browser, your Mac receives the request on port 9090.
#Since you started the Docker container using docker run -p 9090:8081 swasthik/myapp:latest, Docker knows that requests arriving at your Mac's port 9090 must be forwarded to port 8081 inside the container.
# Inside the container, your Spring Boot application is running and listening on port 8081, as specified by server.port=8081 in your properties file. Docker forwards the request to that application, Spring Boot processes it and sends a response back through Docker to your Mac, and your browser displays that response.
# So, 9090 is the entry point on your Mac, and 8081 is the port where Spring Boot receives the request inside the container.
# First 9090: port on your Mac.
# Second 8081: port inside the container, where Spring Boot listens.
# You can then access your backend at http://localhost:9090
EXPOSE 8081

# ENTRYPOINT tells Docker which command to execute when someone starts a container from your image.
# Here, we write the command as a JSON array, with each part in its own string.
# instead of java -jar app.jar we are writing "java", "-jar", "app.jar"
# Why use this format? #Docker calls this the exec form. It is generally preferred because the application runs directly as the container's main process, which helps it receive stop signals correctly.
ENTRYPOINT ["java", "-jar", "app.jar"]