package com.vector.init;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages={"com.vector.service","com.vector.dao"})
@EnableTransactionManagement
@PropertySource("classpath:applicationTest.properties")
public class WebAppConfigTest {

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
    private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
     
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
//    private static final String PROPERTY_NAME_HIBERNATE_AUTOCOMMIT = "hibernate.connection.autocommit";
    
    @Resource
    private Environment env;
    
    private Properties hibernateProp(){
    	Properties properties = new Properties();
    	
    	properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
    	properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
//    	properties.put(PROPERTY_NAME_HIBERNATE_AUTOCOMMIT, env.getProperty(PROPERTY_NAME_HIBERNATE_AUTOCOMMIT));
    	
    	return properties;
    }
    
    @Bean
    public DataSource dataSource(){
    	
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
    	
    	dataSource.setDriverClassName(env.getProperty(PROPERTY_NAME_DATABASE_DRIVER));
    	dataSource.setUrl(env.getProperty(PROPERTY_NAME_DATABASE_URL));
    	dataSource.setUsername(env.getProperty(PROPERTY_NAME_DATABASE_USERNAME));
    	dataSource.setPassword(env.getProperty(PROPERTY_NAME_DATABASE_PASSWORD));
    	
    	return dataSource;
    }
    
    @Bean
    public LocalSessionFactoryBean sessionFactory(){
    	
    	LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
    	
    	sessionFactoryBean.setDataSource(dataSource());
    	sessionFactoryBean.setPackagesToScan(env.getProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
    	sessionFactoryBean.setHibernateProperties(hibernateProp());
    	
    	return sessionFactoryBean;
    }
    
    @Bean
    public HibernateTransactionManager transactionManager(){
    	
    	HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    	transactionManager.setSessionFactory(sessionFactory().getObject());
    	
    	return transactionManager;
    }
}
