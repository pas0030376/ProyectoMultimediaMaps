package com.example.proyectomultimediamaps;

import java.io.Serializable;

/**
 * Created by Vicky on 17/04/2018.
 */

public class Places implements Serializable{
    private String nombre;
    private double lat;
    private double longi;

    public Places(String nombre, double lat, double longi) {
        this.nombre = nombre;
        this.lat = lat;
        this.longi = longi;
    }

    public  Places(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }
}
