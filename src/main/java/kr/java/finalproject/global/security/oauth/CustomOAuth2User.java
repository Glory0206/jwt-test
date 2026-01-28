package kr.java.finalproject.global.security.oauth;

import kr.java.finalproject.domain.user.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
public class CustomOAuth2User implements OAuth2User {

    private final Long userId;                    // ⭐ 우리 시스템 기준
    private final String email;
    private final Map<String, Object> attributes; // OAuth 제공 데이터

    public CustomOAuth2User(User user,
                            Map<String, Object> attributes) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // 지금은 Role 안 쓰니까 빈 컬렉션
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    // Spring Security가 식별자로 쓰는 값
    @Override
    public String getName() {
        return userId.toString();
    }
}
