package com.example.alwaysspring.model;

import java.time.LocalDate;

public class Friends {

        private int fIdx; // 친구 인덱스
        private User user; // 사용자 정보
        private int fUser; // 친구 사용자 인덱스
        private String friendRequestStatus; // 친구 요청 상태
        private LocalDate fDatetime; // 친구 관계 시작 날짜
        private String friendName;

        // 기본 생성자
        public Friends() {
        }

        // 생성자 (필드를 초기화하는 생성자)
        public Friends(User user, int fUser, String friendRequestStatus, LocalDate fDatetime) {
            this.user = user;
            this.fUser = fUser;
            this.friendRequestStatus = friendRequestStatus;
            this.fDatetime = fDatetime;
        }

        public String getFriendName() {
            return friendName;
        }

        public void setFriendName(String friendName) {
            this.friendName = friendName;
        }
        public int getfIdx() {
            return fIdx;
        }

        public void setfIdx(int fIdx) {
            this.fIdx = fIdx;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public int getfUser() {
            return fUser;
        }

        public void setfUser(int fUser) {
            this.fUser = fUser;
        }

        public String getFriendRequestStatus() {
            return friendRequestStatus;
        }

        public void setFriendRequestStatus(String friendRequestStatus) {
            this.friendRequestStatus = friendRequestStatus;
        }

        public LocalDate getfDatetime() {
            return fDatetime;
        }

        public void setfDatetime(LocalDate fDatetime) {
            this.fDatetime = fDatetime;
        }

        @Override
        public String toString() {
            return "Friends{" +
                    "fIdx=" + fIdx +
                    ", user=" + user +
                    ", fUser=" + fUser +
                    ", friendRequestStatus='" + friendRequestStatus + '\'' +
                    ", fDatetime=" + fDatetime +
                    '}';
        }

}
