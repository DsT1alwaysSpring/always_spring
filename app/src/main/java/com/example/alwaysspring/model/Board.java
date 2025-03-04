package com.example.alwaysspring.model;

import java.sql.Timestamp;

public class Board {
    private Long b_idx; // 게시글 ID (자동 증가)
    private Long user_idx; // 사용자 ID (외래키)
    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private Timestamp b_datetime; // 게시글 생성 날짜 및 시간
    private int views = 0; // 게시글 조회수 (기본값 0)
    private Timestamp b_s_period; // 게시글 시작 기간
    private Timestamp b_e_period; // 게시글 종료 기간
    private String b_state; // 게시글 상태 (예: "공개", "비공개")

    // 기본 생성자
    public Board() {
    }

    // 필드를 초기화하는 생성자 추가 (편의성 제공)
    public Board(Long user_idx, String title, String content, Timestamp b_s_period, Timestamp b_e_period, String b_state) {
        this.user_idx = user_idx;
        this.title = title;
        this.content = content;
        this.b_s_period = b_s_period;
        this.b_e_period = b_e_period;
        this.b_state = b_state;
    }

    // Getter와 Setter 메서드

    public Long getB_idx() {
        return b_idx;
    }

    public void setB_idx(Long b_idx) {
        this.b_idx = b_idx;
    }

    public Long getUser_idx() {
        return user_idx;
    }

    public void setUser_idx(Long user_idx) {
        this.user_idx = user_idx;
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

    // toString 메서드 (디버깅 및 로깅에 유용)
    @Override
    public String toString() {
        return "Board{" +
                "b_idx=" + b_idx +
                ", user_idx=" + user_idx +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", b_datetime=" + b_datetime +
                ", views=" + views +
                ", b_s_period=" + b_s_period +
                ", b_e_period=" + b_e_period +
                ", b_state='" + b_state + '\'' +
                '}';
    }
}