package com.niels.referall.repository;

import com.niels.referall.entity.UserAccount;
import com.niels.referall.enumerate.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {

    @Query(value = "SELECT ua FROM UserAccount ua WHERE ua.taxCode = ?1")
    Optional<UserAccount> findByTaxCode(String taxCode);

    @Query(value = "SELECT ua FROM UserAccount ua WHERE ua.email = ?1")
    Optional<UserAccount> findByEmail(String email);

    @Query(value = "SELECT ua FROM UserAccount ua WHERE ua.documentType = ?1 AND ua.documentId = ?2")
    Optional<UserAccount> findByDocumentTypeAndDocumentId(DocumentType documentType, String documentId);
}
