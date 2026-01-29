package kr.java.finalproject.domain.auth.service;

import kr.java.finalproject.domain.auth.entity.RefreshToken;
import kr.java.finalproject.domain.auth.repository.RefreshTokenRepository;
import kr.java.finalproject.global.security.details.CustomUserDetails;
import kr.java.finalproject.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public String refreshAccessToken(String refreshToken) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        // 2. 토큰에서 유저 정보 추출
        Authentication authentication = jwtProvider.getAuthentication(refreshToken);
        Long userId = extractUserId(authentication);

        // 3. Redis(또는 DB)에 저장된 해당 유저의 RefreshToken 가져오기
        RefreshToken savedToken = refreshTokenRepository.findById(String.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("Refresh token not found in storage"));

        // 4. 클라이언트가 보낸 토큰과 저장된 토큰이 일치하는지 대조 (보안 핵심)
        if (!savedToken.getRefreshToken().equals(refreshToken)) {
            refreshTokenRepository.delete(savedToken);
            throw new RuntimeException("Refresh token mismatch");
        }

        // 5. 모든 검증 완료 -> 새로운 Access Token 생성 및 반환
        return jwtProvider.createAccessToken(authentication, userId);
    }

    private Long extractUserId(Authentication authentication) {
        return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
    }
}