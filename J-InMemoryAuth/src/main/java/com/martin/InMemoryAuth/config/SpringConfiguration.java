package com.martin.InMemoryAuth.config;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//Resources
// 1. Create a secure login using an in-memory "admin" user
// https://spring.io/guides/gs/securing-web/
// https://reflectoring.io/spring-security/

// 2. Creating secure endpoints for secure and public pages
// https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html
// https://javadoc.io/doc/org.springframework.security/spring-security-config/latest/org/springframework/security/config/annotation/web/builders/HttpSecurity.html

// 3. Applying a custom logout page
// https://docs.spring.io/spring-security/reference/servlet/authentication/logout.html

// 4. Enabling access to static content
// https://stackoverflow.com/questions/77508907/spring-security-blocks-access-to-styles
// https://stackoverflow.com/questions/56460062/spring-security-not-serving-static-content

@Configuration
@EnableWebSecurity
public class SpringConfiguration {


    @Bean
    public PasswordEncoder passwordEncoder(){                                   // passwordEncoder encrypts the required password
        return  new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService() {                            // create a password administration for admin
        UserDetails adminDetails  = User
                .withUsername("admin")                                          // in-memory username: "admin"
                .password(passwordEncoder().encode("password"))     // in-memory "admin" password: "password"
                .roles("ADMIN")
                .build();

        return  new InMemoryUserDetailsManager(adminDetails);

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{                                            // filters routes between public and administrative pages

        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasRole("ADMIN")                                                    // login user "admin" has access to all content in /admin
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()                           // allow access to static files such as .css
                        .requestMatchers("/", "/index.html", "/about.html", "/contact.html", "/logout.html").permitAll()  // pages that are public facing
                        .anyRequest().denyAll())                                                                                    // all other requests are denied
        .formLogin(form -> form.permitAll().defaultSuccessUrl("/admin/admin.html", true))                  // successful login will route users to /admin/admin.html
        .logout(logout -> logout.logoutSuccessUrl("/logout.html"))                                                                  // successful logout will route users to /logout.html
        .csrf((csrf) -> csrf.disable());                                                                                            //  CSRF protection is disabled in configuration, this is requires to display the custom logout success page

        return http.build();
    }
}