package com.flink.ireview.Dto;

import java.util.ArrayList;

// 클래스의 생성자는 맨 밑에 있음 !!!!!

public class ReviewDto {
    private String reviewer_nickname;
    private String review_UID;   // 처음 생성 할때는 필요(?) 없기 때문에 생성자 초기값에서는 뺌!!! 추가 할 경우 setter로 추가하기 (set)
    private String review_create_time;
    private String review_category_UID;
    private String reviewer_uid;
    private String review_main_title;
    private String review_main_string;
    private ArrayList<String> review_main_image;
    private ArrayList<String> review_main_advantage;    // 장점
    private ArrayList<String> review_main_weakness;      // 단점
    private ArrayList<String> recommend_list;
    private ArrayList<String> scrap_list;
    private int recommend_count;
    private int review_view_number;
    private int review_scrap_count;
    private ArrayList<String> comment_list;

    public String getReview_UID() {
        return review_UID;
    }

    public void setReview_UID(String review_UID) {
        this.review_UID = review_UID;
    }

    public String getReview_create_time() {
        return review_create_time;
    }

    public void setReview_create_time(String review_create_time) {
        this.review_create_time = review_create_time;
    }

    public String getReview_category_UID() {
        return review_category_UID;
    }

    public void setReview_category_UID(String review_category_UID) {
        this.review_category_UID = review_category_UID;
    }

    public String getReview_main_title() {
        return review_main_title;
    }

    public void setReview_main_title(String review_main_title) {
        this.review_main_title = review_main_title;
    }

    public String getReviewer_uid() {
        return reviewer_uid;
    }

    public void setReviewer_uid(String reviewer_uid) {
        this.reviewer_uid = reviewer_uid;
    }

    public int getRecomment_count() {
        return recommend_count;
    }

    public void setRecomment_count(int recomment_count) {
        this.recommend_count = recomment_count;
    }

    public int getReview_scrap_count() {
        return review_scrap_count;
    }

    public void setReview_scrap_count(int review_scrap_count) {
        this.review_scrap_count = review_scrap_count;
    }

    public String getReviewer_name() {
        return reviewer_nickname;
    }

    public void setReviewer_name(String reviewer_name) {
        this.reviewer_nickname = reviewer_name;
    }

    public String getReview_main_string() {
        return review_main_string;
    }

    public void setReview_main_string(String review_main_string) {
        this.review_main_string = review_main_string;
    }

    public ArrayList<String> getReview_main_image() {
        return review_main_image;
    }

    public void setReview_main_image(ArrayList<String> review_main_image) {
        this.review_main_image = review_main_image;
    }

    public ArrayList<String> getReview_main_advantage() {
        return review_main_advantage;
    }

    public void setReview_main_advantage(ArrayList<String> review_main_advantage) {
        this.review_main_advantage = review_main_advantage;
    }

    public ArrayList<String> getReview_main_weakness() {
        return review_main_weakness;
    }

    public void setReview_main_weakness(ArrayList<String> review_main_weakness) {
        this.review_main_weakness = review_main_weakness;
    }

    public ArrayList<String> getRecommend_list() {
        return recommend_list;
    }

    public void setRecommend_list(ArrayList<String> recommend_list) {
        this.recommend_list = recommend_list;
    }

    public int getReview_view_number() {
        return review_view_number;
    }

    public void setReview_view_number(int review_view_number) {
        this.review_view_number = review_view_number;
    }

    public ArrayList<String> getComment_list() {
        return comment_list;
    }

    public void setComment_list(ArrayList<String> comment_list) {
        this.comment_list = comment_list;
    }

    public int getRecommend_count() {
        return recommend_count;
    }

    public void setRecommend_count(int recommend_count) {
        this.recommend_count = recommend_count;
    }

    public ArrayList<String> getScrap_list() {
        return scrap_list;
    }

    public void setScrap_list(ArrayList<String> scrap_list) {
        this.scrap_list = scrap_list;
    }

    public String getReviewer_nickname() {
        return reviewer_nickname;
    }

    public void setReviewer_nickname(String reviewer_nickname) {
        this.reviewer_nickname = reviewer_nickname;
    }

    public ReviewDto(String reviewer_nickname, String reviewer_uid, String review_create_time, String review_category_UID, String review_main_title, String review_main_string, ArrayList<String> review_main_image, ArrayList<String> review_main_advantage, ArrayList<String> review_main_weakness, ArrayList<String> recommend_list, int recomment_count, int review_view_number, ArrayList<String> comment_list, ArrayList<String> scrap_list) {
        this.reviewer_nickname = reviewer_nickname;
        this.reviewer_uid = reviewer_uid;
        this.review_create_time = review_create_time;
        this.review_category_UID = review_category_UID;
        this.review_main_title = review_main_title;
        this.review_main_string = review_main_string;
        this.review_main_image = review_main_image;
        this.review_main_advantage = review_main_advantage;
        this.review_main_weakness = review_main_weakness;
        this.recommend_list = recommend_list;
        this.recommend_count = recomment_count;
        this.review_view_number = review_view_number;
        this.comment_list = comment_list;
        this.scrap_list = scrap_list;
    }
}
