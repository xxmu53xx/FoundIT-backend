package com.g4AppDev.FoundIT.repository;

import com.g4AppDev.FoundIT.entity.Item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long> {
	long countByStatus(String status);
	
}
