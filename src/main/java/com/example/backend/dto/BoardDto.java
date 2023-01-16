package com.example.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull
    @Size(min=2)
    private String content;
    private String createdAt;
}
