package com.flink.ireview.Dto;

import java.util.ArrayList;

public class CommentDto {
    private String review_uid;
    private String comment_nickname;
    private String comment;
    private ArrayList<String> recommendList;
    private String create_time;
    private String comment_uid;

    public String getReview_uid() {
        return review_uid;
    }

    public void setReview_uid(String review_uid) {
        this.review_uid = review_uid;
    }

    public String getComment_nickname() {
        return comment_nickname;
    }

    public void setComment_nickname(String comment_nickname) {
        this.comment_nickname = comment_nickname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ArrayList<String> getRecommendList() {
        return recommendList;
    }

    public void setRecommendList(ArrayList<String> recommendList) {
        this.recommendList = recommendList;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getComment_uid() {
        return comment_uid;
    }

    public void setComment_uid(String comment_uid) {
        this.comment_uid = comment_uid;
    }

    public CommentDto(String review_uid, String comment_nickname, String comment, ArrayList<String> recommendList, String create_time) {
        this.review_uid = review_uid;
        this.comment_nickname = comment_nickname;
        this.comment = comment;
        this.recommendList = recommendList;
        this.create_time = create_time;
    }
}
