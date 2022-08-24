package com.ug.PaymentsService.Controller;

import com.ug.PaymentsService.Dao.TransationDao;
import com.ug.PaymentsService.Entity.TransactionDetailsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/payment")
@RestController
public class PaymentController {
    @Autowired
    TransationDao transationDao;

//    This method sends transaction details for given transaction id. (uri: localhost:8083/payment/transaction/1)
    @GetMapping(value = "/transaction/{id}")
    public @ResponseBody
    ResponseEntity getTransactionInfoByTransactionId(@PathVariable(name = "id") int Tid){
        TransactionDetailsEntity transactionDetails=transationDao.findByTransactionId(Tid);
        if(transactionDetails==null)
        {
            return new ResponseEntity("No Transactions found for given transaction id",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(transationDao.findByTransactionId(Tid),HttpStatus.OK);
    }

//    This method saves the incoming transaction details and returns the transaction id. (uri: localhost:8081/hotel/booking/1/transaction)
    @PostMapping(value = "/transaction")
    public  @ResponseBody Integer createTransaction(@RequestBody TransactionDetailsEntity transactionDetailsEntity){
        if(transationDao.findByBookingId(transactionDetailsEntity.getBookingId())!=null)
        {
            return -1;
        }
        TransactionDetailsEntity savedTransaction= transationDao.save(transactionDetailsEntity);
        int transId=savedTransaction.getTransactionId();

        return  transId;
    }


}

