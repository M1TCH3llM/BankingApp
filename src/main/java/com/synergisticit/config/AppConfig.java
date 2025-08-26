package com.synergisticit.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

//import com.synergisticit.util.AuditorAwareImpl;

@PropertySource(value="classpath:db.properties")
@Configuration
public class AppConfig {
@Autowired Environment env;
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource  = new DriverManagerDataSource();
		dataSource.setUrl(env.getProperty("url"));
		dataSource.setDriverClassName(env.getProperty("driverClassName"));
		dataSource.setUsername(env.getProperty("un"));
		dataSource.setPassword(env.getProperty("p"));
		
		return dataSource;
	}
	
	
	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource());
		emf.setPackagesToScan("com.synergisticit.domain");
		emf.setJpaProperties(properties());
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		return emf;
		
	}
	
	
	Properties properties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.show_SQL", "true");
		properties.setProperty("hibernate.hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		return properties;
	}
	
	@Bean
	ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		
		return messageSource;
	}
	
//	@Bean
//	public AuditorAware<String> auditAware(){
//		return new AuditorAwareImpl();
//	}
}
