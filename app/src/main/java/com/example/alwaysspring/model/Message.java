package com.example.alwaysspring.model;

import java.sql.Timestamp;

public class Message {
    private Long m_idx;
    private Long user_idx;
    private Long chat_room_idx;
    private String m_content;
    private Timestamp m_datetime;

    public String getM_content() {
        return m_content;
    }

    public void setM_content(String m_content) {
        this.m_content = m_content;
    }

    public Long getChat_room_idx() {
        return chat_room_idx;
    }

    public void setChat_room_idx(Long chat_room_idx) {
        this.chat_room_idx = chat_room_idx;
    }

    public Timestamp getM_datetime() {
        return m_datetime;
    }

    public void setM_datetime(Timestamp m_datetime) {
        this.m_datetime = m_datetime;
    }

    public Long getUser_idx() {
        return user_idx;
    }

    public void setUser_idx(Long user_idx) {
        this.user_idx = user_idx;
    }

    public Long getM_idx() {
        return m_idx;
    }

    public void setM_idx(Long m_idx) {
        this.m_idx = m_idx;
    }
}
