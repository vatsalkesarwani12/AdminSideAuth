package com.vatsal.kesarwani.adminapp;

class DataParam {
    private String Email;
    private String Password;
    private int Status;

    public DataParam() {
    }

    public DataParam(String email, String password, int status) {
        Email = email;
        Password = password;
        Status = status;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
