package com.ironhack.midterm.bankingAPI.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Decode {
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println("123456");
        System.out.println(bCryptPasswordEncoder.encode("123456"));
        System.out.println(bCryptPasswordEncoder.matches("123456", "$2a$10$rGyUQKQZC9ybFZCRAiYpmeSpO53weufrTBZ3NbdWiIM8tmJGRyQ.C"));
    }

}
