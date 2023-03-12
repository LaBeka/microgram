package com.example.microgram;

import com.example.microgram.entity.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class MicrogramApplication {

    public static void main(String[] args) {
        Like like = new Like(LocalDate.now(), new User(), new Comment());
        SpringApplication.run(MicrogramApplication.class, args);
    }

}
