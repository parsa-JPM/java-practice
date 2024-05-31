package com.example.interview_practice.mockmvc;

import com.example.interview_practice.MyUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
// it's needed to use mockmvc object
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void sendMoneyOK() throws Exception {
        var requestDTO = new TrxReq("parsa", "mahsa", new BigDecimal("25.1"));
        mockMvc.perform(post("/trx")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MyUtils.toJson(requestDTO))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // $ represent json root and dot references the path to a value
                .andExpect(jsonPath("$.sender", is("parsa")))
                .andExpect(jsonPath("$.rec").value("mahsa"))
                .andExpect(jsonPath("$.amount").isNumber())
                .andExpect(jsonPath("$.amount", is(25.1)))
                // it prints all request and response details (body, headers, method, etc)
                .andDo(print());
        // todo push codes to Github
        // Todo make user entity
        // Todo Test user DB layer
        // todo check what's etag and location in ResponseEntity
    }
}