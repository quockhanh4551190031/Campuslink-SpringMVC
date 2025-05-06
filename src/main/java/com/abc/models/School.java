package com.abc.models;

public class School {
    private int maTruong;
    private String tenTruong;

    public School(int maTruong, String tenTruong) {
        this.maTruong = maTruong;
        this.tenTruong = tenTruong;
    }

    // Getters và Setters
    public int getMaTruong() {
        return maTruong;
    }

    public void setMaTruong(int maTruong) {
        this.maTruong = maTruong;
    }

    public String getTenTruong() {
        return tenTruong;
    }

    public void setTenTruong(String tenTruong) {
        this.tenTruong = tenTruong;
    }
}