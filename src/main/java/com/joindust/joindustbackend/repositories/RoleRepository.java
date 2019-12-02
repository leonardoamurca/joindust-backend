package com.joindust.joindustbackend.repositories;

import java.util.Optional;

import com.joindust.joindustbackend.models.Role;
import com.joindust.joindustbackend.utils.RoleName;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(RoleName roleName);
}