package s1mple.dlowji.ffms_refactor.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import s1mple.dlowji.ffms_refactor.exceptions.CustomAccessDeniedException;
import s1mple.dlowji.ffms_refactor.exceptions.CustomAuthenEntryPointException;
import s1mple.dlowji.ffms_refactor.filters.JwtFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthorizationServerConfiguration {

	@Autowired
	@Lazy
	private JwtFilter jwtFilter;
	@Autowired
	private CustomAccessDeniedException customAccessDeniedException;

	@Autowired
	private CustomAuthenEntryPointException customAuthenEntryPointException;
	@Bean
	public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
//		http.csrf().disable();
//		http.authorizeRequests(auth -> {
//			auth.antMatchers(HttpMethod.POST, "/eventmanagement-api/events").access("hasRole('ADMIN')");
//			auth.antMatchers(HttpMethod.PATCH, "/eventmanagement-api/events/**").access("hasRole('ADMIN')");
//			auth.antMatchers(HttpMethod.PUT, "/eventmanagement-api/events/**").access("hasRole('ADMIN')");
//			auth.antMatchers("/eventmanagement-api/participants/**").access("hasRole('USER')");
//			auth.antMatchers("/eventmanagement-api/events").authenticated();
//			auth.antMatchers("/login").permitAll();
//		});
//		http.addFilterAt(loginFilter, BasicAuthenticationFilter.class);
//		http.addFilterAt(jwtFilter, BasicAuthenticationFilter.class);
//		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		http.exceptionHandling((ex) -> {
//			ex.accessDeniedHandler((request, response, accessDeniedException) -> {
//				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//				response.getWriter().println(accessDeniedException.getMessage());
//			});
//			ex.authenticationEntryPoint((request, response, auth) -> {
//				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//				response.getWriter().println(auth.getMessage());
//			});
//		});
		http.csrf().disable();
		http.authorizeRequests((auth) -> {
			auth.antMatchers("/auth/**").permitAll();

		});
		http.addFilterAt(jwtFilter, BasicAuthenticationFilter.class);
		http.exceptionHandling((ex) -> {
			ex.accessDeniedHandler(customAccessDeniedException);
			ex.authenticationEntryPoint(customAuthenEntryPointException);
		});

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(daoAuthenticationProvider);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
