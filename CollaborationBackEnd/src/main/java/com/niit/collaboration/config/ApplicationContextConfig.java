package com.niit.collaboration.config;

import java.util.Properties;





import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.niit.collaboration.model.User;



@Configuration
@EnableTransactionManagement

public class ApplicationContextConfig {
	
 @Bean
 public SessionFactory sessionFactory(){
	 
	 LocalSessionFactoryBuilder lsf = new  LocalSessionFactoryBuilder(getDataSource()); 
	 Properties hibernateProperties = new  Properties(); 
	 hibernateProperties.setProperty("hibernate.dialect","org.hibernate.dialect.Oracle11gDialect");
	 hibernateProperties.setProperty("hibernate.hbm2ddl.auto","update");
	 hibernateProperties.setProperty("hibernate.show_sql","true");
	 lsf.addProperties(hibernateProperties);
	 Class Classes[] = {User.class};
	 return lsf.addAnnotatedClasses(Classes).buildSessionFactory();
	 
 }
 
 @Bean
 public DataSource getDataSource(){
	 BasicDataSource datasource  = new BasicDataSource();
	 datasource.setDriverClassName("oracle.jdbc.OracleDriver");
	 datasource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
	 datasource.setUsername("CLOB_DB");
	 datasource.setPassword("root");
	 return datasource;
	 
 }
 
 @Bean
 public HibernateTransactionManager hibTransManagement(){
	 
	 return new HibernateTransactionManager(sessionFactory());  
	 
 }

}
