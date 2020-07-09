package com.flink.ireview.Recycler_ranking_user;

public class RankinguserData {
    private String ranking_user_item_number;
    private int ranking_user_item_image;
    private String ranking_user_item_nickname;
    private String ranking_user_item_recommend;
    private String ranking_user_item_recommendnumber;
    private String ranking_user_item_commend;
    private String ranking_user_item_commendnumber;

    public String getRanking_item_recommendnumber() {
        return ranking_user_item_recommendnumber;
    }

    public void setRanking_item_recommendnumber(String ranking_item_recommendnumber) {
        this.ranking_user_item_recommendnumber = ranking_item_recommendnumber;
    }

    public String getRanking_item_commend() {
        return ranking_user_item_commend;
    }

    public void setRanking_item_commend(String ranking_item_commend) {
        this.ranking_user_item_commend = ranking_item_commend;
    }

    public String getRanking_item_commendnumber() {
        return ranking_user_item_commendnumber;
    }

    public void setRanking_item_commendnumber(String ranking_item_commendnumber) {
        this.ranking_user_item_commendnumber = ranking_item_commendnumber;
    }

    public RankinguserData(String ranking_item_recommendnumber, String ranking_item_commend, String ranking_item_commendnumber) {
        this.ranking_user_item_recommendnumber = ranking_item_recommendnumber;
        this.ranking_user_item_commend = ranking_item_commend;
        this.ranking_user_item_commendnumber = ranking_item_commendnumber;
    }

    public RankinguserData(String ranking_item_number, int ranking_item_image, String ranking_item_company, String ranking_item_name) {
        this.ranking_user_item_number = ranking_item_number;
        this.ranking_user_item_image = ranking_item_image;
        this.ranking_user_item_nickname = ranking_item_company;
        this.ranking_user_item_recommend = ranking_item_name;
    }


    public String getRanking_item_number() {
        return ranking_user_item_number;
    }

    public void setRanking_item_number(String ranking_item_number) {
        this.ranking_user_item_number = ranking_item_number;
    }

    public int getRanking_item_image() {
        return ranking_user_item_image;
    }

    public void setRanking_item_image(int ranking_item_image) {
        this.ranking_user_item_image = ranking_item_image;
    }

    public String getRanking_item_company() {
        return ranking_user_item_nickname;
    }

    public void setRanking_item_company(String ranking_item_company) {
        this.ranking_user_item_nickname = ranking_item_company;
    }

    public String getRanking_item_name() {
        return ranking_user_item_recommend;
    }

    public void setRanking_item_name(String ranking_item_name) {
        this.ranking_user_item_recommend = ranking_item_name;
    }
}
