package com.printmanagement.repository;

import com.printmanagement.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    Optional<Receipt> findByReceiptNumber(String receiptNumber);
    boolean existsByReceiptNumber(String receiptNumber);
}