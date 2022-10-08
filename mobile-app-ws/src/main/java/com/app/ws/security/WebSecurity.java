package com.app.ws.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.app.ws.service.UserService;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
	private final UserService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public WebSecurity(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
//	@Bean
//	protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
//		
//		// Configure AuthenticationManagerBuilder
//		AuthenticationManagerBuilder authenticationManagerBuilder =
//				http.getSharedObject(AuthenticationManagerBuilder.class);
//		
//		authenticationManagerBuilder
//		.userDetailsService(userDetailsService)
//		.passwordEncoder(bCryptPasswordEncoder);
//		
//		
//		http.csrf().disable()
//		.authorizeRequests()
//		.antMatchers(HttpMethod.POST,SecurityConstants.SIGN_UP_URL)
//		.permitAll()
//		.anyRequest().authenticated().and()
//		.addFilter(new AuthenticationFilter(authenticationManager()));
//		
//		return http.build();
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.POST,SecurityConstants.SIGN_UP_URL)
		.permitAll()
		.antMatchers(HttpMethod.GET,SecurityConstants.VERIFICATION_EMAIL_URL)
		.permitAll()
		.antMatchers(SecurityConstants.H2_CONSOLE)
		.permitAll()
		.anyRequest().authenticated().and()
		.addFilter(getAuthenticationFilter())
		.addFilter(new AuthorizationFilter(authenticationManager()))
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.headers().frameOptions().disable();
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	public AuthenticationFilter getAuthenticationFilter() throws Exception{
		
		final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
		filter.setFilterProcessesUrl("/users/login");
		return filter;
		
	}

}
