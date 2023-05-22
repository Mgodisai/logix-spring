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

    public static Role getRoleValue(String stringValue) {
        for(Role value : values())
            if(value.getName().equalsIgnoreCase(stringValue.replace("ROLE_","")))  {
                return value;
            }
        throw new IllegalArgumentException();
    }


}
