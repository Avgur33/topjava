package ru.javawebinar.topjava;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)

        /*try(GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()){
            appCtx.getEnvironment().setActiveProfiles(Profiles.POSTGRES_DB,Profiles.DATAJPA);
            appCtx.load("spring/spring-app.xml","spring/spring-db.xml");
            appCtx.refresh();
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
        }
*/
        try(ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext(new String[]{"spring/spring-app.xml","spring/spring-db.xml"}, false) ){
            appCtx.getEnvironment().setActiveProfiles(Profiles.POSTGRES_DB,Profiles.JPA);
            appCtx.refresh();
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
        }


        /*ConfigurableApplicationContext appCtx = null;
        try  {
            appCtx = new ClassPathXmlApplicationContext(new String[]{"spring/spring-app.xml","spring/spring-db.xml"}, false);
            appCtx.getEnvironment().setActiveProfiles(Profiles.HSQL_DB,Profiles.JDBC);
            appCtx.refresh();
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
        }finally {
            if (appCtx != null){
                appCtx.close();
            }
        }*/
    }
}
