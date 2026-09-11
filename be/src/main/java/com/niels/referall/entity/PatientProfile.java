package com.niels.referall.entity;

import com.niels.referall.entity.extra.CommonEntity;
import com.niels.referall.enumerate.BloodType;
import com.niels.referall.enumerate.converter.BloodTypeConverter;
import com.niels.referall.util.Encryptor;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class PatientProfile extends CommonEntity {

    @Column(nullable = false)
    @Convert(converter = BloodTypeConverter.class)
    private BloodType bloodType;

    @Column(nullable = false)
    @Convert(converter = Encryptor.class)
    private String allergies;

    @Column(nullable = false)
    @Convert(converter = Encryptor.class)
    private String chronicConditions;

    public PatientProfile() {
    }

    public PatientProfile(BloodType bloodType, String allergies, String chronicConditions) {
        this.bloodType = bloodType;
        this.allergies = allergies;
        this.chronicConditions = chronicConditions;
    }

    public PatientProfile(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, BloodType bloodType, String allergies, String chronicConditions) {
        super(id, createdAt, updatedAt);
        this.bloodType = bloodType;
        this.allergies = allergies;
        this.chronicConditions = chronicConditions;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getChronicConditions() {
        return chronicConditions;
    }

    public void setChronicConditions(String chronicConditions) {
        this.chronicConditions = chronicConditions;
    }

}