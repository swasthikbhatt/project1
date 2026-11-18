package com.example.project1;

import org.springframework.beans.factory.config.BeanDefinition;
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
		// SpringApplication.run() returns the ApplicationContext (here, through the ConfigurableApplicationContext interface). We store that returned context in the variable context. So, context represents your Spring container.
		SpringApplication app = new SpringApplication(Project1Application.class);

		/* You're telling Spring Boot:
		   - If the application needs my.program.args[0], its default value should be the first program argument.
           - If the application needs my.program.args[1], its default value should be the second program argument.
           - If an argument isn't provided, use "Not present".
           For example, suppose you start your application with -> java -jar project1.jar Swaz India
           Here args[0] = Swaz and args[1] = India, since we set app.setDefaultProperties(Map.of("my.program.args[0]", args.length > 0 ? args[0] : "Not present", "my.program.args[1]", args.length > 1 ? args[1] : "Not present"));
		   It will become my.program.args[0] = Swaz and my.program.args[1] = India
		   So, args[0] → Java program argument and my.program.args[0] → A Spring configuration property that you created using setDefaultProperties().

		   always setDefaultProperties() should be present before app.run(), Because Think of it this way:
		   - setDefaultProperties() = Give Spring Boot the configuration it should use.
		   - app.run(args) = Start the Spring Boot application using the configuration provided.
		   When app.run(args) executes, Spring Boot prepares its Environment and creates the ApplicationContext.
		 */
		app.setDefaultProperties(Map.of(
				"my.program.args[0]", args.length > 0 ? args[0] : "Not present",
				"my.program.args[1]", args.length > 1 ? args[1] : "Not present"
		));

		ConfigurableApplicationContext context = app.run(args);

		// context.getBeanDefinitionNames() is a method that returns the names of all bean definitions registered in the Spring container.
		String[] names = context.getBeanDefinitionNames();
		for (String name : names) System.out.println(name);

		// You can also inspect the definition of your particular bean:
		BeanDefinition definition = context.getBeanFactory().getBeanDefinition("propertiesDisplayUsingEnvironmentClass");
		System.out.println("classname -> " + definition.getBeanClassName());
		System.out.println("scope -> " + definition.getScope());

		DisplayProperties.displayVmOptionsOrJvmOptionsOrSystemProperties(); // Display properties defined in the VM/JVM Options using System.getProperty() method.
		DisplayProperties.displayEnvironmentVariables(); // Display environment variables defined in the system using System.getenv() method.
		System.out.println(args.length);

		// Display command line arguments/program arguments passed to the application.
		if (args.length > 0) System.out.println(args[0]);
		if (args.length > 1) System.out.println(args[1]);

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
		// normally, if we store a lambda in a variable, we can execute it by calling: runner.run();
		// But in Spring Boot, you don't need to call runner.run() manually. Spring Boot calls it for you.
		CommandLineRunner runner = args -> {
			System.out.println("Second CommandLineRunner is executing after the application context has started...");
			propertiesDisplayUsingSpringValueAnnotation.printDetails();
			System.out.println("Second CommandLineRunner executed after the application context has started.");
		};
		return runner;
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