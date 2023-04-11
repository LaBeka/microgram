package com.example.microgram.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;


@Data
@AllArgsConstructor
@Builder
public class ResultDto {
    public String message;
    public boolean isTrue;
}
