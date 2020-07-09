package com.flink.ireview.Recycler_ranking_review;

public class RankingreviewData {

    private String ranking_item_number;
    private int ranking_item_image;
    private String ranking_item_company;
    private String ranking_item_name;

    public RankingreviewData(String ranking_item_number, int ranking_item_image, String ranking_item_company, String ranking_item_name) {
        this.ranking_item_number = ranking_item_number;
        this.ranking_item_image = ranking_item_image;
        this.ranking_item_company = ranking_item_company;
        this.ranking_item_name = ranking_item_name;
    }


    public String getRanking_item_number() {
        return ranking_item_number;
    }

    public void setRanking_item_number(String ranking_item_number) {
        this.ranking_item_number = ranking_item_number;
    }

    public int getRanking_item_image() {
        return ranking_item_image;
    }

    public void setRanking_item_image(int ranking_item_image) {
        this.ranking_item_image = ranking_item_image;
    }

    public String getRanking_item_company() {
        return ranking_item_company;
    }

    public void setRanking_item_company(String ranking_item_company) {
        this.ranking_item_company = ranking_item_company;
    }

    public String getRanking_item_name() {
        return ranking_item_name;
    }

    public void setRanking_item_name(String ranking_item_name) {
        this.ranking_item_name = ranking_item_name;
    }
}
