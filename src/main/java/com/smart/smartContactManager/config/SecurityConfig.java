package com.smart.smartContactManager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthenticatedReactiveAuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. UserDetailsService Bean
    @Bean
    public UserDetailsService getUserDetailService() {
        return new UserDetailsServiceImpl();
    }

    // 2. Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3. Authentication Provider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(getUserDetailService());
        //authProvider.setUserDetailsService(getUserDetailService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//    	UserDetails user = User.withDefaultPasswordEncoder()
//    			.username("user")
//    			.password("password")
//    			.roles("USER")
//    			.build();
//    	UserDetails admin = User.withDefaultPasswordEncoder()
//    			.username("user")
//    			.password("password")
//    			.roles("ADMIN")
//    			.build();
//    	return new InMemoryUserDetailsManager(user,admin);
//    }

    // 5. SecurityFilterChain Bean (replaces configure(HttpSecurity))
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	     return 
	    		 http
	    		 .csrf(csrf -> csrf.disable())
	    		 .authorizeHttpRequests(auth ->{
	    			 
	    			 auth.requestMatchers("/user/**").hasRole("USER");
	    			 auth.requestMatchers("/admin/**").hasRole("ADMIN");
	    			 auth.requestMatchers("/**").permitAll();
	    		 })
	    		 .formLogin(formLogin ->
	    		  	formLogin
	    	        .loginPage("/signin")
	    	        .loginProcessingUrl("/do_login")        
	                .defaultSuccessUrl("/user/index")   
	    	        .permitAll()
	    	      )
	    		 .build();
    }
}

