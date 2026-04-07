package com.example.interview_practice;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

public class MyUtils {

    public static String toJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(object);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }
}
