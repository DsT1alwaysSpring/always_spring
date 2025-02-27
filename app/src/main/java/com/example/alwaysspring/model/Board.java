package com.example.alwaysspring.model;

import java.sql.Timestamp;

public class Board {
    private Long b_idx;
    private Long user_idx;
    private String title;
    private String content;
    private Timestamp b_datetime;
    private int views;
    private Timestamp b_s_period;
    private Timestamp b_e_period;
    private String b_state;

    public Long getUser_idx() {
        return user_idx;
    }

    public void setUser_idx(Long user_idx) {
        this.user_idx = user_idx;
    }

    public Long getB_idx() {
        return b_idx;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getB_datetime() {
        return b_datetime;
    }

    public void setB_datetime(Timestamp b_datetime) {
        this.b_datetime = b_datetime;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Timestamp getB_s_period() {
        return b_s_period;
    }

    public void setB_s_period(Timestamp b_s_period) {
        this.b_s_period = b_s_period;
    }

    public Timestamp getB_e_period() {
        return b_e_period;
    }

    public void setB_e_period(Timestamp b_e_period) {
        this.b_e_period = b_e_period;
    }

    public String getB_state() {
        return b_state;
    }

    public void setB_state(String b_state) {
        this.b_state = b_state;
    }


}
