package com.kms.smartsiren;

public class UserAccount {
    private String emailID;
    private String password;
    private String name;
    private int reliability;
    private int reportN;
    private int reportG;

    public UserAccount(String emailID, String password, String name, int reliability, int reportN, int reportG) {
        this.emailID = emailID;
        this.password = password;
        this.name = name;
        this.reliability = reliability;
        this.reportN = reportN;
        this.reportG = reportG;
    }

    public int getReportG() {
        return reportG;
    }

    public void setReportG(int reportG) {
        this.reportG = reportG;
    }


    public int getReportN() {
        return reportN;
    }

    public void setReportN(int reportN) {
        this.reportN = reportN;
    }


    public int getReliability() {
        return reliability;
    }

    public void setReliability(int reliability) {
        this.reliability = reliability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserAccount() { } //빈 생성자 꼭 생성해야 DB접근 가능
}
