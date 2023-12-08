package com.kms.smartsiren;

import java.util.ArrayList;

public class UnconfirmedCase extends CaseInfo{
    private int personnel; //인원 수
    ArrayList<String> informants;

    public UnconfirmedCase(){}

    public UnconfirmedCase(double latitude, double longitude, char category, char rating, String detail, String informant) {
        super(latitude, longitude, category, rating, detail);
        this.informants = new ArrayList<>();
        this.informants.add(informant);
        this.personnel = this.informants.size();
    }

    public void addReport(double latitude, double longitude, String informant){
        setLatitude( (getLatitude() * personnel + latitude) / (personnel + 1) );
        setLongitude( (getLongitude() * personnel + longitude) / (personnel + 1));
        personnel += 1;
        informants.add(informant);
    }

    public int getPersonnel() {
        return personnel;
    }

    public void setPersonnel(int personnel) {
        this.personnel = personnel;
    }

    public ArrayList<String> getInformants() {
        return informants;
    }

    public void setInformants(ArrayList<String> informants) {
        this.informants = informants;
    }
}
