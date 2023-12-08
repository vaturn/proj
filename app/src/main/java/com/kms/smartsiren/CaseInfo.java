package com.kms.smartsiren;

public class CaseInfo {
    private double latitude; // 위도
    private double longitude; // 경도
    private char category; //사건 분류
    private char rating; //위험 등급
    private String detail; // 사건 정보

    public CaseInfo(){}

    public CaseInfo(double latitude, double longitude, char category, char rating, String detail) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.rating = rating;
        this.detail = detail;
    }

    public int getDistance(){
        int dis = 0;
        switch (category){
            case 'A':
                break;
            case 'B':
                break;
            case 'C':
                break;
            case 'D':
                break;
            case 'E':
                break;
            case 'F':
                break;
            default:
                break;
        }
        return dis;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public char getCategory() {
        return category;
    }

    public void setCategory(char category) {
        this.category = category;
    }

    public char getRating() {
        return rating;
    }

    public void setRating(char rating) {
        this.rating = rating;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
