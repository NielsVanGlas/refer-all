package com.niels.referall.repository;

import com.niels.referall.dto.specialization.ShowSpecializationDto;
import com.niels.referall.entity.Specialization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, UUID> {
    @Query(value = "SELECT new com.niels.referall.dto.specialization.ShowSpecializationDto(s.specialization) FROM Specialization s")
    Page<ShowSpecializationDto> findAllSpecializations(Pageable pagination);
}
