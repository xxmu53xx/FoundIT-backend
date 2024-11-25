package com.g4AppDev.FoundIT.repository;

import com.g4AppDev.FoundIT.entity.Point;
import com.g4AppDev.FoundIT.entity.UserEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
	List<Point> findAllByUser(UserEntity user);
	List<Point> findByUser(UserEntity user);
}
