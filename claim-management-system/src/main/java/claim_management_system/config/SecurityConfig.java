package claim_management_system.config;




import claim_management_system.entity.User;
import claim_management_system.repository.UserRepo;
import claim_management_system.security.CustomUserDetailsService;
import claim_management_system.service.JwtAuthenticationFilter;
import claim_management_system.service.JwtService;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        
    }
    
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService) {

        return new JwtAuthenticationFilter(
                jwtService, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

        http
        .cors(cors ->
        cors.configurationSource(corsConfigurationSource())
    )
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/api/manager/**")
                    .hasAuthority("ROLE_MANAGER")
                .anyRequest().authenticated()
            )

            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable())

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
    @Bean
    public CommandLineRunner createManagers(
            UserRepo userRepo,
            PasswordEncoder passwordEncoder) {

        return args -> {

            createManager(userRepo, passwordEncoder,
                    "manager1", "Manager@123",
                    "Manager One", "manager1@example.com");

            createManager(userRepo, passwordEncoder,
                    "manager2", "Manager@456",
                    "Manager Two", "manager2@example.com");
        };
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:4200")
        );

        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );

        configuration.setAllowedHeaders(
                List.of("Authorization", "Content-Type")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
    private void createManager(
            UserRepo userRepo,
            PasswordEncoder passwordEncoder,
            String username,
            String password,
            String fullName,
            String email) {

        if (!userRepo.existsByUsername(username)) {

            User manager = new User();

            manager.setUsername(username);
            manager.setPassword(passwordEncoder.encode(password));
            manager.setRole("ROLE_MANAGER");
            manager.setFullName(fullName);
            manager.setEmail(email);

            userRepo.save(manager);

            System.out.println(username + " created successfully!");
        }
    }
}