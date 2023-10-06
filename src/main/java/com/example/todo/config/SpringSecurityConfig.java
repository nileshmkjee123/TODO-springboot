package com.example.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {   httpSecurity.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((authorize)->{
                authorize.requestMatchers(HttpMethod.POST,"/api/**").hasRole("ADMIN");
                authorize.requestMatchers(HttpMethod.PUT,"/api/**").hasRole("ADMIN");
                authorize.requestMatchers(HttpMethod.DELETE,"/api/**").hasRole("ADMIN");
                authorize.requestMatchers(HttpMethod.GET,"/api/**").hasAnyRole("ADMIN","USER");
                authorize.requestMatchers(HttpMethod.PATCH,"/api/**").hasAnyRole("ADMIN","USER");
                authorize.anyRequest().authenticated();
            }).httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }
    @Bean
public static PasswordEncoder passwordEncoder()
{
    return new BCryptPasswordEncoder();
}
    @Bean
    public UserDetailsService userDetailsService()
    {
        UserDetails nil = User.builder().
                username("nil").password(passwordEncoder().encode("nil")).roles("USER").build();
        UserDetails fil = User.builder().
                username("fil").password(passwordEncoder().encode("fil")).roles("ADMIN").build();

        return new InMemoryUserDetailsManager(nil,fil);
    }
}
