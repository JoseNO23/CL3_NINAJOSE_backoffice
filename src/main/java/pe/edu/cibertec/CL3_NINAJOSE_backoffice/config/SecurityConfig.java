package pe.edu.cibertec.CL3_NINAJOSE_backoffice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.service.impl.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(auth -> auth
                        .requestMatchers("/login", "/users/add", "error").permitAll()
                        .requestMatchers("/users/profile/**").authenticated()  // Permitir a cualquier usuario autenticado
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/users/promote/**", "/users/edit/**", "/users/update/**").hasRole("ADMIN")
                        .requestMatchers("/cars/details/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/cars/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/users/login")  // Página de login
                        .defaultSuccessUrl("/cars/list", true)  // Redirigir a /cars/list después del login
                        .failureUrl("/users/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/users/logout")  // URL para manejar el logout
                        .logoutSuccessUrl("/users/login?logout")  // URL tras logout exitoso
                        .permitAll()
                )
                .exceptionHandling().accessDeniedPage("/access-denied");  // Página para accesos denegados

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

