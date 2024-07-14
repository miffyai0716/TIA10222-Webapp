package com.member.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Member implements Serializable {

    private Long memberId; // 使用者ID
    private Timestamp regisDate; // 註冊日期
    private String name; // 使用者名稱
    private Byte gender; // 性別
    private String account; // 帳號
    private String password; // 密碼
    private String mobileNo; // 手機號碼
    private String email; // 電子信箱
    private byte[] sticker; // 照片


    public Member(Long memberId, Timestamp regisDate, String name, Byte gender, String account, String password,
                  String mobileNo, String email, byte[] sticker) {
        super();
        this.memberId = memberId;
        this.regisDate = regisDate;
        this.gender = gender;
        this.name = name;
        this.account = account;
        this.password = password;
        this.mobileNo = mobileNo;
        this.email = email;
        this.sticker = sticker;
    }

    public Member() {

    }

    // Getters and Setters
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Timestamp getRegisDate() {
        return regisDate;
    }

    public void setRegisDate(Timestamp regisDate) {
        this.regisDate = regisDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getSticker() {
        return sticker;
    }

    public void setSticker(byte[] sticker) {
        this.sticker = sticker;
    }

}
