package com.grupc.userms.dto.request;

import com.sun.istack.NotNull;

public class UpdateUserRequest {
    private @NotNull
    Long id;
    private @NotNull String fullName;
    private @NotNull String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
