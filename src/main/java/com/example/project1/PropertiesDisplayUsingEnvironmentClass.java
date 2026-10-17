package com.example.project1;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * This class demonstrates how to access properties defined in the VM Options section, environment variables, program arguments, and application.properties file using Spring's Environment class.
 * The @Component annotation indicates that this class is a Spring-managed bean, allowing Spring to automatically detect it during component scanning and manage its lifecycle.
 */
@Component
public class PropertiesDisplayUsingEnvironmentClass
{
    private final Environment environment;

    // Constructor injection
    public PropertiesDisplayUsingEnvironmentClass(Environment environment)
    {
        this.environment = environment;
    }

    public void showDetails()
    {
        // Properties from application.properties / Maven-filtered resources
        String developerName = environment.getProperty("developer.name", "Not provided");
        String fatherName = environment.getProperty("developer.father.name", "Not provided");
        String motherName = environment.getProperty("developer.mother.name", "Not provided");
        String sisterName = environment.getProperty("developer.sister.name", "Not provided");

        // Environment variables, with default values
        String developerAge = environment.getProperty("DEVELOPER_AGE", "18");
        String developerGender = environment.getProperty("DEVELOPER_GENDER", "Male");

        // Spring Boot command-line arguments
        String motherTongue = environment.getProperty("developer.mother.tongue", "Not provided");
        String dreamCountry = environment.getProperty("developer.dream.country", "Not provided");

        // Custom properties populated from the program arguments
        String programArg1 = environment.getProperty("my.program.args[0]", "Not provided");
        String programArg2 = environment.getProperty("my.program.args[1]", "Not provided");

        // Other application properties
        String applicationName = environment.getProperty("spring.application.name", "Not provided");
        String serverPort = environment.getProperty("server.port", "8080");

        System.out.println("\n========== DEVELOPER DETAILS USING ENVIRONMENT CLASS ==========");
        System.out.println("Developer Name: " + developerName);
        System.out.println("Father Name: " + fatherName);
        System.out.println("Mother Name: " + motherName);
        System.out.println("Sister Name: " + sisterName);
        System.out.println("Developer Age: " + developerAge);
        System.out.println("Developer Gender: " + developerGender);
        System.out.println("Mother Tongue: " + motherTongue);
        System.out.println("Dream Country: " + dreamCountry);
        System.out.println("Program Argument 1: " + programArg1);
        System.out.println("Program Argument 2: " + programArg2);
        System.out.println("Application Name: " + applicationName);
        System.out.println("Server Port: " + serverPort);
        System.out.println("=======================================\n");
    }
}