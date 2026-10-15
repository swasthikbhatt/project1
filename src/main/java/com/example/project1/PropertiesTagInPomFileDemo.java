package com.example.project1;

/**
 * This class demonstrates how to access properties defined in the pom.xml file using the System.getProperty() method.
 * The properties can be set as JVM VM options when running the application.
 */
public class PropertiesTagInPomFileDemo
{
    /**
     * Displays the properties defined in the pom.xml file properties section. These properties can be accessed using System.getProperty() method.
     * -Ddeveloper.name="swasthik bhat" -Ddeveloper.father.name="suresh bhat" -Ddeveloper.mother.name="shantha bhat" -Ddeveloper.sister.name="shantha bhat"
     * VM options beginning with -D create JVM system properties. Java can read them directly using System.getProperty()
     */
    public static void displayProperties()
    {
        System.out.println("Developer Name: " + System.getProperty("developer.name"));
        System.out.println("Father Name: " + System.getProperty("developer.father.name"));
        System.out.println("Mother Name: " + System.getProperty("developer.mother.name"));
        System.out.println("Sister Name: " + System.getProperty("developer.sister.name"));

        System.out.println(System.getProperty("user.name"));
        System.out.println(System.getProperty("user.home"));
        System.out.println(System.getProperty("user.dir"));
    }
}