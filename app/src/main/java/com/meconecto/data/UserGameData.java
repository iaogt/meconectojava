package com.meconecto.data;

import java.util.HashMap;
import java.util.Map;

public class UserGameData {
    public Long punteo;
    public Map<String,String> amigos;
    public Map<String,String> logros;
    public Map<String,Tool> herramientas;
    public String nivel;
    public String actividadesCompletadas;

    UserGameData(){
        punteo=new Long(0);
        amigos = new HashMap<>();
        logros = new HashMap<>();
        herramientas = new HashMap<>();
        nivel="nivel1";
        actividadesCompletadas="";
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

    public void setHerramientas(Map<String,Tool> tools){
        this.herramientas = tools;
    }

    public String getNivel(){
        return nivel;
    }

    public void addCompleted(String idActiv){
        actividadesCompletadas = actividadesCompletadas.concat(","+idActiv);
    }

    public String getActividadesCompletadas(){
        return actividadesCompletadas;
    }

    public void setActividadesCompletadas(String c){
        this.actividadesCompletadas = c;
    }
}
