package com.grupc.userms.model.request;

import com.sun.istack.NotNull;

public class CreateUserRequest {
    private @NotNull
    String fullName;
    private @NotNull String email;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
