package com.demo.portailsaisie.backend.core.mapping;

import com.demo.portailsaisie.backend.core.domain.Authority;
import com.demo.portailsaisie.backend.core.domain.User;
import com.demo.portailsaisie.backend.core.dto.auth.KeycloakTokenResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Mapper
public interface AuthMapper {
    User map(UserDetails userDetails);

    default List<Authority> mapAuthorities(Collection<? extends GrantedAuthority> collection) {
        if ( collection == null ) {
            return Collections.emptyList();
        }
        List<Authority> list = new ArrayList<Authority>( collection.size() );
        for ( GrantedAuthority grantedAuthority : collection ) {
            list.add(mapAuthority(grantedAuthority));
        }
        return list;
    }

    default Authority mapAuthority(GrantedAuthority grantedAuthority) {
        if ( grantedAuthority == null ) {
            return null;
        }
        Authority authority = new Authority();
        authority.setAuthority( grantedAuthority.getAuthority() );
        return authority;
    }

    @Mapping(target = "expires_in", expression = "java(String.valueOf(response.get(\"expires_in\")))")
    @Mapping(target = "refresh_expires_in", expression = "java(String.valueOf(response.get(\"refresh_expires_in\")))")
    KeycloakTokenResponseDto mapToKeycloakDto(Map<String, String> response);
}
