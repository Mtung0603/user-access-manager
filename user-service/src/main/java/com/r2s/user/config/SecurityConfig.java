package com.r2s.user.config;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;




@Configuration("userSecurityConfig")
@EnableWebSecurity
    @EnableMethodSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
    public class SecurityConfig {

        private final JwtFilter jwtFilter;
        private final UserDetailsService userDetailsService;



        public SecurityConfig(JwtFilter jwtFilter, UserDetailsService userDetailsService) {
            this.jwtFilter = jwtFilter;
            this.userDetailsService = userDetailsService;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.disable())
                    .exceptionHandling(ex -> ex
                                    .authenticationEntryPoint((request, response, authException) -> {
                                        System.out.println("[AUTH ENTRY POINT] 401 Unauthorized triggered for URL: " + request.getRequestURI());
                                        System.out.println("Exception: " + authException.getClass().getName() + " - " + authException.getMessage());
                                        authException.printStackTrace();  // để xem stack nếu cần
                                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                        response.setContentType("text/plain; charset=UTF-8");
                                        response.getWriter().write("Unauthorized - Vui long dang nhap lai");
                                    })
                                    .accessDeniedHandler((request, response, accessDeniedException) -> {
                                        System.out.println("[ACCESS DENIED HANDLER] 403 Forbidden triggered for URL: " + request.getRequestURI());
                                        System.out.println("Denied reason: " + accessDeniedException.getMessage());
                                        accessDeniedException.printStackTrace();
                                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                        response.setContentType("text/plain; charset=UTF-8");
                                        response.getWriter().write("Access Denied - Ban khong co quyen");
                                    })


                            //})

                    )
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(
                                    "/auth/**",
                                    "/swagger-ui/**",
                                    "/swagger-ui.html",
                                    "/api-docs/**",
                                    "/v3/api-docs/**"
                            ).permitAll()

                            .anyRequest().authenticated()
                    )
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                    // Dòng quan trọng nhất: tắt anonymous filter
                    .anonymous(AbstractHttpConfigurer::disable);

            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }


        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

