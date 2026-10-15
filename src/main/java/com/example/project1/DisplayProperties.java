package com.example.project1;

/**
 * This class demonstrates how to access properties defined in the VM Options section and environment variables in edit configuration.
 */
public class DisplayProperties
{
    /**
     * Displays the properties defined in the VM Options section in edit configuration. These properties can be accessed using System.getProperty() method.
     * -Ddeveloper.name="swasthik bhat" -Ddeveloper.father.name="suresh bhat" -Ddeveloper.mother.name="shantha bhat" -Ddeveloper.sister.name="swathi bhat"
     * VM options beginning with -D create JVM system properties. Java can read them directly using System.getProperty().
     */
    public static void displayVmOptionsOrJvmOptionsOrSystemProperties()
    {
        System.out.println("Developer Name: " + System.getProperty("developer.name"));
        System.out.println("Father Name: " + System.getProperty("developer.father.name"));
        System.out.println("Mother Name: " + System.getProperty("developer.mother.name"));
        System.out.println("Sister Name: " + System.getProperty("developer.sister.name"));

        System.out.println(System.getProperty("user.name"));
        System.out.println(System.getProperty("user.home"));
        System.out.println(System.getProperty("user.dir"));
    }

    /**
     * Displays the environment variables defined in the system. These properties can be accessed using System.getenv() method.
     * DEVELOPER_AGE="23" DEVELOPER_GENDER="male"
     * Environment variables are set at the operating system level and can be accessed by any process running on that system.
     */
    public static void displayEnvironmentVariables()
    {
        System.out.println("Developer Age: " + System.getenv("DEVELOPER_AGE"));
        System.out.println("Developer Gender: " + System.getenv("DEVELOPER_GENDER"));

        System.out.println("USER: " + System.getenv("USER"));
        System.out.println("HOME: " + System.getenv("HOME"));
        System.out.println("Current Directory: " + System.getenv("PWD"));
    }
}