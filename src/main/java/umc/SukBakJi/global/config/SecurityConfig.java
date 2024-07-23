package umc.SukBakJi.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import umc.SukBakJi.global.security.oauth2.OAuth2UserService;
import umc.SukBakJi.global.security.oauth2.OAuth2SuccessHandler;
import umc.SukBakJi.global.security.jwt.JwtAuthenticationEntryPoint;
import umc.SukBakJi.global.security.jwt.JwtAuthenticationFilter;
import umc.SukBakJi.global.security.jwt.JwtTokenProvider;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final OAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() { // security를 적용하지 않을 리소스
        return web -> web.ignoring()
                .requestMatchers("/error", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // rest api 설정
            .csrf(auth -> auth.disable()) // csrf 비활성화
            .httpBasic(httpBasic -> httpBasic.disable()) // 기본 인증 비활성화
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 안 함

            // 요청 인증 및 인가 설정
            .authorizeHttpRequests(request ->
                    request.requestMatchers("/", "/auth/success").permitAll()
                            .anyRequest().authenticated()
            )

            .formLogin(formLogin -> formLogin
                    .loginPage("/login")
                    .permitAll()
            )

            // oauth2 설정
            .oauth2Login(oauth -> oauth
                    .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                    .successHandler(oAuth2SuccessHandler)
            )

            // jwt 필터 설정
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)

            // 인증 예외 처리
            .exceptionHandling(exceptions ->
                    exceptions.authenticationEntryPoint(jwtAuthenticationEntryPoint)
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
