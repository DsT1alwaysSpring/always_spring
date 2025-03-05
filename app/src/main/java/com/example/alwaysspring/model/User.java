package com.example.alwaysspring.model;

public class User {
    private Long userIdx;
    private String name;
    private String nickname;
    private String birth;
    private String phone;
    private String address;
    private String password;
    private String profile;
    private String appeal;
    private String keyword;
    private String purpose;

    public User(String name, String nickname, String birth, String phone, String address, String password) {
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    public Long getUserIdx() {  // 변경
        return userIdx;
    }

    public void setUserIdx(Long userIdx) {  // 변경
        this.userIdx = userIdx;
    }

//    public Long getUser_idx() {
//        return userIdx;
//    }
//    public void setUser_idx(Long user_idx) {
//        this.userIdx = user_idx;
//    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getAppeal() {
        return appeal;
    }

    public void setAppeal(String appeal) {
        this.appeal = appeal;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}