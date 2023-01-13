package com.example.backend.entity;

import com.example.backend.repository.BoardRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String name;

    private String postNo;

    private String basicAddress;
    private String DtlAddress;

//    @OneToMany(mappedBy = "member")
//    private List<Board> board = new ArrayList<>();
}
