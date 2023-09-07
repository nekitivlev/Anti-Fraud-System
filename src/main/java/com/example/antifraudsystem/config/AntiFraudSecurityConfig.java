package com.example.antifraudsystem.config;


import com.example.antifraudsystem.security.authentication.RestAuthenticationEntryPoint;
import com.example.antifraudsystem.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


 /**
 * Defines the security configurations including authentication and authorization strategies.
 */
@EnableWebSecurity
public class AntiFraudSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsService;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    public AntiFraudSecurityConfig(UserDetailsServiceImpl userDetailsService, RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }

    /**
     * Sets up the authentication manager utilizing the user service and password encoding.
     *
     * @param auth AuthenticationManagerBuilder
     * @throws Exception if authentication fails
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    /**
     * Configures HTTP security with authentication entry point, URL based authorization and session management.
     *
     * @param http HttpSecurity
     * @throws Exception if configuration fails
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .mvcMatchers("/api/auth/user", "/actuator/shutdown").permitAll()
                .mvcMatchers("/api/auth/list").hasAnyRole("ADMINISTRATOR", "SUPPORT")
                .mvcMatchers(HttpMethod.POST,"/api/antifraud/transaction").hasRole("MERCHANT")
                .mvcMatchers("/api/auth/**").hasRole("ADMINISTRATOR")
                .mvcMatchers("/api/antifraud/**").hasRole("SUPPORT")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * Defines the password encoder to be used in application.
     *
     * @return a BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
