package com.niels.referall.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.niels.referall.entity.extra.CommonEntity;
import com.niels.referall.util.Encryptor;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Specialization extends CommonEntity {

    @Column(nullable = false, unique = true)
    @Convert(converter = Encryptor.class)
    private String specialization;

    // Doctor Profile
    @ManyToMany(mappedBy = "specializations", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<DoctorProfile> doctorProfiles;

    public Specialization() {
    }

    public Specialization(String specialization) {
        this.specialization = specialization;
    }

    public Specialization(String specialization, List<DoctorProfile> doctorProfiles) {
        this.specialization = specialization;
        this.doctorProfiles = doctorProfiles;
    }

    public Specialization(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, String specialization, List<DoctorProfile> doctorProfiles) {
        super(id, createdAt, updatedAt);
        this.specialization = specialization;
        this.doctorProfiles = doctorProfiles;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<DoctorProfile> getDoctorProfiles() {
        return doctorProfiles;
    }

    public void setDoctorProfiles(List<DoctorProfile> doctorProfiles) {
        this.doctorProfiles = doctorProfiles;
    }

}
