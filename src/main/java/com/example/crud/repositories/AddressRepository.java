package com.example.crud.repository;

import com.example.crud.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository for the Address entity.
 * Provides standard CRUD operations for Address entities.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    // Standard CRUD methods are inherited from JpaRepository
}