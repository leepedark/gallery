package com.example.backend.controller;

import com.example.backend.dto.JoinDto;
import com.example.backend.entity.Member;
import com.example.backend.repository.MemberRepository;
import com.example.backend.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class AccountController {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JwtService jwtService;

    @PostMapping("/api/account/join")
    public ResponseEntity join(@RequestBody JoinDto joinDto) {

        Member newMember = new Member();
        newMember.setEmail(joinDto.getEmail());
        newMember.setName(joinDto.getName());
        newMember.setPassword(joinDto.getPassword());

        memberRepository.save(newMember);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/api/account/login")
    public ResponseEntity login(@RequestBody Map<String, String> map, HttpServletResponse response) {

        Member member = memberRepository.findByEmailAndPassword(map.get("email"), map.get("password"));

        if (member != null) {
            String token = jwtService.getToken("id", member.getId());
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            cookie.setHttpOnly(true);

            response.addCookie(cookie);

            return new ResponseEntity(member.getId(), HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/account/check")
    public ResponseEntity check(@CookieValue(value = "token", required = false) String token) {

        if (jwtService.isValid(token)) {
            return new ResponseEntity(jwtService.getId(token), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/api/account/logout")
    public void logout(@CookieValue(value = "token", required = false) String token
            , HttpServletResponse res) {

        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        res.addCookie(cookie);

//        return new ResponseEntity(res, HttpStatus.OK);

    }

}
