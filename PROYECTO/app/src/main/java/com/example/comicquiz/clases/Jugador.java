package com.example.comicquiz.clases;

public class Jugador{

    private String nombre;
    private String tiempo;
    private int correctas;
    private int fallos;

    public Jugador(){
        this.nombre = null;
        this.tiempo = null;
        this.correctas = 0;
        this.fallos = 0;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nuevoNombre){
        this.nombre = nuevoNombre;
    }

    public String getTiempo(){
        return this.tiempo;
    }

    public void setTiempo(String nuevoTiempo){
        this.tiempo = nuevoTiempo;
    }

    public int getCorrectas(){
        return this.correctas;
    }

    public void setCorrectas(int nuevasCorrectas){
        this.correctas = nuevasCorrectas;
    }

    public int getFallos(){
        return this.fallos;
    }

    public void setFallos(int nuevosFallos){
        this.fallos = nuevosFallos;
    }

    /*public int compareTo (Jugador j){
        if (j.getCorrectas() > this.correctas){
            return 1;
        } else if (j.getCorrectas() == this.correctas){
            return 0;
        } else {
            return 1;
        }
    }*/
}
