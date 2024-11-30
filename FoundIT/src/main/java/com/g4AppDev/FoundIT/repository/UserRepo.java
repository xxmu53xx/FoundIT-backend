package com.g4AppDev.FoundIT.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.g4AppDev.FoundIT.entity.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
	 UserEntity findBySchoolEmail(String schoolEmail);
	 
	 List<UserEntity> findTop5ByOrderByPointsDesc();
	 
	 @Query("SELECT u FROM UserEntity u ORDER BY u.current_points DESC")
	  List<UserEntity> findTop5Bycurrent_points();
}