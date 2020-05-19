package com.prokarma.customerdetails.producer.security.resourceserver;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.RestController;
import com.prokarma.customerdetails.producer.security.authserver.CustomAuthenticationFailureHandler;

@EnableResourceServer
@RestController
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	@Override
	public void configure(HttpSecurity http) throws Exception {


		http.antMatcher("/**").authorizeRequests().antMatchers("/oauth/token**", "/login**", "/error**")
				.permitAll().and().authorizeRequests().anyRequest().authenticated().and().formLogin()
				.permitAll();
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.authenticationEntryPoint(customAuthEntryPoint());
	}

	@Bean
	public AuthenticationEntryPoint customAuthEntryPoint() {
		return new CustomAuthenticationFailureHandler();
	}


}
