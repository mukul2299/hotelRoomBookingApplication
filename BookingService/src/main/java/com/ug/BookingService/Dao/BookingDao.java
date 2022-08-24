package com.ug.BookingService.Dao;


import com.ug.BookingService.Entity.BookingInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDao extends JpaRepository<BookingInfoEntity,Integer> {
    public BookingInfoEntity findByBookingId(Integer bookingId);
}

