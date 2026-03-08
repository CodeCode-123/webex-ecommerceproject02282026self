package com.code.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.code.api.repository.IUsersRepository;
import com.code.api.exception.*;

@Configuration
@EnableWebSecurity
public class ApplicationConfiguration {
	private final IUsersRepository userRepository;
	public ApplicationConfiguration(IUsersRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmailId(username)
        		.orElseThrow(() -> new UsernameNotFoundException("User not found"));
               
    }
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
		
	}

}
