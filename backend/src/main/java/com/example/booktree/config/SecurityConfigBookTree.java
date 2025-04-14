package com.example.booktree.config;


import com.example.booktree.jwt.filter.JwtAuthenticationFilter;
import com.example.booktree.jwt.util.JwtTokenizer;
import com.example.booktree.oauth.handler.CustomOAuth2AuthenticationSuccessHandler;
import com.example.booktree.oauth.resolver.CustomAuthorizationRequestResolver;
import com.example.booktree.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigBookTree {

    private final JwtTokenizer jwtTokenizer;
    private final CustomOAuth2AuthenticationSuccessHandler customOAuth2AuthenticationSuccessHandler;
    private final CustomAuthorizationRequestResolver customAuthorizationRequestResolver;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .headers(headers -> headers.frameOptions(frame -> frame.disable())) // H2 console 용
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/api/oauth/**",
                                "/oauth",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/h2-console/**"
                        ).permitAll()
                        .requestMatchers("/api/v1/posts/create", "/api/v1/posts/patch/**", "/api/v1/posts/delete/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/api/v1/users/get", "/api/v1/users/create","/api/v1/users/login").permitAll()
                        .anyRequest().authenticated()
                )// "/api/v1/posts"
                .oauth2Login(
                        oauth2Login -> {
                            // Configure OAuth2 login
                            oauth2Login
                                    .successHandler(customOAuth2AuthenticationSuccessHandler)
                                    .authorizationEndpoint(
                                            authorizationEndpoint ->
                                                    authorizationEndpoint
                                                            .authorizationRequestResolver(customAuthorizationRequestResolver)
                                    );
                        }
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenizer), UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .cors(cors -> cors.configurationSource(configurationSource()))
                //oauth2 로그인
//                .oauth2Login(
//                        oauth2Login->
//                )
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"));


        return http.build();
    }


    //특정 포트 번호 허락
    public CorsConfigurationSource configurationSource(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**",config);
        return source;

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
