package com.flink.ireview.Dto;

import java.util.ArrayList;
import java.util.Map;

public class UsersDto {
    String personal_UID;
    String users_email;
    String users_name;
    String users_phone_number;
    int users_reliability;
    String users_nickname;

    private ArrayList<Map<String,String>> my_recommend_list;
    private ArrayList<String> my_comment_list;
    private ArrayList<String> my_review_list;
    private ArrayList<String> my_scrap_list;

    public UsersDto(){
        this.users_nickname = " ";
    }
    public void setPersonal_UID(String personal_UID) {
        this.personal_UID = personal_UID;
    }

    public void setUsers_email(String users_email) {
        this.users_email = users_email;
    }

    public void setUsers_name(String users_name) {
        this.users_name = users_name;
    }


    public void setUsers_phone_number(String users_phone_number) {
        this.users_phone_number = users_phone_number;
    }

    public void setUsers_reliability(int users_reliability) {
        this.users_reliability = users_reliability;
    }

    public void setUsers_nickname(String users_nickname) {
        this.users_nickname = users_nickname;
    }


    public ArrayList<String> getMy_comment_list() {
        return my_comment_list;
    }

    public void setMy_comment_list(ArrayList<String> my_comment_list) {
        this.my_comment_list = my_comment_list;
    }

    public ArrayList<String> getMy_review_list() {
        return my_review_list;
    }

    public void setMy_review_list(ArrayList<String> my_review_list) {
        this.my_review_list = my_review_list;
    }

    public ArrayList<String> getMy_scrap_list() {
        return my_scrap_list;
    }

    public void setMy_scrap_list(ArrayList<String> my_scrap_list) {
        this.my_scrap_list = my_scrap_list;
    }

    public ArrayList<Map<String,String>> getMy_recommend_list() {
        return my_recommend_list;
    }

    public void setMy_recommend_list(ArrayList<Map<String,String>> my_recommend_list) {
        this.my_recommend_list = my_recommend_list;
    }

    public UsersDto(String personal_UID, String users_email, String users_name, String users_phone_number, int users_reliability, String users_nickname, ArrayList<String> my_comment_list, ArrayList<String> my_review_list, ArrayList<String> my_scrap_list
    , ArrayList<Map<String,String>> my_recommemd_list) {
        this.personal_UID = personal_UID;
        this.users_email = users_email;
        this.users_name = users_name;
        this.users_phone_number = users_phone_number;
        this.users_reliability = users_reliability;
        this.users_nickname = users_nickname;
        this.my_comment_list = my_comment_list;
        this.my_review_list = my_review_list;
        this.my_scrap_list = my_scrap_list;
        this.my_recommend_list = my_recommemd_list;
    }

    public String getPersonal_UID() {
        return personal_UID;
    }

    public String getUsers_email() {
        return users_email;
    }

    public String getUsers_name() {
        return users_name;
    }

    public String getUsers_phone_number() {
        return users_phone_number;
    }

    public int getUsers_reliability() {
        return users_reliability;
    }

    public String getUsers_nickname() {
        return users_nickname;
    }



}

