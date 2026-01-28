package kr.java.finalproject.global.security.oauth;

import kr.java.finalproject.domain.user.entity.User;
import kr.java.finalproject.domain.user.repository.UserRepository;
import kr.java.finalproject.global.security.oauth.dto.GoogleUserInfo;
import kr.java.finalproject.global.security.oauth.dto.KakaoUserInfo;
import kr.java.finalproject.global.security.oauth.dto.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService
        extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        OAuth2User oauth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo userInfo = switch (registrationId) {
            case "google" -> new GoogleUserInfo(oauth2User.getAttributes());
            case "kakao" -> new KakaoUserInfo(oauth2User.getAttributes());
            default -> throw new RuntimeException("지원하지 않는 제공자입니다.");
        };

        String email = userInfo.getEmail();

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(new User(email)));

        return new CustomOAuth2User(user, oauth2User.getAttributes());
    }
}
