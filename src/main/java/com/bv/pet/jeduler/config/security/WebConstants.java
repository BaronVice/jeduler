package com.bv.pet.jeduler.config.security;

public enum WebConstants {
    API_BASE_PATH("/**"),
    AUTHORIZATION_HEADER("Token");

    private String s;
    WebConstants(String s) {
        this.s = s;
    }

    public String getS(){
        return s;
    }

    @Override
    public String toString() {
        return s;
    }
}
