package br.edu.ifpb.tccii.materialzone.config;

import br.edu.ifpb.tccii.materialzone.service.auth.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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

    private final String PROFESSOR_ROLE = "PROFESSOR";
    private final String ALUNO_ROLE = "ALUNO";

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/materiais").permitAll()
                .antMatchers(HttpMethod.POST, "/materiais").hasRole("PROFESSOR")
                .anyRequest().authenticated().and().httpBasic()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"));

        //        http.authorizeRequests()
//                .anyRequest().permitAll()
//                .and().csrf().disable();

//        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .and().httpBasic()
//                .and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());


//        auth.inMemoryAuthentication()
//                .withUser("romulo").password("{noop}soares").roles(ALUNO_ROLE)
//                .and()
//                .withUser("Deus").password("{noop}meajude").roles(ALUNO_ROLE, PROFESSOR_ROLE);
    }
}
