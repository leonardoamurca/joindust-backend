package com.joindust.joindustbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joindust.joindustbackend.models.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}