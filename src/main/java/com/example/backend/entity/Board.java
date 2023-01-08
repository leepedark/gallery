package com.example.backend.entity;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @NotNull
//    @Size(min=2, max=30, message = "제목은 2자이상 30자 이하입니다.")
    private String title;
    private String content;

    private LocalDateTime createAt;

    private LocalDateTime modifyAt;

//    private int memberId;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    @JsonIgnore
//    private Member member;
}
