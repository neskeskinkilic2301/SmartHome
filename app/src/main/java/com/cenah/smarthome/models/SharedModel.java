package com.cenah.smarthome.models;

public class SharedModel {

    private User user;
    private boolean remember;

    public SharedModel(User user, boolean remember) {
        this.user = user;
        this.remember = remember;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }
}
