package com.example.interview_practice.mockmvc;


import com.example.interview_practice.mockmvc.model.Transaction;
import com.example.interview_practice.mockmvc.model.TrxRepo;
import com.example.interview_practice.mockmvc.model.User;
import com.example.interview_practice.mockmvc.model.UserRepo;
import com.example.interview_practice.mockmvc.model.dto.SendMoneyDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SendMoneyServiceTest {

    @Mock
    TrxRepo trxRepo;

    @Mock
    UserRepo userRepo;

    @InjectMocks
    SendMoneyService sendMoneyService;

    @Test
    void send() {
        when(trxRepo.save(any())).thenReturn(mockTransaction());
        SendMoneyDTO sendMoneyDTO = sendMoneyService.send(new TrxReq("Parsa", "Mahsa", new BigDecimal("27")));

        Assertions.assertEquals("Parsa", sendMoneyDTO.sender());
        Assertions.assertEquals("Mahsa", sendMoneyDTO.rec());
    }

    Transaction mockTransaction() {
        return Transaction.builder()
                .version("1")
                .amount(new BigDecimal("27"))
                .sender(User.builder().name("Parsa").build())
                .rec(User.builder().name("Mahsa").build())
                .build();
    }
}