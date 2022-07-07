package com.eazybytes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.Collections;

@Configuration
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * /myAccount - Secured /myBalance - Secured /myLoans - Secured /myCards -
     * Secured /notices - Not Secured /contact - Not Secured
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /**
         * Default configurations which will secure all the requests
         */

        /*
         * http .authorizeRequests() .anyRequest().authenticated() .and()
         * .formLogin().and() .httpBasic();
         */

        /**
         * Custom configurations as per our requirement
         */


//        http.authorizeRequests().antMatchers("/myAccount").authenticated()
//                .antMatchers("/myBalance").authenticated()
//                .antMatchers("/myLoans").authenticated()
//                .antMatchers("/myCards").authenticated().antMatchers("/notices").permitAll()
//                .antMatchers("/contact").permitAll().and().formLogin().and().httpBasic();
        http.cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }).and().csrf().ignoringAntMatchers("/contact").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and().
                authorizeRequests().antMatchers("/myAccount").authenticated().antMatchers("/myBalance").authenticated()
                .antMatchers("/myLoans").authenticated().antMatchers("/myCards").authenticated()
                .antMatchers("/user").authenticated().antMatchers("/notices").permitAll()
                .antMatchers("/contact").permitAll().and().httpBasic();

        /**
         * Configuration to deny all the requests
         */

//
//         http .authorizeRequests() .anyRequest().denyAll() .and() .formLogin().and()
//         .httpBasic();


        /**
         * Configuration to permit all the requests
         */
//
//		http .authorizeRequests() .anyRequest().permitAll().and() .formLogin().and()
//		 .httpBasic();


    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws
//            Exception {
//        auth.inMemoryAuthentication().withUser("admin").password("12345").authorities
//                        ("admin").and().withUser("user").password("12345").authorities("read").and()
//                .passwordEncoder(NoOpPasswordEncoder.getInstance());
//    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws
//            Exception {
//        InMemoryUserDetailsManager userDetailsService = new
//                InMemoryUserDetailsManager();
//        UserDetails user =
//                User.withUsername("admin").password("12345").authorities("admin").build();
//        UserDetails user1 =
//                User.withUsername("user").password("12345").authorities("read").build();
//        userDetailsService.createUser(user);
//        userDetailsService.createUser(user1);
//        auth.userDetailsService(userDetailsService);
//    }


//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
