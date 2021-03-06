package org.poc.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class RbacConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Configure security for http request - Role based access to specific endpoints
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/api/file/download").hasRole("ADMIN")
		.antMatchers("/api/**").permitAll()
		.anyRequest().authenticated()
		.and().httpBasic();
	}

	/**
	 * Configure basic authentication with some role
	 * @param AuthenticationManagerBuilder auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("$2a$04$ZTvLZTtcq2l6L60/9/WMdeDTFKqwj5alMZjWo7tbWEQ9D.wLu8tNe").roles("ADMIN")
		.and()
		.withUser("user").password("$2a$04$ZTvLZTtcq2l6L60/9/WMdeDTFKqwj5alMZjWo7tbWEQ9D.wLu8tNe").roles("USER");
	}
	
	@Bean
	public BCryptPasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
}
