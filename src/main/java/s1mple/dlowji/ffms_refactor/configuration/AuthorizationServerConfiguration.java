package s1mple.dlowji.ffms_refactor.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import s1mple.dlowji.ffms_refactor.exceptions.CustomAccessDeniedException;
import s1mple.dlowji.ffms_refactor.exceptions.CustomAuthenEntryPointException;
import s1mple.dlowji.ffms_refactor.filters.JwtFilter;

import java.util.Arrays;
import java.util.List;

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
		http.csrf().disable();
		http.cors(Customizer.withDefaults());
		http.httpBasic(Customizer.withDefaults());
		http.authorizeRequests((auth) -> {
			auth.antMatchers("/auth/**").permitAll();
			auth.anyRequest().authenticated();
		});
		http.addFilterAt(jwtFilter, BasicAuthenticationFilter.class);
		http.exceptionHandling((ex) -> {
			ex.accessDeniedHandler(customAccessDeniedException);
			ex.authenticationEntryPoint(customAuthenEntryPointException);
		});

		return http.build();
	}
	@Bean
	public CorsConfigurationSource corsConfiguration() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(List.of("*"));
		corsConfig.setAllowCredentials(true);
		corsConfig.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
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
