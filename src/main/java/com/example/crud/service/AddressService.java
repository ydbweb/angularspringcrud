package com.example.crud.service;

import com.example.crud.model.Address;
import com.example.crud.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing Address entities.
 * Contains business logic and interacts with the AddressRepository.
 */
@Service
public class AddressService {

    private final AddressRepository addressRepository;

    /**
     * Constructor injection for AddressRepository.
     * @param addressRepository The repository for Address entities.
     */
    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    /**
     * Retrieves all addresses from the database.
     * @return A list of all Address entities.
     */
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    /**
     * Retrieves an address by its ID.
     * @param id The ID of the address to retrieve.
     * @return An Optional containing the Address if found, or empty if not found.
     */
    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    /**
     * Creates a new address or updates an existing one.
     * @param address The Address entity to save or update.
     * @return The saved or updated Address entity.
     */
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    /**
     * Deletes an address by its ID.
     * @param id The ID of the address to delete.
     * @return true if the address was found and deleted, false otherwise.
     */
    public boolean deleteAddress(Long id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
