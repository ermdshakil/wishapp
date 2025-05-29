package com.wish.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wish.model.WishStatus;

public interface WishStatusRepository extends JpaRepository<WishStatus, Long> {

	Optional<WishStatus> findByCustomerId(Long id);
}
