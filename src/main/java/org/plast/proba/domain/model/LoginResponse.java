package org.plast.proba.domain.model;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    private final String jwttoken;

    public LoginResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}