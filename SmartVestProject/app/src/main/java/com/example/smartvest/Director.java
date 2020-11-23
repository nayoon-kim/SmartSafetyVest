package com.example.smartvest;

public class Director {

    private int director_id;

    public Director() {
    }

    public Director(int director_id) {
        setDirector_id(director_id);
    }

    public int getDirector_id() {
        return director_id;
    }
    public void setDirector_id(int director_id) {
        this.director_id = director_id;
    }
}
