package kr.java.finalproject.global.security.details;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "userId")
public class CustomUserDetails implements UserDetails {

    private final Long userId;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // 소셜 로그인이라 password 사용 안 함
    @Override
    public String getPassword() {
        return null;
    }

    // Spring Security 내부에서 사용하는 식별자
    @Override
    public String getUsername() {
        return String.valueOf(userId);
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}