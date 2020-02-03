package com.example.nathaniel.recyclerview;

public class LogModel {
    private String Name;
    private String Number;
    private String timeStamp;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getStatusPhoto() {
        return statusPhoto;
    }

    public void setStatusPhoto(int statusPhoto) {
        this.statusPhoto = statusPhoto;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    private int statusPhoto;
    private int photo;
    public LogModel() {
    }

    public LogModel(String name, String number, String timeStamp, int photo, int statusPhoto) {
        Name = name;
        Number = number;
        this.timeStamp = timeStamp;
        this.photo = photo;
        this.statusPhoto= statusPhoto;
    }





}
