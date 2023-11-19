package pl.coderslab.sportschool.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private UserDetailsService instructorDetailsService;

    @Autowired
    public void setUserDetailsService(@Qualifier("userService") UserDetailsService userDetailsService,
                                      @Qualifier("instructorService") UserDetailsService instructorDetailsService) {
        this.userDetailsService = userDetailsService;
        this.instructorDetailsService = instructorDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/instructor/**").hasRole("INSTRUCTOR")
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/", "/login", "/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/default")
                .successHandler((request, response, authentication) -> {
                    for (GrantedAuthority auth : authentication.getAuthorities()) {
                        if (auth.getAuthority().equals("ROLE_ADMIN")) {
                            response.sendRedirect("/admin/home");
                        } else if (auth.getAuthority().equals("ROLE_INSTRUCTOR")) {
                            response.sendRedirect("/instructor/home");
                        } else if (auth.getAuthority().equals("ROLE_USER")) {
                            response.sendRedirect("/user/home");
                        }
                    }
                })
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied")
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .userDetailsService(instructorDetailsService).passwordEncoder(passwordEncoder());
    }
}
