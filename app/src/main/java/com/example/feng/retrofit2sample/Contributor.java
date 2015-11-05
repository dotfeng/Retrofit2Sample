package com.example.feng.retrofit2sample;

/**
 * Created by feng on 2015/11/5.
 */
public class Contributor {
    public final String login;
    public final int contributions;

    public String getLogin() {
        return login;
    }

    public int getContributions() {
        return contributions;
    }

    public Contributor(String login, int contributions) {
        this.login = login;
        this.contributions = contributions;
    }
}
