package com.example.quiz.models;

public class Jugador {
    private String nombre;

    public Jugador (String nombre){
        this.nombre = nombre;
    }
    public String getNombre(){
        return this.nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
}
