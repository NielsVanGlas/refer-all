package com.niels.referall.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.niels.referall.entity.extra.CommonEntity;
import com.niels.referall.util.Encryptor;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Specialization extends CommonEntity {

    @Column(nullable = false, unique = true)
    @Convert(converter = Encryptor.class)
    private String specialization;

    // Doctor Profile
    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "doctor_id")
    @JsonBackReference
    private DoctorProfile doctorProfile;

    public Specialization() {
    }

    public Specialization(String specialization, DoctorProfile doctorProfile) {
        this.specialization = specialization;
        this.doctorProfile = doctorProfile;
    }

    public Specialization(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, String specialization, DoctorProfile doctorProfile) {
        super(id, createdAt, updatedAt);
        this.specialization = specialization;
        this.doctorProfile = doctorProfile;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public DoctorProfile getDoctorProfile() {
        return doctorProfile;
    }

    public void setDoctorProfile(DoctorProfile doctorProfile) {
        this.doctorProfile = doctorProfile;
    }

}
