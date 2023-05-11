package timesheet.configuration;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import timesheet.util.constants.HeaderConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  protected UserDetailsService myUserDetailsService;

  @Autowired
  protected JwtRequestFilter jwtRequestFilter;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(myUserDetailsService);
    //.passwordEncoder(passwordEncoder());
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
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .cors().disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
        .antMatchers("/**").permitAll()
        .anyRequest().authenticated()
        .and().exceptionHandling()
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().logout().permitAll().clearAuthentication(true).invalidateHttpSession(true);

    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.setAllowedOrigins(Collections.singletonList("*"));
    config.setAllowedHeaders(Arrays.asList(
        HeaderConstants.ACCEPT,
        HeaderConstants.AUTHORIZATION,
        HeaderConstants.BROWSER_INFO,
        HeaderConstants.BROWSER_INFO.toLowerCase(),
        HeaderConstants.BROWSER_INFO.toUpperCase(),
        HeaderConstants.CONTENT_TYPE,
        HeaderConstants.CONTENT_TYPE.toLowerCase(),
        HeaderConstants.CONTENT_TYPE.toUpperCase(),
        HeaderConstants.DEVICE,
        HeaderConstants.DEVICE_INFO,
        HeaderConstants.DEVICE_INFO.toLowerCase(),
        HeaderConstants.DEVICE_INFO.toUpperCase(),
        HeaderConstants.FROM,
        HeaderConstants.LANGUAGE,
        HeaderConstants.ORIGIN,
        HeaderConstants.RESPONSE_TOKEN,
        HeaderConstants.RESPONSE_TOKEN.toLowerCase(),
        HeaderConstants.RESPONSE_TOKEN.toUpperCase(),
        HeaderConstants.REQUEST_TOKEN,
        HeaderConstants.REQUEST_TOKEN.toLowerCase(),
        HeaderConstants.REQUEST_TOKEN.toUpperCase()
    ));
    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE"));
    config.setExposedHeaders(Collections.singletonList(HttpHeaders.CONTENT_DISPOSITION));
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

}
