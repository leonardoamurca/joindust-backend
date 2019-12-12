package com.joindust.joindustbackend.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joindust.joindustbackend.models.Collect;

@Repository
public interface CollectRepository extends JpaRepository<Collect, Long> {

  Optional<Collect> findById(Long collectId);

  Page<Collect> findByUserId(Long userId, Pageable pageable);

  long countByUserId(Long userId);
}