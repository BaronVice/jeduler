package com.bv.pet.jeduler.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.bv.pet.jeduler.entities.user.Role.*;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final SecurityContextRepository securityContextRepository;
    private final String[] publicRoutes = {
            "/jeduler/auth",
    };
    private final String[] adminRoutes = {
            "/jeduler/admin/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
//				.cors(AbstractHttpConfigurer::disable) Create for React requests
                .securityContext(
                        context -> context
                                .securityContextRepository(securityContextRepository)
                )
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(publicRoutes).permitAll()
                                .requestMatchers(adminRoutes).hasAuthority(
                                        ADMIN.name()
                                )
                                .requestMatchers("/jeduler/user-page").hasAnyAuthority(
                                        USER.name(),
                                        ADMIN.name()
                                )
                                .anyRequest().authenticated()
                )
                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                .maximumSessions(6)
                                .maxSessionsPreventsLogin(false)
//							.expiredUrl("/login")
                )
//				.formLogin(
//						form -> form
//								.loginPage("/login")
//								.loginProcessingUrl("/jeduler/auth")
//								.successHandler(customSuccessHandler).permitAll()
//				)
                .logout(
                        form -> form
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID")
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//								.logoutSuccessUrl("/login?logout")
                                .permitAll()
                );

        return http.build();

    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Autowired
    public void configure (AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        auth
                .parentAuthenticationManager(new ProviderManager(provider));
    }
}
