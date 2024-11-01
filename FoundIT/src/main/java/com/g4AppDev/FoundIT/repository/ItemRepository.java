package com.g4AppDev.FoundIT.repository;

import com.g4AppDev.FoundIT.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
