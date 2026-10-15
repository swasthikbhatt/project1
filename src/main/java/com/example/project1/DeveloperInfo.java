package com.example.project1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class demonstrates how to access properties defined in the pom.xml file using Spring's @Value annotation.
 * The properties can be set as JVM VM options when running the application.
 * we can directly acess using system.getProperty() method. But we can also use @Value annotation to access the properties defined in the pom.xml file.
 * The @Value annotation is used to inject values into fields in Spring-managed beans. It can be used to inject values from various sources, including properties files, system properties, and environment variables.
 */
@Component
public class DeveloperInfo
{
    @Value("${developer.name}")
    private String developerName;

    @Value("${developer.father.name}")
    private String developerFatherName;

    @Value("${developer.mother.name}")
    private String developerMotherName;

    @Value("${developer.sister.name}")
    private String developerSisterName;

    @Value("${spring.application.name}")
    private String springApplicationName;

    @Value("${server.port}")
    private int serverPort;

    @Value("${vaadin.launch-browser}")
    private boolean vaadinLaunchBrowser;

    public void printDetails()
    {
        System.out.println("Developer Name: " + developerName);
        System.out.println("Father Name: " + developerFatherName);
        System.out.println("Mother Name: " + developerMotherName);
        System.out.println("Sister Name: " + developerSisterName);
        System.out.println("Spring Application Name: " + springApplicationName);
        System.out.println("Server Port: " + serverPort);
        System.out.println("Vaadin Launch Browser: " + vaadinLaunchBrowser);
    }
}