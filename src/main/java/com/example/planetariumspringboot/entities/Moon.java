package com.example.planetariumspringboot.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "moons")
public class Moon {
    @Id
    @Column(name = "moon_id")
    private int moonId;

    @Column(name = "moon_name")
    private String moonName;

    @Column(name = "my_planet_id")
    private int myPlanetId;

    public int getMoonId() {
        return moonId;
    }

    public void setMoonId(int moonId) {
        this.moonId = moonId;
    }

    public String getMoonName() {
        return moonName;
    }

    public void setMoonName(String moonName) {
        this.moonName = moonName;
    }

    public int getMyPlanetId() {
        return myPlanetId;
    }

    public void setMyPlanetId(int myPlanetId) {
        this.myPlanetId = myPlanetId;
    }

    @Override
    public String toString() {
        return "Moon{" +
                "moonId=" + moonId +
                ", moonName='" + moonName + '\'' +
                ", myPlanetId=" + myPlanetId +
                '}';
    }
}
