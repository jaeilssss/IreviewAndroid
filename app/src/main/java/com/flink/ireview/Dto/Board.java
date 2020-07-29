package com.flink.ireview.Dto;

import java.util.ArrayList;

public class Board {
    private Long id;
    private Integer categoryId;
    private String title;
    private String contentString;
    private String userAccount;
    // private Long userId;
    private String userNickname;
    private Integer totalView;
    private Integer totalRecommend;
    private Integer totalComment;
    private boolean manageBoard;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String image6;
    private String image7;
    private String image8;
    private ArrayList<String> goodness;
    private ArrayList<String> badness;
    private Long scrapCount;

    public ArrayList<String> getGoodness() {
        return goodness;
    }

    public void setGoodness(ArrayList<String> goodness) {
        this.goodness = goodness;
    }

    public ArrayList<String> getBadness() {
        return badness;
    }

    public void setBadness(ArrayList<String> badness) {
        this.badness = badness;
    }

    public Board(Long id, Integer categoryId, String title, String contentString, String userAccount, String userNickname, Integer totalView, Integer totalRecommend, Integer totalComment, boolean manageBoard, String image1, String image2, String image3, String image4, String image5, String image6, String image7, String image8, Long scrapCount) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.contentString = contentString;
        this.userAccount = userAccount;
        this.userNickname = userNickname;
        this.totalView = totalView;
        this.totalRecommend = totalRecommend;
        this.totalComment = totalComment;
        this.manageBoard = manageBoard;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
        this.image6 = image6;
        this.image7 = image7;
        this.image8 = image8;
        this.scrapCount = scrapCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentString() {
        return contentString;
    }

    public void setContentString(String contentString) {
        this.contentString = contentString;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public Integer getTotalView() {
        return totalView;
    }

    public void setTotalView(Integer totalView) {
        this.totalView = totalView;
    }

    public Integer getTotalRecommend() {
        return totalRecommend;
    }

    public void setTotalRecommend(Integer totalRecommend) {
        this.totalRecommend = totalRecommend;
    }

    public Integer getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(Integer totalComment) {
        this.totalComment = totalComment;
    }

    public boolean isManageBoard() {
        return manageBoard;
    }

    public void setManageBoard(boolean manageBoard) {
        this.manageBoard = manageBoard;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    public String getImage6() {
        return image6;
    }

    public void setImage6(String image6) {
        this.image6 = image6;
    }

    public String getImage7() {
        return image7;
    }

    public void setImage7(String image7) {
        this.image7 = image7;
    }

    public String getImage8() {
        return image8;
    }

    public void setImage8(String image8) {
        this.image8 = image8;
    }

    public Long getScrapCount() {
        return scrapCount;
    }

    public void setScrapCount(Long scrapCount) {
        this.scrapCount = scrapCount;
    }
}
