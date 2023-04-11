package com.autmaple.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

//@Configuration
public class SecurityConfig {

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {
        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {
            Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
            Collection<String> roles = realmAccess.get("roles");
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        };
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }


//    @Override
//    protected AccessDecisionManager accessDecisionManager() {
//        RoleVoter roleVoter = new RoleVoter();
//        roleVoter.setRolePrefix("");
//        List<AccessDecisionVoter<?>> decisionVoters = Arrays.asList(roleVoter, new AuthenticatedVoter());
//        return new AffirmativeBased(decisionVoters);
//    }

//    @Bean
//    public RoleVoter roleVoter() {
//        RoleVoter roleVoter = new RoleVoter();
//        roleVoter.setRolePrefix("");
//        return roleVoter;
//    }

//    @Bean
//    public GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
//        return authorities -> authorities.stream()
//                .map(this::removeROLE_Prefix)
//                .collect(Collectors.toList());
//    }
//
//    public GrantedAuthority removeROLE_Prefix(GrantedAuthority authority) {
//        String role = authority.getAuthority();
//        if (role.startsWith("ROLE_")) {
//            role = role.substring(5);
//        }
//        return new SimpleGrantedAuthority(role);
//    }
//
//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {
//        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {
//            Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
//            Collection<String> roles = realmAccess.get("roles");
//            return roles.stream()
//                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//                    .collect(Collectors.toList());
//        };
//
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
//
//        return jwtAuthenticationConverter;
//    }

//    @Bean
//    public JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter() {
//        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
//        converter.setAuthorityPrefix("");
//        return converter;
//    }
}
