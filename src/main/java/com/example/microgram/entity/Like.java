package com.example.microgram.entity;

import lombok.Data;

@Data
public class Like implements Likable {
    private String likeDate;
    private User user;

    @Override
    public void likePost(Post post) {

    }

    @Override
    public void likeComment(Comment comment) {

    }
}
