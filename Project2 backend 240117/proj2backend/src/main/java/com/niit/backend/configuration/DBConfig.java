package com.niit.backend.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.niit.backend.model.Blog;
import com.niit.backend.model.Bulletin;
import com.niit.backend.model.Event;
import com.niit.backend.model.Friend;
import com.niit.backend.model.Job;
import com.niit.backend.model.JobApplication;
import com.niit.backend.model.User;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.niit")
public class DBConfig {
	@Bean(name = "dataSource")
	public DataSource getOracleDataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
		dataSource.setUsername("MY1");
		dataSource.setPassword("my");
		return dataSource;
	}

	private Properties getHibernateProperties() {
		Properties connectionProperties = new Properties();
		connectionProperties.put("hibernate.show", "true");
		connectionProperties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		connectionProperties.put("hibernate.hbm2ddl.auto", "update");
		return connectionProperties;
	}

	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {
		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
		sessionBuilder.addProperties(getHibernateProperties());
		sessionBuilder.addAnnotatedClasses(User.class);
		sessionBuilder.addAnnotatedClasses(Job.class);
		 sessionBuilder.addAnnotatedClasses(JobApplication.class);
		 sessionBuilder.addAnnotatedClasses(Friend.class);
		 sessionBuilder.addAnnotatedClasses(Blog.class);
		 sessionBuilder.addAnnotatedClasses(Event.class);
		 sessionBuilder.addAnnotatedClasses(Bulletin.class);
		return sessionBuilder.buildSessionFactory();
	}

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionmanager = new HibernateTransactionManager(sessionFactory);
		return transactionmanager;

	}

}