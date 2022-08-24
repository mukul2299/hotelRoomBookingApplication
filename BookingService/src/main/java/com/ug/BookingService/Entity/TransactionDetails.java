package com.ug.BookingService.Entity;


import lombok.Data;

@Data
public class TransactionDetails {

        private int transactionId;

        private String paymentMode;

        private int bookingId;

        private String upiId;

        private String cardNumber;
    }

