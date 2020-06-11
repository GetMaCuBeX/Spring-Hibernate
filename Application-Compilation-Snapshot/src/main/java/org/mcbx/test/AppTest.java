package org.mcbx.test;

import org.mcbx.controller.AppController;
import org.mcbx.service.AuthorsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.mcbx.entityinterface.AuthorsInterface;

@SpringBootApplication
public class AppTest {

    public static void main(String[] args) {
        SpringApplication.run(AppTest.class, args);

        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        AppController appCtrllr = context.getBean("appController", AppController.class);
        AuthorsService authorsService = context.getBean("authorsService", AuthorsService.class);
        AuthorsInterface authorsContractImpl = authorsService;
        /**/
        System.out.println();

        System.out.println("The Authors object created by spring is : " + authorsService);
        System.out.println("The AppController object created by spring is : " + appCtrllr);
        System.out.println();
        /**/
//        appCtrllr.callAuthorsServiceMethod();
        authorsContractImpl.create();
        /**/
        ((AnnotationConfigApplicationContext) context).close();

    }

}
