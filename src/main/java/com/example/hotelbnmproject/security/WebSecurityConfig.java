package com.example.hotelbnmproject.security;




import com.example.hotelbnmproject.entity.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final JWTAuthFilter jwtAuthFilter;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    private static String[] publicRoutes = {"/error","/auth/**"};

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        /**
         * authorizeRequests() --> restricts access based on RequestMatcher implementations
         * authenticated() -> requires that all endpoints called be authenticated before proceeding in the filter chain
         * csrf() --> this is to configure the csrf protection
         * sessionManagement() -> manages the session
         * formLogin() --> this calls the default FormLoginConfigurer class that loads the login page to authenticate
         *                 via username-password and accordingly redirects to corresponding failure or success handlers
         */
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole(Role.HOTEL_MANAGER.name())
                        .requestMatchers("/bookings/**").authenticated()
                        .anyRequest().permitAll())
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exHandlingConfig -> exHandlingConfig.accessDeniedHandler(accessDeniedHandler()));


        return httpSecurity.build();
    }

    /*
    @Bean
    UserDetailsService myInMemoryUserDetailService(){
        UserDetails normalUser = User
                .withUsername("sahil")
                .password(passwordEncoder().encode("sk"))
                .roles("USER")
                .build();

        UserDetails adminUser = User
                .withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(normalUser,adminUser);
    }
     */

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager getAuthenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return (request, response, accessDeniedException) -> {
            handlerExceptionResolver.resolveException(request,response,null,accessDeniedException);
        };
    }
}

