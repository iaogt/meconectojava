package com.meconecto.data;

public class UserGameData {
    public Long punteo;

    UserGameData(){
        punteo=new Long(0);
    }

    public void sumarPuntos(Long puntos){
        punteo = punteo + puntos;
    }

    public void restarPuntos(Long puntos){
        punteo = punteo - puntos;
    }
}
