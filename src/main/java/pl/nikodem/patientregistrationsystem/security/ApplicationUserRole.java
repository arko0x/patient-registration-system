package pl.nikodem.patientregistrationsystem.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public enum ApplicationUserRole {
    ADMIN(Sets.newHashSet(new SimpleGrantedAuthority("ROLE_ADMIN"))),
    PATIENT(Sets.newHashSet(new SimpleGrantedAuthority("ROLE_PATIENT"))),
    DOCTOR(Sets.newHashSet(new SimpleGrantedAuthority("ROLE_DOCTOR")));

    private final Set<GrantedAuthority> authorities;

    ApplicationUserRole(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public Set<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
