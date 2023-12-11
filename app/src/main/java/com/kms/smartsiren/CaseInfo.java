package com.kms.smartsiren;

public class CaseInfo {
    private double latitude; // 위도
    private double longitude; // 경도
<<<<<<< HEAD
    private int category; //사건 분류
    private int rating; //위험 등급
    private String detail; // 사건 정보
    //private String UUID;

    public CaseInfo(){}

    public CaseInfo(double latitude, double longitude, int category, int rating, String detail) {
=======
    private char category; //사건 분류
    private char rating; //위험 등급
    private String detail; // 사건 정보

    public CaseInfo(){}

    public CaseInfo(double latitude, double longitude, char category, char rating, String detail) {
>>>>>>> test
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.rating = rating;
        this.detail = detail;
    }

    public int getDistance(){
        int dis = 0;
        switch (category){
<<<<<<< HEAD
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
=======
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
>>>>>>> test
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

<<<<<<< HEAD
    public int getCategory() {
=======
    public char getCategory() {
>>>>>>> test
        return category;
    }

    public void setCategory(char category) {
        this.category = category;
    }

<<<<<<< HEAD
    public int getRating() {
=======
    public char getRating() {
>>>>>>> test
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
