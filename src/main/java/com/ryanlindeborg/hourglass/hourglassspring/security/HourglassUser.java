package com.ryanlindeborg.hourglass.hourglassspring.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class HourglassUser extends User {
    private Long minJwtIssuedTimestamp;

    public HourglassUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long minJwtIssuedTimestamp) {
        super(username, password, authorities);
        this.minJwtIssuedTimestamp = minJwtIssuedTimestamp;
    }

    public HourglassUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Long minJwtIssuedTimestamp) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.minJwtIssuedTimestamp = minJwtIssuedTimestamp;
    }

    public Long getMinJwtIssuedTimestamp() {
        return minJwtIssuedTimestamp;
    }

    public static HourglassUserBuilder hourglassUserBuilder() {
        return new HourglassUserBuilder();
    }

    public static class HourglassUserBuilder {
        private String username;
        private String password;
        private List<GrantedAuthority> authorities;
        private boolean accountExpired;
        private boolean accountLocked;
        private boolean credentialsExpired;
        private boolean disabled;
        private Function<String, String> passwordEncoder = password -> password;
        private Long minJwtIssuedTimestamp;

        private HourglassUserBuilder() {
        }

        public HourglassUserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public HourglassUserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public HourglassUserBuilder passwordEncoder(Function<String, String> encoder) {
            this.passwordEncoder = passwordEncoder;
            return this;
        }

        public HourglassUserBuilder roles(String... roles) {
            List<GrantedAuthority> authorities = new ArrayList<>(
                    roles.length);
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
            return authorities(authorities);
        }

        public HourglassUserBuilder authorities(GrantedAuthority... authorities) {
            return authorities(Arrays.asList(authorities));
        }

        public HourglassUserBuilder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = new ArrayList<>(authorities);
            return this;
        }

        public HourglassUserBuilder authorities(String... authorities) {
            return authorities(AuthorityUtils.createAuthorityList(authorities));
        }

        public HourglassUserBuilder accountExpired(boolean accountExpired) {
            this.accountExpired = accountExpired;
            return this;
        }

        public HourglassUserBuilder accountLocked(boolean accountLocked) {
            this.accountLocked = accountLocked;
            return this;
        }

        public HourglassUserBuilder credentialsExpired(boolean credentialsExpired) {
            this.credentialsExpired = credentialsExpired;
            return this;
        }

        public HourglassUserBuilder disabled(boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        public HourglassUserBuilder minJwtIssuedTimestamp(Long minJwtIssuedTimestamp) {
            this.minJwtIssuedTimestamp = minJwtIssuedTimestamp;
            return this;
        }

        public HourglassUser build() {
            String encodedPassword = this.passwordEncoder.apply(password);
            return new HourglassUser(username, encodedPassword, !disabled, !accountExpired,
                    !credentialsExpired, !accountLocked, authorities, minJwtIssuedTimestamp);
        }
    }
}
