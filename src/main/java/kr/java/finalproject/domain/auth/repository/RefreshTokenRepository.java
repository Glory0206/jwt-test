package kr.java.finalproject.domain.auth.repository;

import kr.java.finalproject.domain.auth.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}