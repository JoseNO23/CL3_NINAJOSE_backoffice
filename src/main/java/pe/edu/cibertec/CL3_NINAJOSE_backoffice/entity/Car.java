package pe.edu.cibertec.CL3_NINAJOSE_backoffice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Integer carId;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "year")
    private Integer year;

    @Column(name = "vin")
    private String vin;

    @Column(name = "license_plate")
    private String licensePlate;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "owner_contact")
    private String ownerContact;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "engine_type")
    private String engineType;

    @Column(name = "color")
    private String color;

    @Column(name = "insurance_company")
    private String insuranceCompany;

    @Column(name = "insurance_policy_number")
    private String insurancePolicyNumber;

    @Column(name = "registration_expiration_date")
    private LocalDate registrationExpirationDate;

    @Column(name = "service_due_date")
    private LocalDate serviceDueDate;
}
