package com.flink.ireview.ui.Rangking;

import android.widget.ImageView;

public class RecyclerView_PostItem {

    String ImageView;
    String name;
    String category;
    String writer;
    String information;


    public RecyclerView_PostItem(String name, String category, String writer, String information) {
        this.name = name;
        this.category = category;
        this.writer = writer;
        this.information = information;
    }

    public RecyclerView_PostItem (String imageView, String name, String category, String writer, String information) {
        this.ImageView = imageView;
        this.name= name;
        this.category = category;
        this.writer = writer;
        this.information = information;
    }

    public String getImageView() {
        return ImageView;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getWriter() {
        return writer;
    }

    public String getInformation() {
        return information;
    }



    public void setImageView(String imageView) {
        ImageView = imageView;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setImformation(String information) {
        this.information = information;
    }



}
