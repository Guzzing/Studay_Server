package org.guzzing.studayserver.domain.auth.jwt;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class CustomUser implements UserDetails {

    private final Long memberId;
    private final String socialId;
    private final List<? extends GrantedAuthority> authorities;

    @Serial
    private static final long serialVersionUID = 0L;

    @Builder
    public CustomUser(Long memberId, String socialId, List<? extends GrantedAuthority> authorities) {
        this.memberId = memberId;
        this.socialId = socialId;
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (CustomUser) obj;
        return Objects.equals(this.memberId, that.memberId) &&
                Objects.equals(this.socialId, that.socialId) &&
                Objects.equals(this.authorities, that.authorities);
    }

    @Override
    public String toString() {
        return "CustomUser[" +
                "memberId=" + memberId + ", " +
                "socialId=" + socialId + ", " +
                "authorities=" + authorities + ']';
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return socialId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
