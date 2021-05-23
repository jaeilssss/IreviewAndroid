package com.flink.ireview.RecyclerView.Recycler_evnet;

public class EventData {

    private int event_photo;
    private String event_name;
    private String event_date;


    public EventData(int event_photo, String event_name, String event_date) {
        this.event_photo = event_photo;
        this.event_name = event_name;
        this.event_date = event_date;
    }

    public int getEvent_photo() {
        return event_photo;
    }

    public void setEvent_photo(int event_photo) {
        this.event_photo = event_photo;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }
}
