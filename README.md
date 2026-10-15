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

## How to run the application directly from the command line
Whatever we specify in the VM/JVM/Java options in the run configuration, we can also specify them in the command line while running the application using -Dspring-boot.run.jvmArguments option.  
Option 1: Everything in one command (recommended)  
<b>DEVELOPER_AGE="23" DEVELOPER_GENDER="male" mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Ddeveloper.name='swasthik bhat' -Ddeveloper.father.name='suresh bhat' -Ddeveloper.mother.name='shantha bhat' -Ddeveloper.sister.name='swathi bhat'" -Dspring-boot.run.arguments="--developer.mother.tongue=tulu --developer.dream.country=USA"</b>  
Option 2: Set environment variables separately  
<b>export DEVELOPER_AGE="23"</b>  
<b>export DEVELOPER_GENDER="male"</b>  
<b>mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Ddeveloper.name='swasthik bhat'" -Dspring-boot.run.arguments="--developer.mother.tongue=tulu --developer.dream.country=USA"</b>  

## How to do maven clean  
<b>mvn clean</b>  

## How to do maven package  
<b>mvn -Ddeveloper.name="swasthik bhat" -Ddeveloper.father.name="suresh bhat" -Ddeveloper.mother.name="shantha bhat" -Ddeveloper.sister.name="swathi bhat" clean package</b>
This command compiles your code, runs tests (if enabled), and creates the executable JAR.  
- VM options (-D...): You can pass Maven properties during the build, but they won't automatically become JVM properties when the application runs.
- Environment variables (DEVELOPER_AGE, etc.): Not required unless your build process or tests need them.
- Program arguments (--developer.dream.country=USA, etc.): Not required because packaging doesn't normally start your application with those runtime arguments.  

## Run the packaged application/deployable artifact from the command line after packaging
<b>DEVELOPER_AGE="23" DEVELOPER_GENDER="male" java -Ddeveloper.name="swasthik bhat" -Ddeveloper.father.name="suresh bhat" -Ddeveloper.mother.name="shantha bhat" -Ddeveloper.sister.name="swathi bhat" -jar target/project1-0.0.1-SNAPSHOT.jar --developer.mother.tongue=tulu --developer.dream.country=USA</b>