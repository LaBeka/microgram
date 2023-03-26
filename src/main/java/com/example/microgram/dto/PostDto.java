package com.example.microgram.dto;

import com.example.microgram.entity.Post;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
public class PostDto {
    private Long postId;
    private Long userId;
    private String photo;
    private String description;

    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    private LocalDate postDate;

    public String validatePostData(){
        StringBuilder messageBack = new StringBuilder();
        String newLine = "\n";
        if(photo == null || photo.isBlank()){
            messageBack.append("Photo is not valid").append(newLine);
        }
        if(description == null || description.isBlank()){
            messageBack.append("Description is not valid").append(newLine);
        }
        if(postDate != null && messageBack.length() == 0){
            return messageBack.toString();
        }
        if(messageBack.length() == 0) {
            this.postDate = LocalDate.now();
        }
        return messageBack.toString();
    }

    public static PostDto buildPostDTO(Post post){
        return PostDto.builder()
                .userId(post.getUserId())
                .photo(post.getPhoto())
                .description(post.getDescription())
                .postDate(post.getPostDate())
                .build();
    }
}
