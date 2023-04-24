package com.example.microgram.dto.post;

import com.example.microgram.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class PostDto {
    private String photo;
    private String description;

    public String validatePostData(){
        StringBuilder messageBack = new StringBuilder();
        String newLine = "\n";
        if(photo == null || photo.isBlank()){
            messageBack.append("Photo is not valid").append(newLine);
        }
        if(description == null || description.isBlank()){
            messageBack.append("Description is not valid").append(newLine);
        }

        return messageBack.toString();
    }

    //"photo": "some",
    //"description": "bala"
}
