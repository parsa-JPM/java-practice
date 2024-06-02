package com.example.interview_practice.mockmvc;


import com.example.interview_practice.mockmvc.model.Transaction;
import com.example.interview_practice.mockmvc.model.TrxRepo;
import com.example.interview_practice.mockmvc.model.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SendMoneyService {

    private TrxRepo trxRepo;

    private UserRepo userRepo;

    public Transaction send(TrxReq trxReq) {

        Transaction trx = Transaction.builder()
                .sender(userRepo.findByName(trxReq.sender()))
//                .rec(userRepo.findByName(trxReq.rec()))
                .amount(trxReq.amount())
                .version("1")
                .build();
        return trxRepo.save(trx);
    }
}
