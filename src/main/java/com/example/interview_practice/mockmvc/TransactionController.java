package com.example.interview_practice.mockmvc;

import com.example.interview_practice.mockmvc.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    SendMoneyService sendMoneyService;

    @PostMapping("/trx")
    public ResponseEntity<Transaction> sendMoney(@RequestBody TrxReq trxReq) {
        Transaction transactionEntity = sendMoneyService.send(trxReq);
        if (transactionEntity != null)
            return ResponseEntity.ok().body(transactionEntity);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
