package com.meconecto.data;

import com.meconecto.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UserGameData {
    public Long punteo;
    public Map<String,String> amigos;
    public String logros;
    public Map<String,Tool> herramientas;
    public String nivel;
    public String actividadesCompletadas;

    UserGameData(){
        punteo=new Long(0);
        amigos = new HashMap<>();
        logros = "";
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

    public boolean checkLogro1(){
        boolean result=false;
        if(punteo>0 && logros.length()==0){
            result=true;
        }
        return result;
    }

    public boolean checkLogro2(ArrayList<String> idsActivs){
        boolean result=true;
        for(int i= 0;i<idsActivs.size();i++){
            if(actividadesCompletadas.indexOf(idsActivs.get(i))<0){
                result=false;
            }
        }
        return result;
    }

    public String checkLogros(ArrayList<String> idsActivs){
        String strLogros = "";
        if(checkLogro1()){
            strLogros = strLogros.concat("logro1");
        }
        if(checkLogro2(idsActivs)){
            strLogros = strLogros.concat(",logro2");
        }
        return strLogros;
    }

    public String getLogros(){
        return logros;
    }

    public ArrayList<String> getArrLogros(){
        return new ArrayList<String>(Arrays.asList(logros.split(",")));
    }

    public void setLogros(String newlogros){
        logros = newlogros;
    }

    public HashMap<String,String> getLogroData(String nomLogro){
        HashMap<String,HashMap<String,String>> collLogros = new HashMap<>();
        HashMap<String,String> logro1 = new HashMap<>();
        logro1.put("nombre","Kinder");
        logro1.put("imagen", String.valueOf(R.drawable.insignia1));
        collLogros.put("logro1",logro1);
        return collLogros.get(nomLogro);
    }

}
