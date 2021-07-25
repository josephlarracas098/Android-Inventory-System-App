package com.pylontradingintl.android_inventory_system_app;

public class User {
    private String companyName,email,password,confirmedPassword;

    public User(){

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String companyName, String email, String password) {
        this.companyName = companyName;
        this.email = email;
        this.password = password;
    }


    public User(String companyName, String email, String password, String confirmedPassword) {
        this.companyName = companyName;
        this.email = email;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
