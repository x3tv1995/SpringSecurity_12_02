package ru.lesson.springsecurity_12_02.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.lesson.springsecurity_12_02.model.enums.Role;
import ru.lesson.springsecurity_12_02.model.entity.User;
import ru.lesson.springsecurity_12_02.repository.UserRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth  ) throws Exception {
        return auth.getAuthenticationManager();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
       provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
          http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public").permitAll() //Доступ разрешён без аутентификации
                        .requestMatchers("/api/user").hasRole("USER") //Требуется роль юзера
                        .requestMatchers("/api/admin").hasRole("ADMIN")
                        .requestMatchers("/api/create","/api/delete","/api/update").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

          http.cors(AbstractHttpConfigurer::disable);
          http.csrf(AbstractHttpConfigurer::disable);
          http.headers(AbstractHttpConfigurer::disable);
          http.httpBasic(Customizer.withDefaults());



          return http.build();

    }

    @Bean
    public CommandLineRunner createAdmin(){
        return args -> {
            User username = userRepository.findByUsername("Admin100");
            if(username==null){
                User user = new User();
                user.setUsername("Admin100");
                user.setPassword(passwordEncoder().encode("123456"));
                user.setRole(Role.ADMIN);
                userRepository.save(user);
            }
        };
    }
}
