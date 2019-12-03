package com.joindust.joindustbackend.repositories;

import com.joindust.joindustbackend.models.Contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}