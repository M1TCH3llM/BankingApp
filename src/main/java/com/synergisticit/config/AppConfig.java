package com.synergisticit.config;

import java.util.Collections;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.synergisticit.domain.Role;
import com.synergisticit.domain.User;
import com.synergisticit.repository.RoleRepository;
import com.synergisticit.repository.UserRepository;

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
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
	    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	    resolver.setPrefix("/WEB-INF/jsp/");
	    resolver.setSuffix(".jsp");
	    return resolver;
	}
	
	
	Properties properties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create");
		properties.setProperty("hibernate.show_SQL", "true");
		properties.setProperty("hibernate.hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		return properties;
	}
	
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		
		return messageSource;
	}
	
	 @Bean
	    CommandLineRunner init(RoleRepository roleRepository, 
	                           UserRepository userRepository,
	                           PasswordEncoder encoder) {
	        return args -> {
	            // Ensure roles exist
	            Role adminRole = roleRepository.findByRoleName("ADMIN");
	            if (adminRole == null) {
	                adminRole = new Role();
	                adminRole.setRoleName("ADMIN");
	                roleRepository.save(adminRole);
	            }

	            Role userRole = roleRepository.findByRoleName("USER");
	            if (userRole == null) {
	                userRole = new Role();
	                userRole.setRoleName("USER");
	                roleRepository.save(userRole);
	            }

	            // Ensure admin exists
	            if (userRepository.findByUsername("admin") == null) {
	                User admin = new User();
	                admin.setUsername("admin");
	                admin.setPassword(encoder.encode("admin")); 
	                admin.setEmail("admin@example.com");
	                admin.setRoles(Collections.singletonList(adminRole));
	                userRepository.save(admin);
	            }

	            // Ensure user exists
	            if (userRepository.findByUsername("user") == null) {
	                User basic = new User();
	                basic.setUsername("user");
	                basic.setPassword(encoder.encode("user")); 
	                basic.setEmail("user@example.com");
	                basic.setRoles(Collections.singletonList(userRole));
	                userRepository.save(basic);
	            }
	        };
	    }
	}
	
//	@Bean
//	public AuditorAware<String> auditAware(){
//		return new AuditorAwareImpl();
//	}

