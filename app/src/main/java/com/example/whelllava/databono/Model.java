package com.example.whelllava.databono;


import java.util.ArrayList;

public class Model {
    String name ;
    String email;
    int gender;

    public Model() {
    }

    public Model(String name, String email, int gender ) {
        this.name = name;
        this.email = email;
        this.gender = gender;

    }



    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getGender() {
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
