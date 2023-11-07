package com.alijas.gimhaeswim.config;

import com.alijas.gimhaeswim.config.security.SecurityConstants;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
// @PreAuthorize 활성화
// [예제]
// 1. @PreAuthorize("hasRole('USER')")
// 2. @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
// 3. @Secured("ROLE_TELLER")
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
//            .exceptionHandling((exceptions) -> exceptions
//                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
//                .accessDeniedHandler(new JwtAccessDeniedHandler())
//            )
//            .csrf(csrf -> csrf
//                    .ignoringRequestMatchers(PathRequest.toH2Console()))
                .csrf(AbstractHttpConfigurer::disable)
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

//            .sessionManagement((session) ->
//                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                            .maximumSessions(1) // 세션 동시성 제어를 위한 설정
//                            .maxSessionsPreventsLogin(true) // 두번째 로그인 거부
//                            .invalidSessionUrl("/invalidSession.htm") // 세션이 만료되었을 때 이동하는 페이지
//            )


                .cors(withDefaults()) // Bean 기본 이름이 corsConfigurationSource
                .authorizeHttpRequests((authorize) ->
                                authorize
                                    .requestMatchers(PathRequest.toH2Console()).permitAll()
                                        .requestMatchers(SecurityConstants.ADMIN).hasRole("ADMIN")
                                        .anyRequest().permitAll()

                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/admin", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
        // RememberMe
//            .rememberMe((remember) -> remember
//                    .rememberMeServices(rememberMeServices)
//            );
        ;
        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers(SecurityConstants.WHITELIST);
//    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
//        configuration.setAllowedOrigins(
//                List.of("http://localhost:8080")
//        );
//        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
//        configuration.addExposedHeader(SecurityConstants.TOKEN_HEADER);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

//    @Bean
//    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
//        TokenBasedRememberMeServices.RememberMeTokenAlgorithm encodingAlgorithm = TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256;
//        TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices(myKey, userDetailsService, encodingAlgorithm);
//        rememberMe.setMatchingAlgorithm(TokenBasedRememberMeServices.RememberMeTokenAlgorithm.MD5);
//        return rememberMe;
//    }


}