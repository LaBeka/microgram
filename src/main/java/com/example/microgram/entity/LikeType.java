package com.example.microgram.entity;

public enum LikeType {
        UNKNOWN("unknown"),
        POST("post"),
        COMMENT("comment");
        private String type;
        LikeType(String type) {
                this.type = type;
        }
        public String getType() {
                return type;
        }
        //        UNKNOWN,
//        POST,
//        COMMENT
}
