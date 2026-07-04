package com.ipd.Gestion_Scolaire.repository;

import com.ipd.Gestion_Scolaire.entity.RefreshToken;
import com.ipd.Gestion_Scolaire.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}