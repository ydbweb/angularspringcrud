package com.example.crud.controller;

import com.example.crud.model.Address;
import com.example.crud.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Address entities.
 * Handles HTTP requests related to Address CRUD operations.
 */
@RestController
@RequestMapping("/api/addresses") // Base path for all address-related endpoints
public class AddressController {

    private final AddressService addressService;

    /**
     * Constructor injection for AddressService.
     * @param addressService The service for Address entities.
     */
    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * GET /api/addresses : Get all addresses.
     * @return ResponseEntity with a list of Address objects and HTTP status OK.
     */
    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressService.getAllAddresses();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    /**
     * GET /api/addresses/{id} : Get address by ID.
     * @param id The ID of the address to retrieve.
     * @return ResponseEntity with the Address object if found, or HTTP status NOT_FOUND.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        return addressService.getAddressById(id)
                .map(address -> new ResponseEntity<>(address, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * POST /api/addresses : Create a new address.
     * @param address The Address object to create.
     * @return ResponseEntity with the created Address object and HTTP status CREATED.
     */
    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        // Ensure addressId is null for creation to let JPA generate it
        address.setAddressId(null);
        Address savedAddress = addressService.saveAddress(address);
        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }

    /**
     * PUT /api/addresses/{id} : Update an existing address.
     * @param id The ID of the address to update.
     * @param addressDetails The updated Address object.
     * @return ResponseEntity with the updated Address object, or HTTP status NOT_FOUND if address does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address addressDetails) {
        return addressService.getAddressById(id)
                .map(existingAddress -> {
                    // Update fields from addressDetails
                    existingAddress.setAddress(addressDetails.getAddress());
                    existingAddress.setAddress2(addressDetails.getAddress2());
                    existingAddress.setDistrict(addressDetails.getDistrict());
                    existingAddress.setCityId(addressDetails.getCityId());
                    existingAddress.setPostalCode(addressDetails.getPostalCode());
                    existingAddress.setPhone(addressDetails.getPhone());
                    // lastUpdate is handled by @PreUpdate in the entity
                    Address updatedAddress = addressService.saveAddress(existingAddress);
                    return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE /api/addresses/{id} : Delete an address by ID.
     * @param id The ID of the address to delete.
     * @return ResponseEntity with HTTP status NO_CONTENT if deleted, or NOT_FOUND if not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAddress(@PathVariable Long id) {
        boolean deleted = addressService.deleteAddress(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
