package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "items")
@Getter
public class Item {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 50, nullable = false)
    String name;
    @Column(length = 100)
    String imgPath;
    @Column
    int price;
    @Column
    int discount_per;



}
