package com.example.crud.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * JPA Entity for the 'address' table.
 * Note: The 'location' (geometry) column from the SQL file is omitted for simplicity,
 * as it requires specialized GIS libraries (e.g., Hibernate Spatial) which are beyond
 * the scope of a basic CRUD application.
 * Foreign keys `city_id` and `country_id` are represented as Longs, without
 * creating full `City` and `Country` entities for this basic CRUD.
 */
@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @Column(name = "address2", length = 50)
    private String address2;

    @Column(name = "district", nullable = false, length = 20)
    private String district;

    @Column(name = "city_id", nullable = false)
    private Long cityId; // Representing foreign key as ID for simplicity

    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    // Custom constructor for creating new addresses without an ID
    public Address(String address, String address2, String district, Long cityId, String postalCode, String phone) {
        this.address = address;
        this.address2 = address2;
        this.district = district;
        this.cityId = cityId;
        this.postalCode = postalCode;
        this.phone = phone;
        this.lastUpdate = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        lastUpdate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}
