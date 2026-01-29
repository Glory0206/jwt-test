package kr.java.finalproject.global.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.java.finalproject.domain.auth.entity.RefreshToken;
import kr.java.finalproject.domain.auth.repository.RefreshTokenRepository;
import kr.java.finalproject.global.security.jwt.JwtProvider;
import kr.java.finalproject.global.security.oauth.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app.frontend-url:http://localhost:3000}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        CustomOAuth2User principal =
                (CustomOAuth2User) authentication.getPrincipal();

        Long userId = principal.getUserId();

        String refreshToken = jwtProvider.createRefreshToken(authentication, userId);

        refreshTokenRepository.save(new RefreshToken(userId, refreshToken));

        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Lax")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        clearAuthenticationAttributes(request);

        getRedirectStrategy().sendRedirect(request, response, frontendUrl + "/oauth/redirect");
    }
}