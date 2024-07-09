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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
// it tells spring to create mockMvc object for us
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void sendMoneyOK() throws Exception {
        var requestDTO = new TrxReq("parsa", "mahsa", new BigDecimal("25.1"));
        mockMvc.perform(MockMvcRequestBuilders.post("/trx")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MyUtils.toJson(requestDTO))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                // $ represent json root and dot references the path to a value
                .andExpect(MockMvcResultMatchers.jsonPath("$.sender", Matchers.is("parsa")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rec").value("mahsa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount", Matchers.is(25.1)))
                // it prints all request and response details (body, headers, method, etc)
                .andDo(print());
    }
}