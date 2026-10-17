package com.example.project1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class demonstrates how to access properties defined in the VM Options section, environment variables, program arguments, and application.properties file using Spring's @Value annotation.
 * The @Component annotation indicates that this class is a Spring-managed bean, allowing Spring to automatically detect it during component scanning and manage its lifecycle.
 */
@Component
public class PropertiesDisplayUsingSpringValueAnnotation
{
    // VM Options in edit configuration
    @Value("${developer.name:Not provided}") private String developerName;
    @Value("${developer.father.name:Not provided}") private String developerFatherName;
    @Value("${developer.mother.name:Not provided}") private String developerMotherName;
    @Value("${developer.sister.name:Not provided}") private String developerSisterName;

    // Environment Variables in edit configuration
    @Value("${DEVELOPER_AGE:18}") private String developerAge;
    @Value("${DEVELOPER_GENDER:Male}") private String developerGender;

    // Program arguments in edit configuration
    @Value("${developer.mother.tongue:Not provided}") private String motherTongue;
    @Value("${developer.dream.country:Not provided}") private String dreamCountry;
    @Value("${my.program.args[0]:Not provided}") private String myProgramArg1;
    @Value("${my.program.args[1]:Not provided}") private String myProgramArg2;

    // Properties defined in the application.properties file
    @Value("${spring.application.name:Not provided}") private String springApplicationName;
    @Value("${server.port:8080}") private int serverPort;

    public void printDetails()
    {
        System.out.println("\n========== DEVELOPER DETAILS USING SPRING @VALUE ANNOTATION ==========");
        System.out.println("Developer Name: " + developerName);
        System.out.println("Father Name: " + developerFatherName);
        System.out.println("Mother Name: " + developerMotherName);
        System.out.println("Sister Name: " + developerSisterName);
        System.out.println("Developer Age: " + developerAge);
        System.out.println("Developer Gender: " + developerGender);
        System.out.println("Mother Tongue: " + motherTongue);
        System.out.println("Dream Country: " + dreamCountry);
        System.out.println("Program Argument 1: " + myProgramArg1);
        System.out.println("Program Argument 2: " + myProgramArg2);
        System.out.println("Spring Application Name: " + springApplicationName);
        System.out.println("Server Port: " + serverPort);
        System.out.println("=======================================\n");
    }
}