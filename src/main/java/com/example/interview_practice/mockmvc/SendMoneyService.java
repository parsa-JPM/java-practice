package com.example.interview_practice.mockmvc;


import com.example.interview_practice.mockmvc.model.Transaction;
import com.example.interview_practice.mockmvc.model.TrxRepo;
import com.example.interview_practice.mockmvc.model.UserRepo;
import com.example.interview_practice.mockmvc.model.dto.SendMoneyDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SendMoneyService {

    private TrxRepo trxRepo;

    private UserRepo userRepo;

    public SendMoneyDTO send(TrxReq trxReq) {

        Transaction trx = Transaction.builder()
                .sender(userRepo.findByName(trxReq.sender()))
                .rec(userRepo.findByName(trxReq.rec()))
                .amount(trxReq.amount())
                .version("1")
                .build();

        // if we return this object its relations end up with endless user and transactions objects in Json
        Transaction transaction = trxRepo.save(trx);

        return new SendMoneyDTO(transaction.getSender().getName(),
                transaction.getRec().getName(),
                transaction.getAmount(),
                transaction.getVersion());
    }
}
