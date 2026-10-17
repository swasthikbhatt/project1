package com.example.project1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import java.util.Map;

@SpringBootApplication
public class Project1Application
{
	public static void main(String[] args)
	{
		SpringApplication app = new SpringApplication(Project1Application.class);
		app.setDefaultProperties(Map.of(
				"my.program.args[0]", args.length > 0 ? args[0] : "Not present",
				"my.program.args[1]", args.length > 1 ? args[1] : "Not present"
		));
		ConfigurableApplicationContext context = app.run(args);

		DisplayProperties.displayVmOptionsOrJvmOptionsOrSystemProperties(); // Display properties defined in the VM/JVM Options using System.getProperty() method.
		DisplayProperties.displayEnvironmentVariables(); // Display environment variables defined in the system using System.getenv() method.
		System.out.println(args.length);
		if (args.length > 0) System.out.println(args[0] + "\n" + args[1] + "\n"); // Display command line arguments/program arguments passed to the application.

		// Display properties defined in the pom.xml file using Spring's @Value annotation. context.getBean(DeveloperInfo.class)Retrieves the Spring-managed object with @Value fields injected.
		PropertiesDisplayUsingSpringValueAnnotation developerInfo = context.getBean(PropertiesDisplayUsingSpringValueAnnotation.class);
		developerInfo.printDetails();

		// Display properties defined in the pom.xml file using Spring's Environment class. context.getBean(PropertiesDisplayUsingEnvironmentClass.class)Retrieves the Spring-managed object with Environment class injected.
		PropertiesDisplayUsingEnvironmentClass propertiesDisplayUsingEnvironmentClass = context.getBean(PropertiesDisplayUsingEnvironmentClass.class);
		propertiesDisplayUsingEnvironmentClass.showDetails();
	}

	/**
	 * Suppose you have a piece in your class, and you want the code to execute automatically whenever you start your Spring Boot application.
	 * How can you do that? That's where CommandLineRunner comes in.
	 * What does CommandLineRunner do? Think of it as telling Spring Boot: "After you start my application, please execute this method."
	 * Spring Boot does not guarantee the execution order of your CommandLineRunner beans unless you specify it using the @Order annotation. The lower the order value, the earlier it will be executed.
	 */
	@Bean
	@Order(1)
	CommandLineRunner firstRunner()
	{
		return args ->
		{
			System.out.println("First CommandLineRunner is executing...");
			DisplayProperties.displayVmOptionsOrJvmOptionsOrSystemProperties();
			DisplayProperties.displayEnvironmentVariables();
			System.out.println("First CommandLineRunner executed.");
		};
	}

	/**
	 * CommandLineRunner is an interface provided by Spring Boot that lets you execute some code automatically after the Spring application context has started.
	 * For example, you want to print your developer information when your application starts. Instead of manually retrieving the bean in your main() method, you can use CommandLineRunner.
	 * This tells Spring Boot to run developerInfo.printDetails() during application startup.
	 * Spring injects DeveloperInfo into the firstRunner method.
	 */
	@Bean
    @Order(2)
	CommandLineRunner secondRunner(PropertiesDisplayUsingSpringValueAnnotation propertiesDisplayUsingSpringValueAnnotation)
	{
		return args -> {
			System.out.println("Second CommandLineRunner is executing after the application context has started...");
			propertiesDisplayUsingSpringValueAnnotation.printDetails();
			System.out.println("Second CommandLineRunner executed after the application context has started.");
		};
	}

	/**
	 * CommandLineRunner is an interface provided by Spring Boot that lets you execute some code automatically after the Spring application context has started.
	 * For example, you want to print your developer information when your application starts. Instead of manually retrieving the bean in your main() method, you can use CommandLineRunner.
	 * This tells Spring Boot to run propertiesDisplayUsingEnvironmentClass.showDetails() during application startup.
	 * Spring injects PropertiesDisplayUsingEnvironmentClass into the thirdRunner method.
	 */
	@Bean
	@Order(3)
	CommandLineRunner thirdRunner(PropertiesDisplayUsingEnvironmentClass propertiesDisplayUsingEnvironmentClass)
	{
		return args -> {
			System.out.println("Third CommandLineRunner is executing after the application context has started...");
			propertiesDisplayUsingEnvironmentClass.showDetails();
			System.out.println("Third CommandLineRunner executed after the application context has started.");
		};
	}

	/**
	 * CommandLineRunner is an interface provided by Spring Boot that lets you execute some code automatically after the Spring application context has started.
	 * For example, you want to print your developer information when your application starts. Instead of manually retrieving the bean in your main() method, you can use CommandLineRunner.
	 * This tells Spring Boot to run this method during application startup.
	 */
	@Bean
	@Order(4)
	CommandLineRunner fourthRunner()
	{
		return args ->
		{
			System.out.println("Fourth CommandLineRunner is executing...");
			System.out.println("Number of program arguments: " + args.length);
			if (args.length > 0) System.out.println("Program Argument 1: " + args[0]);
			if (args.length > 1) System.out.println("Program Argument 2: " + args[1]);
			System.out.println("Fourth CommandLineRunner executed.");
		};
	}
}