package com.example.interview_practice.mockmvc;


import com.example.interview_practice.mockmvc.model.Transaction;
import com.example.interview_practice.mockmvc.model.TrxRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SendMoneyService {

    private TrxRepo trxRepo;

    public Transaction send(TrxReq trxReq) {
        Transaction trx = Transaction.builder()
                .sender(trxReq.sender())
                .rec(trxReq.rec())
                .amount(trxReq.amount())
                .build();
        return trxRepo.save(trx);
    }
}
