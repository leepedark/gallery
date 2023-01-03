package com.example.backend.dto;

import lombok.Getter;

@Getter
public class OrderDto {

    private int itemId;
    private String name;

    private String address;

    private String payment;

    private String cardNumber;

    private String items;
}
