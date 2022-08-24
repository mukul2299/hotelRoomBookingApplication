package com.ug.BookingService.Controller;


import com.sun.istack.NotNull;
import com.ug.BookingService.Dao.BookingDao;
import com.ug.BookingService.Entity.BookingInfoEntity;
import com.ug.BookingService.Entity.TransactionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;


@RequestMapping(value = "/hotel")
@RestController
public class BookingController {

    @Autowired
    BookingDao bookingDao;
    String uri="http://localhost:8083/payment/transaction";
    RestTemplate restTemplate= new RestTemplate();

    @PostMapping(value = "/booking" )
    public @ResponseBody ResponseEntity roomBooking(@NotNull @RequestBody BookingInfoEntity bookingInfoEntity){

        bookingInfoEntity.setRoomPrice(bookingInfoEntity.calculateTotalPrice());

        bookingInfoEntity.setBookedOn(LocalDateTime.now());

        bookingInfoEntity.setRoomNumbers(bookingInfoEntity.generateRoomNumber(bookingInfoEntity.getNumOfRooms()));

        BookingInfoEntity savedBooking=bookingDao.save(bookingInfoEntity);
        return new ResponseEntity(savedBooking, HttpStatus.CREATED);

    }
//    Making payment for given booking id (uri: localhost:8081/hotel/booking/1/transaction)
    @PostMapping(value = "booking/{id}/transaction")
    public ResponseEntity roomPayment(@RequestBody TransactionDetails transactionDetails , @PathVariable(name = "id") int Bid)
    {
        BookingInfoEntity bookingInfo= bookingDao.findByBookingId(Bid);

//        If given Booking Id does not exist.
        if(bookingInfo==null)
        {
            return new ResponseEntity("Invalid Booking Id", HttpStatus.BAD_REQUEST);
        }

//        Payment Mode validations
        if(transactionDetails.getPaymentMode().equals("UPI") )
        {
            if(transactionDetails.getUpiId().length()<4) return new ResponseEntity("Provide valid UPI Id", HttpStatus.BAD_REQUEST);
        }
        else if(transactionDetails.getPaymentMode().equals("CARD") )
        {
            if(transactionDetails.getCardNumber().length()<4) return new ResponseEntity("Provide valid Card Number", HttpStatus.BAD_REQUEST);
        }
        else
        {
            return new ResponseEntity("Choose valid Payment mode", HttpStatus.BAD_REQUEST);
        }

//        Sending request to payment service
        Integer responseTransactionId= restTemplate.postForObject(uri,transactionDetails,Integer.class);

//        If payment already completed then payment service return -1.
        if(responseTransactionId<0) return new ResponseEntity("Payment already completed for this booking id: "+Bid,HttpStatus.BAD_REQUEST);
        else
        {
            bookingInfo.setTransactionId(responseTransactionId);
            System.out.println("Booking confirmed for user with aadhaar number: " + bookingInfo.getAadharNumber()
                    + "    |    " + "Here are the booking details:    " + bookingInfo.toString());
        }

        return new ResponseEntity(bookingDao.save(bookingInfo),HttpStatus.CREATED);
    }
}
/*
{"fromDate":"2022-06-18",
        "toDate":"2022-06-19",
        "aadharNumber":"8686876896",
        "numOfRooms":2}

 localhost:8081/BOOKING-SERVICE/booking/2/transaction
 {
"paymentMode":"Card",
"bookingId":2,
"upiId":"",
"cardNumber":"6769868"
}
 */

