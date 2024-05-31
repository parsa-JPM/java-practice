package com.example.interview_practice.mockmvc;

import java.math.BigDecimal;

public record TrxReq(String sender,
                     String rec,
                     /*
                     BigDecimal supports arithmetic operations (addition, subtraction, multiplication, division)
                      with arbitrary precision,
                      which is essential for financial calculations where rounding errors cannot be tolerated
                      */
                     BigDecimal amount) { }
