package com.joindust.joindustbackend.repositories;

import com.joindust.joindustbackend.models.Collect;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectRepository extends JpaRepository<Collect, Long> {

  @Query("SELECT c.id, c.price, c.quantity, c.created_at FROM Collect c WHERE c.user.id = :userId")
  Page<Long> findCollectionsByUserId(@Param("userId") Long userId, Pageable pageable);

}