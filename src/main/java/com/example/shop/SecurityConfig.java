package com.example.shop;

import com.example.shop.model.entity.User;
import com.example.shop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner createAdmin(UserRepository userRepository){
        return  args -> {
            if(userRepository.findAll().isEmpty()) {
                User user = new User("admin", bCryptPasswordEncoder().encode("admin"));
                userRepository.save(user);
            }
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic().and()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/",
                        "/images/**",
                        "/js/**",
                        "/styles/**",
                        "/*.html",
                        "/favicon.ico",
                        "/api/common/**"
                ).permitAll()
                .antMatchers("/admin/system/state/*", "/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**")
                .hasAuthority("ADMIN")
                .anyRequest().authenticated();


    }





}
