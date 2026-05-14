package com.example.interview_practice.roboshop;

public record Product(long id,
                      String description,
                      String name,
                      String imageName,
                      String category,
                      double price,
                      double discount)
{ }
