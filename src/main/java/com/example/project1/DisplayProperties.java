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
        System.out.println("Developer Name: " + System.getProperty("developer.name", "Not provided"));
        System.out.println("Father Name: " + System.getProperty("developer.father.name", "Not provided"));
        System.out.println("Mother Name: " + System.getProperty("developer.mother.name", "Not provided"));
        System.out.println("Sister Name: " + System.getProperty("developer.sister.name", "Not provided"));

        // In built system properties
        System.out.println(System.getProperty("user.name", "Not provided"));
        System.out.println(System.getProperty("user.home", "Not provided"));
        System.out.println(System.getProperty("user.dir", "Not provided"));
    }

    /**
     * Displays the environment variables defined in the system. These properties can be accessed using System.getenv() method.
     * DEVELOPER_AGE="23" DEVELOPER_GENDER="male"
     * Environment variables are set at the operating system level and can be accessed by any process running on that system.
     */
    public static void displayEnvironmentVariables()
    {
        System.out.println("Developer Age: " + System.getenv().getOrDefault("DEVELOPER_AGE", "Not provided"));
        System.out.println("Developer Gender: " + System.getenv().getOrDefault("DEVELOPER_GENDER", "Not provided"));

        // In built environment variables
        System.out.println("USER: " + System.getenv().getOrDefault("USER", "Not provided"));
        System.out.println("HOME: " + System.getenv().getOrDefault("HOME", "Not provided"));
        System.out.println("Current Directory: " + System.getenv().getOrDefault("PWD", "Not provided"));
    }
}