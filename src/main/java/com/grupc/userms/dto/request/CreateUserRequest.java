package com.grupc.userms.dto.request;

import com.sun.istack.NotNull;

public class CreateUserRequest {
    private @NotNull
    String fullName;
    private @NotNull String email;

    public CreateUserRequest (){

    }

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
