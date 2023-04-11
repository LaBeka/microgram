package com.example.microgram.dto;

import com.example.microgram.entity.Likable;
import com.example.microgram.entity.LikeType;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class LIkeDto {
    private Long likeId;
    private String typeStr;
    private LikeType likeType;
    private Long entityId;
//    public LIkeDto validateData(){
//        for (LikeType value : LikeType.values()) {
//            if(value.getType().equals(typeStr.toLowerCase())){
//                this.likeType = value;
//                break;
//            }
//        }
//    }
    public LIkeDto(
            String typeStr,
            Long entityId) {
        this.typeStr = typeStr;
        this.entityId = entityId;
        for (LikeType value : LikeType.values()) {
            if(value.getType().equals(typeStr.toLowerCase())){
                this.likeType = value;
                break;
            }
        }
    }
}
