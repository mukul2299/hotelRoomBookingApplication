package com.ug.BookingService.Entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import static java.time.temporal.ChronoUnit.DAYS;

@Table(name = "booking")
@Entity
@Data

public class BookingInfoEntity  {

        private static int oneRoomPrice=1000;
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int bookingId;

        @Column(name = "from_date")
        private LocalDate fromDate;

        @Column(name = "to_date")
        private LocalDate toDate;

        @Column(name = "aadhar_number")
        private  String aadharNumber;

        @Column(name ="num_of_rooms" )
        private int numOfRooms;

        @Column(name = "room_numbers")
        private String  roomNumbers;

        @Column(name ="room_price",nullable = false )
        private int roomPrice=1000;


        @Column(name = "transaction_id",nullable = false)
        private int transactionId=0;

        @Column(name = "booked_on")
        private LocalDateTime bookedOn;


        //This will generate list random room numbers.
        public String generateRoomNumber(int roomsCount){
            Random rand = new Random();
            int upperBound = 100;
            ArrayList<String>numberList = new ArrayList<String>();
            for (int i=0; i<roomsCount; i++){
                numberList.add(String.valueOf(rand.nextInt(upperBound)));
            }
            return numberList.toString();
        }

        //This will calculate the total billable amount to be paid, Based on number of rooms and days of stay.
        public  int calculateTotalPrice(){
            int numOfDays =(int) DAYS.between(fromDate,toDate);
            return oneRoomPrice*numOfRooms*numOfDays;
        }

    }


