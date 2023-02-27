package com.example.comicquiz.clases;

import static java.lang.Integer.parseInt;

import java.util.Comparator;

public class CompararJugadores implements Comparator<Jugador> {

    @Override
    public int compare(Jugador j1, Jugador j2) {
        if (j1.getCorrectas() > j2.getCorrectas()){
            return -1;
        } else if (j1.getCorrectas() == j2.getCorrectas()){

            int tiempoJ1 = obtenerTiempo(j1.getTiempo());
            int tiempoJ2 = obtenerTiempo(j2.getTiempo());

            if (tiempoJ1<tiempoJ2){
                return -1;
            }else if(tiempoJ1 == tiempoJ2){
                return 0;
            }else{
                return 1;
            }

        } else {
            return 1;
        }
    }

    public int obtenerTiempo (String tiempoCadena){

        String[] partesTiempo = tiempoCadena.split(":");

        int minutos  = parseInt(partesTiempo[0],10);
        int segundos = parseInt(partesTiempo[1],10);

        int tiempoTotal = (minutos*60) + segundos;

        return tiempoTotal;
    }

}
