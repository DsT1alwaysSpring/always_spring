package com.example.alwaysspring.model;

import java.sql.Timestamp;

public class B_Comment {
    private Long c_idx;
    private Long user_idx;
    private Long b_idx;
    private Timestamp c_date;
    private String com_content;

    public Long getC_idx() {
        return c_idx;
    }

    public Long getUser_idx() {
        return user_idx;
    }

    public Long getB_idx() {
        return b_idx;
    }

    public String getCom_content() {
        return com_content;
    }

    public void setCom_content(String com_content) {
        this.com_content = com_content;
    }
}
