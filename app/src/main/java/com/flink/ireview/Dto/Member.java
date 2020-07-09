package com.flink.ireview.Dto;

public class Member {
    public Member() {
    }

    private Long id;

    private String account;

    private String password;

    private String email;

    private String name;

    private String nickName;

    private String phoneNumber;

    private String year;

    private String month;

    private String date;

    private String gender;

    private int interest1;

    private int interest2;

    private int interest3;

    private int interest4;

    private int interest5;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getInterest1() {
        return interest1;
    }

    public void setInterest1(int interest1) {
        this.interest1 = interest1;
    }

    public int getInterest2() {
        return interest2;
    }

    public void setInterest2(int interest2) {
        this.interest2 = interest2;
    }

    public int getInterest3() {
        return interest3;
    }

    public void setInterest3(int interest3) {
        this.interest3 = interest3;
    }

    public int getInterest4() {
        return interest4;
    }

    public void setInterest4(int interest4) {
        this.interest4 = interest4;
    }

    public int getInterest5() {
        return interest5;
    }

    public void setInterest5(int interest5) {
        this.interest5 = interest5;
    }

    public Member(String account, String password, String email, String name, String nickName, String phoneNumber, String year, String month, String date, String gender) {
        this.account = account;
        this.password = password;
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.year = year;
        this.month = month;
        this.date = date;
        this.gender = gender;
    }
}
