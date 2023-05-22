package hu.alagi.logixspring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/login").permitAll()
                .requestMatchers("/api/addresses/**").hasAuthority(Role.ADDRESS_MANAGER.getAuthority())
                .requestMatchers("/api/transportPlans/**").hasAuthority(Role.TRANSPORT_MANAGER.getAuthority())
                .anyRequest().authenticated();
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        return auth.authenticationProvider(authenticationProvider()).build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(users());
        return daoAuthenticationProvider;
    }

    @Bean
    public UserDetailsService users() {
        final String BAD_COMMON_PASSWORD = "pass123";
        UserDetails addressManagerUser = User.builder()
                .username("aman")
                .password(passwordEncoder().encode(BAD_COMMON_PASSWORD))
                .roles(Role.ADDRESS_MANAGER.getName())
                .build();
        UserDetails transportManagerUser = User.builder()
                .username("tman")
                .password(passwordEncoder().encode(BAD_COMMON_PASSWORD))
                .roles(Role.TRANSPORT_MANAGER.getName())
                .build();
        UserDetails superUser = User.builder()
                .username("super")
                .password(passwordEncoder().encode(BAD_COMMON_PASSWORD))
                .roles(Role.TRANSPORT_MANAGER.getName(), Role.ADDRESS_MANAGER.getName())
                .build();
        return new InMemoryUserDetailsManager(addressManagerUser, transportManagerUser, superUser);
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
