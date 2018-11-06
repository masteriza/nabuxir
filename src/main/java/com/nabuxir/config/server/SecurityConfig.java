package com.nabuxir.config.server;

//import com.maslen.services.SpringDataUserDetailsService;

import com.nabuxir.service.SpringDataUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final int VALIDITY_SECONDS = 604800;

    @Bean
    public SpringDataUserDetailsService springDataUserDetailsService() {
        return new SpringDataUserDetailsService();
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/api/**");
                //.antMatchers("/html/**");
//                .antMatchers("/js/**")
//                .antMatchers("/fonts/**")
//                .antMatchers("/swagger-ui/index.html")
//                .antMatchers("/test/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("filterz 2");




        http.authorizeRequests()
                //.and().anonymous().disable()
                .antMatchers(
                        "/html/**",
                        "/js/**",
                        "/css/**",
                        "/fonts/**",
                        "/api/**",
                        "/").permitAll()
                .anyRequest().authenticated()
                //.antMatchers("/api/**").hasRole("COMPANY_CREATE")
                //.antMatchers("/api/**").hasAuthority("ROLE_COMPANY_CREATE")
                //.anyRequest().authenticated()
                //.antMatchers("/api/**").authenticated()
                .and().csrf().disable()
                //.and()
                .httpBasic()
                .and()
                .formLogin()
                .loginPage("/html/login.html")
                //.successForwardUrl("/html/loggedIn")
                .failureHandler((request, response, e) -> {
                    if (e instanceof BadCredentialsException) {
                        response.sendRedirect("/login?error");
                    } else {
                        response.sendRedirect("/error");
                    }
                })
                .permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/")
                .permitAll();


    }

}
