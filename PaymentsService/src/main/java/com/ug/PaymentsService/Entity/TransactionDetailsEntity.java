package com.ug.PaymentsService.Entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="transctions")
public class TransactionDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int transactionId;

    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "booking_id",nullable = false)
    private int bookingId;

    private String upiId;

    private String cardNumber;
}
/*
{
"paymentMode":"Card",
"bookingId":2,
"upiId":"",
"cardNumber":"6769868"
}
 */

