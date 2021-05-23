package com.flink.ireview.RecyclerView.Recycler_recomment;

public class RecommentData {

    private int recomment_photo;
    private String recomment_nickname;
    private String recomment_date;
    private String recomment_content;
    private int recomment_like_button;
    private String recomment_like_number;
    private String recomment_modify_button;
    private String recomment_delete_button;

    public RecommentData(int recomment_photo, String recomment_nickname, String recomment_date, String recomment_content, String imageView, int recomment_like_button, String recomment_like_number, String recomment_modify_button, String recomment_delete_button) {
        this.recomment_photo = recomment_photo;
        this.recomment_nickname = recomment_nickname;
        this.recomment_date = recomment_date;
        this.recomment_content = recomment_content;
        this.recomment_like_button = recomment_like_button;
        this.recomment_like_number = recomment_like_number;
        this.recomment_modify_button = recomment_modify_button;
        this.recomment_delete_button = recomment_delete_button;
    }

    public int getRecomment_photo() {
        return recomment_photo;
    }

    public void setRecomment_photo(int recomment_photo) {
        this.recomment_photo = recomment_photo;
    }

    public String getRecomment_nickname() {
        return recomment_nickname;
    }

    public void setRecomment_nickname(String recomment_nickname) {
        this.recomment_nickname = recomment_nickname;
    }

    public String getRecomment_date() {
        return recomment_date;
    }

    public void setRecomment_date(String recomment_date) {
        this.recomment_date = recomment_date;
    }

    public String getRecomment_content() {
        return recomment_content;
    }

    public void setRecomment_content(String recomment_content) {
        this.recomment_content = recomment_content;
    }

    public int getRecomment_like_button() {
        return recomment_like_button;
    }

    public void setRecomment_like_button(int recomment_like_button) {
        this.recomment_like_button = recomment_like_button;
    }

    public String getRecomment_like_number() {
        return recomment_like_number;
    }

    public void setRecomment_like_number(String recomment_like_number) {
        this.recomment_like_number = recomment_like_number;
    }


    public String getRecomment_modify_button() {
        return recomment_modify_button;
    }

    public void setRecomment_modify_button(String recomment_modify_button) {
        this.recomment_modify_button = recomment_modify_button;
    }

    public String getRecomment_delete_button() {
        return recomment_delete_button;
    }

    public void setRecomment_delete_button(String recomment_delete_button) {
        this.recomment_delete_button = recomment_delete_button;
    }
}
