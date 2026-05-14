package com.example.interview_practice.roboshop;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CardService {

    private CopyOnWriteArrayList<Product> products;

    public List<Product> getCard(){
        return new ArrayList<>(products);
    }
}
