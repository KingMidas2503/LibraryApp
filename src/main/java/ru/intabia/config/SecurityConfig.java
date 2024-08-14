package ru.intabia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String jwkSetUri;

    @Bean
    public JwtAuthenticationConverter customJwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakJwtConverter());
        return converter;
    }

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri + "/protocol/openid-connect/certs").build();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(HttpMethod.PUT, "/api/book/*").hasAnyRole(Role.READER.value, Role.LIBRARIAN.value)
                        .requestMatchers("/admin/librarian/*").hasRole(Role.LIBRARIAN.value)
                        .requestMatchers("/admin/library/*").hasAnyRole(Role.READER.value, Role.LIBRARIAN.value)
                        .requestMatchers("admin/reader/*").hasRole(Role.READER.value)
                        .requestMatchers("admin/rent/*").hasAnyRole(Role.LIBRARIAN.value, Role.READER.value)
                        .requestMatchers(HttpMethod.POST).hasRole("Librarian")
                        .requestMatchers(HttpMethod.PUT).hasRole("Librarian")
                        .requestMatchers(HttpMethod.DELETE).hasRole("Librarian")
                        .anyRequest().permitAll()
                ).oauth2Login(withDefaults())
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(customJwtAuthenticationConverter())
                        )
                ).logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl(jwkSetUri + "/protocol/openid-connect/certs")
                )
                .build();
    }

    @Bean
    @SuppressWarnings("unchecked")
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            authorities.forEach(authority -> {
                if (authority instanceof OidcUserAuthority oidcUserAuthority) {
                    OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();
                    Map<String, Object> realmAccess = userInfo.getClaim("realm_access");
                    Collection<String> realmRoles;
                    if (realmAccess != null && (realmRoles = (Collection<String>) realmAccess.get("roles")) != null) {
                        realmRoles.forEach(role -> mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
                    }
                }
            });
            return mappedAuthorities;
        };
    }

}
