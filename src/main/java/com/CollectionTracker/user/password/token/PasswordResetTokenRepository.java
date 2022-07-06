package com.CollectionTracker.user.password.token;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{

	Optional<PasswordResetToken> findByToken(String token);

	 @Transactional
	 @Modifying
	 @Query("UPDATE PasswordResetToken c " + "SET c.confirmedAt = ?2 " + "WHERE c.token = ?1")
	int updateConfirmedAt(String token, LocalDateTime now);
}
