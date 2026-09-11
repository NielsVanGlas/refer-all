package com.niels.referall.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.niels.referall.entity.extra.CommonEntity;
import com.niels.referall.util.Encryptor;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class DoctorProfile extends CommonEntity {

    @Column(nullable = false, unique = true)
    @Convert(converter = Encryptor.class)
    private String licenseNumber;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "doctor_specialization",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    @JsonManagedReference
    private List<Specialization> specializations;

    public DoctorProfile() {
    }

    public DoctorProfile(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public DoctorProfile(String licenseNumber, List<Specialization> specializations) {
        this.licenseNumber = licenseNumber;
        this.specializations = specializations;
    }

    public DoctorProfile(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, String licenseNumber, List<Specialization> specializations) {
        super(id, createdAt, updatedAt);
        this.licenseNumber = licenseNumber;
        this.specializations = specializations;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public List<Specialization> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<Specialization> specializations) {
        this.specializations = specializations;
    }

}
