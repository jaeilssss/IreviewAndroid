package com.flink.ireview.Recycler_alarm;

public class AlarmData {

    private int alarm_image_profile;
    private String alarm_item_id;
    private String alarm_item_goodtext;
    private String alarm_item_time;
    private int alarm_image_post;


    public AlarmData(int alarm_image_profile, String alarm_item_id, String alarm_item_goodtext, String alarm_item_time, int alarm_image_post) {
        this.alarm_image_profile = alarm_image_profile;
        this.alarm_item_id = alarm_item_id;
        this.alarm_item_goodtext = alarm_item_goodtext;
        this.alarm_item_time = alarm_item_time;
        this.alarm_image_post = alarm_image_post;
    }


    public int getAlarm_image_profile() {
        return alarm_image_profile;
    }

    public void setAlarm_image_profile(int alarm_image_profile) {
        this.alarm_image_profile = alarm_image_profile;
    }

    public String getAlarm_item_id() {
        return alarm_item_id;
    }

    public void setAlarm_item_id(String alarm_item_id) {
        this.alarm_item_id = alarm_item_id;
    }

    public String getAlarm_item_goodtext() {
        return alarm_item_goodtext;
    }

    public void setAlarm_item_goodtext(String alarm_item_goodtext) {
        this.alarm_item_goodtext = alarm_item_goodtext;
    }

    public String getAlarm_item_time() {
        return alarm_item_time;
    }

    public void setAlarm_item_time(String alarm_item_time) {
        this.alarm_item_time = alarm_item_time;
    }

    public int getAlarm_image_post() {
        return alarm_image_post;
    }

    public void setAlarm_image_post(int alarm_image_post) {
        this.alarm_image_post = alarm_image_post;
    }
}
