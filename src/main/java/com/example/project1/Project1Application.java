package com.example.project1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Project1Application
{
	public static void main(String[] args)
	{
		ConfigurableApplicationContext context = SpringApplication.run(Project1Application.class, args);

		// Display properties defined in the pom.xml file using System.getProperty() method
		new PropertiesTagInPomFileDemo().displayProperties();

		// Display properties defined in the pom.xml file using Spring's @Value annotation. context.getBean(DeveloperInfo.class)Retrieves the Spring-managed object with @Value fields injected.
		DeveloperInfo developerInfo = context.getBean(DeveloperInfo.class);
		developerInfo.printDetails();
	}

	/**
	 * CommandLineRunner is an interface provided by Spring Boot that lets you execute some code automatically after the Spring application context has started.
	 * For example, you want to print your developer information when your application starts. Instead of manually retrieving the bean in your main() method, you can use CommandLineRunner.
	 * This tells Spring Boot to run developerInfo.printDetails() during application startup.
	 * Spring injects DeveloperInfo into the firstRunner method.
	 */
	@Bean
    CommandLineRunner firstRunner(DeveloperInfo developerInfo)
	{
		return args -> {
			System.out.println("First CommandLineRunner is executing after the application context has started.");
			developerInfo.printDetails();
			System.out.println("First CommandLineRunner executed after the application context has started.");
		};
	}

	/**
	 * Suppose you have a piece in your class and you want the code to execute automatically whenever you start your Spring Boot application.
	 * How can you do that? That's where CommandLineRunner comes in.
	 * What does CommandLineRunner do? Think of it as telling Spring Boot: "After you start my application, please execute this method."
	 */
	@Bean
	CommandLineRunner secondRunner()
	{
		return args ->
		{
			System.out.println("Second CommandLineRunner is executing after displaying properties.");
			new PropertiesTagInPomFileDemo().displayProperties();
			System.out.println("Second CommandLineRunner executed after displaying properties.");
		};
	}
}