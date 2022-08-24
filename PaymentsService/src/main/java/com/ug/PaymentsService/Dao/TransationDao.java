package com.ug.PaymentsService.Dao;

import com.ug.PaymentsService.Entity.TransactionDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransationDao extends JpaRepository<TransactionDetailsEntity,Integer> {
    public TransactionDetailsEntity findByBookingId(Integer bId);
    public TransactionDetailsEntity findByTransactionId(Integer TId);
}
