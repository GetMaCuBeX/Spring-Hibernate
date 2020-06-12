package org.mcbx.database.relationship.test;

import org.mcbx.test.AppTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class AppRelationship {

    public static void main(String[] args) {
        SpringApplication.run(AppTest.class, args); 
        
        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigRelationship.class);
        EmployeeService service = context.getBean("employeeService", EmployeeService.class);

        service.createEmployee();
    }

}
