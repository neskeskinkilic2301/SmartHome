package com.cenah.smarthome.models;

public class User {

    private String name;
    private String surName;
    private String userName;
    private String email;
    private String password;
    private String phone;
    private boolean fingerEnabled;

    public User(String name, String surName, String userName, String email, String password, String phone, boolean fingerEnabled) {
        this.name = name;
        this.surName = surName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.fingerEnabled = fingerEnabled;
    }

    public User() {
    }

    public boolean isFingerEnabled() {
        return fingerEnabled;
    }

    public void setFingerEnabled(boolean fingerEnabled) {
        this.fingerEnabled = fingerEnabled;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
