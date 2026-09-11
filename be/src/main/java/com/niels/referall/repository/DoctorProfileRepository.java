package com.niels.referall.repository;

import com.niels.referall.entity.DoctorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, UUID> {

    @Query(value = "SELECT dp FROM DoctorProfile dp WHERE dp.licenseNumber = ?1")
    Optional<DoctorProfile> findIfExist(String licenseNumber);

    @Query(value = "SELECT dp FROM DoctorProfile dp WHERE dp.licenseNumber = ?1")
    DoctorProfile findByLicenseNumber(String licenseNumber);

}
