package org.mcbx.database.relationship.test;

import java.util.Properties;
import javax.sql.DataSource; 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan; 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan("org.mcbx.database.relationship.test")
@EnableJpaRepositories
@ComponentScan(basePackages = {
    "org.mcbx.database.relationship.test"})
@PropertySource("classpath:datasource.properties")
//To bootstrap the XML into the Spring context, we can use a simple Java configuration file if the application is configured with Java configuration:
//@ImportResource({"classpath:hibernate5Configuration.xml"}) 
public class ConfigRelationship {

    @Value("${mysql.datasource.driverclassname}")
    private String DRIVERCLASSNAME;
    @Value("${mysql.datasource.url}")
    private String URL;
    @Value("${mysql.datasource.username}")
    private String USERNAME;
    @Value("${mysql.datasource.password}")
    private String PASSWORD;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        /*MySQL database we are using*/
        dataSource.setDriverClassName(DRIVERCLASSNAME);
        dataSource.setUrl("jdbc:mysql://localhost:3306/relationship");
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() { 
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setHibernateProperties(hibernateProperties());
        sessionFactory.setPackagesToScan("org.mcbx.database.relationship.test");
        return sessionFactory;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("connection.pool_size", "10");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        hibernateProperties.setProperty("show_sql", "true");
        /*BATCH PROCESSING*/
        hibernateProperties.setProperty("hibernate.jdbc.batch_size", "50");
        hibernateProperties.setProperty("hibernate.order_inserts", "true");
        hibernateProperties.setProperty("hibernate.order_updates", "true");
        /*CACHE*/
        hibernateProperties.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.EhCacheProvider");
        return hibernateProperties;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

}
