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
                    /*
                      An ETag (Entity Tag) is an HTTP header used for web cache validation and conditional requests.
                      It is a unique identifier assigned by a web server to a specific version of a resource.
                      This mechanism helps improve web performance by allowing clients (browsers or intermediaries)
                      to cache resources and validate their freshness efficiently. so we have to check etag using if-match header in update method
                      to make sure we're updating last version of entity otherwise it must be re-retrieved
                      (I'm using it in transaction just for example but it's not needed as we don't update transaction)
                     */
                    .header(HttpHeaders.ETAG, sendMoneyDTO.version())
                    .body(sendMoneyDTO);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
