package com.example.parking_system.Config;

import com.example.parking_system.Security.CustomerDetailsService;
import com.example.parking_system.Security.FacilityAdminDetailsService;
import com.example.parking_system.Security.SecurityManDetailsService;
import lombok.RequiredArgsConstructor;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

     private final CustomerDetailsService customerDetailsService;
     private final FacilityAdminDetailsService facilityAdminDetailsService;
     private final SecurityManDetailsService securityManDetailsService;


     @Override
     protected void configure(AuthenticationManagerBuilder auth) throws Exception {//use database info
          auth.userDetailsService(customerDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
          auth.userDetailsService(facilityAdminDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
          auth.userDetailsService(securityManDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
     }

     @Override
     protected void configure(HttpSecurity http) throws Exception {// welcome page
          http.csrf().disable()
                  .cors().configurationSource(corsConfigurationSource())
                  .and()
                  .authorizeRequests()

                  // customer url
                  .antMatchers("/api/v1/customers/register").permitAll()
                  .antMatchers("/api/v1/customers/login").permitAll()
                  .antMatchers("/api/v1/customers/all").hasAuthority("admin")
                  .antMatchers("/api/v1/customers").hasAnyAuthority("customer", "admin")

                  // facilities-admins url
                  .antMatchers("/api/v1/facilities-admins/register").permitAll()
                  .antMatchers("/api/v1/facilities-admins/all").hasAuthority("admin")
                  .antMatchers("/api/v1/facilities-admins").hasAnyAuthority("owner", "admin")
                  .antMatchers("/api/v1/facilities-admins/**").hasAnyAuthority("owner", "admin")

                  // facilities url - YES
                  .antMatchers(HttpMethod.GET,"/api/v1/facilities/**").permitAll()
//                  .antMatchers(HttpMethod.GET, "/api/v1/facilities/**").hasAnyAuthority("owner", "admin")
                  .antMatchers(HttpMethod.PUT, "/api/v1/facilities").hasAnyAuthority("owner", "admin")
                  .antMatchers(HttpMethod.DELETE, "/api/v1/facilities/**").hasAnyAuthority("owner", "admin")
                  .antMatchers(HttpMethod.POST, "/api/v1/facilities/**").hasAnyAuthority("owner", "admin")

                  // securities url
                  .antMatchers("/api/v1/securities/register").hasAnyAuthority("owner", "admin")
                  .antMatchers("/api/v1/securities/all").hasAuthority("admin")
                  .antMatchers("/api/v1/securities").hasAnyAuthority("owner", "admin", "security")


                  // parks url
                  .antMatchers(HttpMethod.GET, "/api/v1/parks/**").permitAll()
                  .antMatchers(HttpMethod.POST, "/api/v1/parks/**").hasAuthority("owner")
                  .antMatchers(HttpMethod.PUT, "/api/v1/parks/**").hasAuthority("owner")
                  .antMatchers(HttpMethod.DELETE, "/api/v1/parks/**").hasAuthority("owner")
                  .antMatchers("/api/v1/parks/**").hasAuthority("owner")

                  // cars url
                  .antMatchers("/api/v1/cars/**").hasAuthority("customer")

                  // reservations urls
                  .antMatchers("/api/v1/reservations/parks").permitAll()
                  .antMatchers(HttpMethod.GET,"/api/v1/reservations/**").hasAnyAuthority("customer", "owner", "admin", "security")
                  .antMatchers(HttpMethod.POST,"/api/v1/reservations/**").hasAuthority("customer")
                  .antMatchers(HttpMethod.DELETE,"/api/v1/reservations/**").hasAuthority("customer")
                  .antMatchers(HttpMethod.PUT,"/api/v1/reservations/**")
                    .hasAnyAuthority("customer", "owner", "admin", "security")

                  .anyRequest().authenticated()
                  .and().httpBasic()
                  .authenticationEntryPoint(entryPoint());
     }


     @Bean
     CorsConfigurationSource corsConfigurationSource() {
          UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
          CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
          configuration.setAllowedOrigins(Collections.singletonList("*"));
          configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "OPTIONS"));
          configuration.addAllowedMethod(HttpMethod.TRACE);
          source.registerCorsConfiguration("/**", configuration);
          return source;
     }

     @Bean
     public AuthenticationEntryPoint entryPoint() {

          return new BasicAuthenticationEntryPoint() {
               @Override
               public void commence(HttpServletRequest request, HttpServletResponse response,
                                    AuthenticationException authException) throws IOException {
                    JSONObject jsonObject = new JSONObject();
                    try {
                         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                         response.setContentType("application/json");
                         jsonObject.put("message", authException.getMessage());
                         response.getWriter()
                                 .println(jsonObject);
                    } catch (Exception e) {
                         e.printStackTrace();
                    }
               }

               @Override
               public void afterPropertiesSet() {
                    setRealmName("Contact-Keeper");
                    super.afterPropertiesSet();
               }
          };
     }
}
