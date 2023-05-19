package hu.alagi.logixspring.security;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    TRANSPORT_MANAGER, ADDRESS_MANAGER;

    @Override
    public String getAuthority() {
        return "ROLE_"+name();
    }

    public String getName() {
        return name();
    }
}
