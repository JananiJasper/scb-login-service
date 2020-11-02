package com.example.loginservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.example.loginservice.filter.JwtRequestFilter;
import com.example.loginservice.service.JPAUserDetailsService;

//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//@EnableSwagger2
@EnableWebSecurity
public class SecurityContext extends WebSecurityConfigurerAdapter {
	
	private static final HttpMethod AUTH_WHITELIST = null;
	
	@Autowired
	private JPAUserDetailsService myUserDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	
	@Autowired
	CorsConfigurationSource CorsConfiguration;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().disable().csrf().disable().authorizeRequests()
		 .antMatchers(
				 // -- swagger ui
		            "/swagger-resources/**",
		            "/swagger-ui.html",
		            "/v2/api-docs",
		            "/webjars/**",
		             /*Probably not needed*/ "/swagger.json")
		        .permitAll().antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/**/*").denyAll().			
						anyRequest().authenticated()
					.and().
					exceptionHandling().
				and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	

	}
}