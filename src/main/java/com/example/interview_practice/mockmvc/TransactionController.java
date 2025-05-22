package com.example.interview_practice.mockmvc;

import com.example.interview_practice.mockmvc.model.dto.SendMoneyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<SendMoneyDTO> sendMoney(@RequestBody TrxReq trxReq) {
        SendMoneyDTO sendMoneyDTO = sendMoneyService.send(trxReq);
        if (sendMoneyDTO != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.ETAG, sendMoneyDTO.version())
                    .body(sendMoneyDTO);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
