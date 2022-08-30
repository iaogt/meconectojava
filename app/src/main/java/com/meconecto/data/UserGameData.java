package com.meconecto.data;

import java.util.HashMap;
import java.util.Map;

public class UserGameData {
    public Long punteo;
    public Map<String,String> amigos;
    public Map<String,String> logros;

    UserGameData(){
        punteo=new Long(0);
        amigos = new HashMap<>();
        logros = new HashMap<>();
    }

    public void sumarPuntos(Long puntos){
        punteo = punteo + puntos;
    }

    public void restarPuntos(Long puntos){
        punteo = punteo - puntos;
    }

    public Map<String, String> getAmigos() {
        return amigos;
    }
}
