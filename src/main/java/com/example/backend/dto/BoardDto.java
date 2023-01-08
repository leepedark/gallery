package com.example.backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
@Builder
public class BoardDto implements Serializable {

    private int id;
    private int memberId;
    private String title;
    private String content;
    private String createAt;
}
