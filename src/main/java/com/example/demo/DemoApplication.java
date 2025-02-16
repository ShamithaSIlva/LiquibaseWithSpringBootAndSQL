package com.example.demo;

import com.example.demo.service.LiquibaseRollbackService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args)
	{
		//SpringApplication.run(DemoApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

		// Get the LiquibaseRollbackService bean
		LiquibaseRollbackService rollbackService = context.getBean(LiquibaseRollbackService.class);

		// Perform the rollback
		rollbackService.rollbackToTag();

		// Close the context if needed (optional)
		context.close();
	}
}
