package com.example.microgram.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class ErrorDto {
    private ArrayList<String> errorMessages;
}
