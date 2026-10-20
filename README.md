# 𝐩𝐫𝐨𝐣𝐞𝐜𝐭𝟏

## How to run the application from IntelliJ IDEA

- VM Options = JVM Options in IntelliJ IDEA. VM stands for Virtual Machine, specifically the Java Virtual Machine (JVM). 
- Anything you pass as a JVM option using -Dkey=value becomes a Java system property, provided it is passed to the JVM correctly.
- VM options beginning with -D create JVM system properties. Java can read them directly using System.getProperty().  
Add VM options in the run configuration: <b>-Ddeveloper.name="swasthik bhat" -Ddeveloper.father.name="suresh bhat" -Ddeveloper.mother.name="shantha bhat" -Ddeveloper.sister.name="swathi bhat"</b>

Imagine you have a Java program that greets a person. Instead of writing the name directly in your code, you pass the name when starting the program.  
While running the Java program you give: <b>java HelloWorld Swasthik</b>, Here:  
- java starts the JVM.
- HelloWorld is the class to run.
- Swasthik is the program argument.
- Your Java code can read that argument using main(String[] args):
- public class HelloWorld { public static void main(String[] args) { System.out.println("Hello, " + args[0]); } }
- Output: Hello, Swasthik  
In Spring Boot these command line arguments are called program arguments. You can pass them in the run configuration in IntelliJ IDEA.  
Add Program arguments in the run configuration: <b>--developer.mother.tongue="tulu" --developer.dream.country="USA"</b>  

An environment variable is a key-value pair provided to your application by the operating system or the run configuration.  
you can pass an environment variable:
Environment variable: DEVELOPER_NAME=swasthik bhat  
Notice that environment variable names conventionally use uppercase letters and underscores.  
Add Environment variables in the run configuration: <b>DEVELOPER_AGE=23;DEVELOPER_GENDER=male</b>

### Open a Terminal to run the frontend application
Open the frontend folder and run: <b>npm run dev</b>  
Then open <b>http://localhost:5173</b>  

## How to run the application directly from the command line
Whatever we specify in the VM/JVM/Java options in the run configuration, we can also specify them in the command line while running the application using -Dspring-boot.run.jvmArguments option.  
Option 1: Everything in one command (recommended)  
<b>DEVELOPER_AGE="23" DEVELOPER_GENDER="male" mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Ddeveloper.name='swasthik bhat' -Ddeveloper.father.name='suresh bhat' -Ddeveloper.mother.name='shantha bhat' -Ddeveloper.sister.name='swathi bhat'" -Dspring-boot.run.arguments="--developer.mother.tongue=tulu --developer.dream.country=USA"</b>  
Option 2: Set environment variables separately  
<b>export DEVELOPER_AGE="23"</b>  
<b>export DEVELOPER_GENDER="male"</b>  
<b>mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Ddeveloper.name='swasthik bhat'" -Dspring-boot.run.arguments="--developer.mother.tongue=tulu --developer.dream.country=USA"</b>  

### Open another Terminal to run the frontend application
Open the frontend folder and run: <b>npm run dev</b>  
Then open <b>http://localhost:5173</b>

## How to do maven clean  
<b>mvn clean</b>  

## How to do maven package  
<b>mvn -Ddeveloper.name="swasthik bhat" -Ddeveloper.father.name="suresh bhat" -Ddeveloper.mother.name="shantha bhat" -Ddeveloper.sister.name="swathi bhat" clean package</b>
This command compiles your code, runs tests (if enabled), and creates the executable JAR.  
- VM options (-D...): You can pass Maven properties during the build, but they won't automatically become JVM properties when the application runs.
- Environment variables (DEVELOPER_AGE, etc.): Not required unless your build process or tests need them.
- Program arguments (--developer.dream.country=USA, etc.): Not required because packaging doesn't normally start your application with those runtime arguments.  

In Java, you use:  
<span style="background-color: yellow;"><b>mvn clean package</b></span>  

In React, you use:  

1) For local development, you can run:  
<span style="background-color: yellow;"><b>cd frontend</b></span>  
<span style="background-color: yellow;"><b>npm install</b></span>  → Installs dependencies listed in package.json and updates package-lock.json if needed.    
<span style="background-color: yellow;"><b>npm run build</b></span> → Runs the build script defined in frontend/package.json.  

2) For your Jenkins build, use:  
<span style="background-color: yellow;"><b>cd frontend</b></span>  
<span style="background-color: yellow;"><b>npm ci</b></span>  → Installs dependencies using package-lock.json without updating the lockfile. Ideal for Jenkins CI builds.  
<span style="background-color: yellow;"><b>npm run build</b></span> → Runs the build script defined in frontend/package.json.  

Why use npm ci in Jenkins? It installs the exact dependency versions recorded in your lockfile, helping make builds consistent and repeatable.  

## Run the packaged application/deployable artifact from the command line after packaging
<b>DEVELOPER_AGE="23" DEVELOPER_GENDER="male" java -Ddeveloper.name="swasthik bhat" -Ddeveloper.father.name="suresh bhat" -Ddeveloper.mother.name="shantha bhat" -Ddeveloper.sister.name="swathi bhat" -jar target/project1-0.0.1-SNAPSHOT.jar --developer.mother.tongue=tulu --developer.dream.country=USA</b>

## Check Java version that you made default in your system
(base) swasthikbhat@Swasthiks-MacBook-Air ~ % java -version  
openjdk version "25.0.4.1" 2026-08-18  
OpenJDK Runtime Environment Homebrew (build 25.0.4.1)  
OpenJDK 64-Bit Server VM Homebrew (build 25.0.4.1, mixed mode, sharing)  

## Check all the Java versions installed in your system
(base) swasthikbhat@Swasthiks-MacBook-Air ~ % /usr/libexec/java_home -V                        
Matching Java Virtual Machines (2):  
25.0.4.1 (arm64) "Homebrew" - "OpenJDK 25.0.4.1" /opt/homebrew/Cellar/openjdk@25/25.0.4.1/libexec/openjdk.jdk/Contents/Home  
22.0.2 (arm64) "Oracle Corporation" - "Java SE 22.0.2" /Library/Java/JavaVirtualMachines/jdk-22.jdk/Contents/Home  
/opt/homebrew/Cellar/openjdk@25/25.0.4.1/libexec/openjdk.jdk/Contents/Home

## Check Maven version installed in your system
Apache Maven 3.10.0 (c43a36b8d67be7e0805a411bc0898af1a51f5472)  
Maven home: /opt/homebrew/Cellar/maven/3.10.0/libexec  
Java version: 25.0.4.1, vendor: Homebrew, runtime: /opt/homebrew/Cellar/openjdk@25/25.0.4.1/libexec/openjdk.jdk/Contents/Home  
Default locale: en_US, platform encoding: UTF-8, time zone: Asia/Kolkata  
OS name: "Mac OS X", version: "27.0.1", arch: "aarch64", family: "Mac"

## How to run the application after creating and pushing the image  
### To check all images locally:  
(base) swasthikbhat@Swasthiks-MacBook-Air ~ % docker images  
IMAGE                     ID             DISK USAGE   CONTENT SIZE   EXTRA   
swasthik18/myapp:23       c4e26d82a006        530MB          133MB        
swasthik18/myapp:latest   c4e26d82a006        530MB          133MB  
### Push the image to the Docker Hub with username=swasthik18 and token/password=check google keep:  
(base) swasthikbhat@Swasthiks-MacBook-Air ~ % docker push swasthik18/myapp:23  
The push refers to repository [docker.io/swasthik18/myapp]  
e8eae6888566: Layer already exists  
aad58af6172b: Already exists  
23: digest: sha256:c4e26d82a006937d211173c76b2cb208da77afd5fa82d518c84d4b5068efb125 size: 856  
### Pull the image from the Docker Hub with username=swasthik18 and token/password=check google keep:  
24: Pulling from swasthik18/myapp  
Digest: sha256:28d88a24b0f2c7d634fbe38cc112b20397993d3e10d5d5c37b0cb615ca0a6fdc  
Status: Image is up to date for swasthik18/myapp:24  
docker.io/swasthik18/myapp:24  
### Run the application inside the container which got pulled from the Docker Hub
(base) swasthikbhat@Swasthiks-MacBook-Air ~ % docker run -d --name project1_container -p 8081:8081 swasthik18/myapp:24  
### Access backend  
http://localhost:8081/settings   
### Access backend  
http://localhost:5143/settings  