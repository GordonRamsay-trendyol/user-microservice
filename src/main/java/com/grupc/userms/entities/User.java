package com.grupc.userms.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String fullName;

    @Column(unique = true)
    @NotNull
    private String eMail;

    public User(){

    }

    public User(String fullName, @NotNull String eMail){
        this.fullName = fullName;
        this.eMail = eMail;
    }

    public User(Long id, String fullName, String email){
        this.id = id;
        this.fullName = fullName;
        this.eMail = email;
    }

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

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }
}
