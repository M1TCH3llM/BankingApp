package com.synergisticit;

import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class BankingappApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingappApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorAware() {
		return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
				.filter(a -> a.isAuthenticated() && !(a instanceof AnonymousAuthenticationToken))
				.map(Authentication::getName).or(() -> Optional.of("system")); // still returns Optional<String>
	}

}

// Create Config File Almost Done 
// Create Controller each entity 
// Create Service each entity
// Maybe Create seperate for each 

// First Role, then User, then Branch , after all is done then go to Customer requires User, then account

// servlet.jsp.jstl
// servlet.jsp.jstl-API
// TOMCAT-JASPER