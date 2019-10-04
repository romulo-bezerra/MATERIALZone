package br.edu.ifpb.tccii.materialzone.config;

import br.edu.ifpb.tccii.materialzone.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
                .and().csrf().disable();

//                .anyRequest()
//                .authenticated()
//                .antMatchers("/api/material/professor/**").hasRole("PROFESSOR")
//                .antMatchers("/api/material/aluno/**").hasAnyRole("ALUNO", "PROFESSOR")
//                .antMatchers(HttpMethod.PUT, "/api/aluno").hasRole("ALUNO")
//                .antMatchers(HttpMethod.DELETE, "/api/aluno").hasRole("ALUNO")
//                .antMatchers(HttpMethod.PUT, "/api/professor").hasRole("PROFESSOR")
//                .antMatchers(HttpMethod.DELETE, "/api/professor").hasRole("PROFESSOR")
//                .anyRequest().authenticated().and().httpBasic().and()
//                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
//                .and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

}
