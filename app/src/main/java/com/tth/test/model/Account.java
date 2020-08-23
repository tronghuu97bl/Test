package com.tth.test.model;

public class Account {

    public static String user;
    public static String pass;

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Account.user = user;
    }

    public static String getPass() {
        return pass;
    }

    public static void setPass(String pass) {
        Account.pass = pass;
    }
}
