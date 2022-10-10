package com.meconecto.data;

import com.meconecto.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserGameData {
    public Long punteo;
    public Map<String,String> amigos;
    public String logros;
    public Map<String,Tool> herramientas;
    public String nivel;
    public String actividadesCompletadas;
    public String avatar;

    public final static String NOMNIVEL1 = "nivel1";
    public final static String NOMNIVEL2 = "nivel2";
    public final static String NOMNIVEL3 = "nivel3";
    public final static String NOMNIVEL4 = "nivel4";
    public final static String NOMNIVEL5 = "nivel5";
    public final static String NOMNIVEL6 = "nivel6";

    UserGameData(){
        punteo=new Long(0);
        amigos = new HashMap<>();
        logros = "";
        herramientas = new HashMap<>();
        nivel="nivel1";
        actividadesCompletadas="";
        avatar = "ana";
    }

    public String getAvatar(){
        return avatar;
    }

    public void setNomAvatar(String s){
        avatar = s;
    }

    public void sumarPuntos(Long puntos){
        punteo = punteo + puntos;
    }

    public Boolean evaluarNivel() {
        String n="";
        Boolean result = false;
        if ((punteo > 0) && (punteo <= 30)) {
            n = "nivel1";
        }
        if ((punteo > 30) && (punteo <= 60)) {
            n = "nivel2";
        }
        if ((punteo > 60) && (punteo <= 90)) {
            n = "nivel3";
        }
        if ((punteo > 90) && (punteo <= 120)) {
            n = "nivel4";
        }
        if ((punteo > 120) && (punteo <= 150)) {
            n = "nivel5";
        }
        if ((punteo > 150) && (punteo <= 180)) {
            n = "nivel6";
        }
        if ((punteo > 180) && (punteo <= 210)) {
            n = "nivel7";
        }
        if (nivel != n) result = true;
        nivel=n;
        return result;
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

    public static List getAvatar1(){
        return Arrays.asList(R.drawable.avatarsusan,R.drawable.avatarsusan2,R.drawable.avatarsusan3,R.drawable.avatarsusan4,R.drawable.avatarsusan5,R.drawable.avatarsusan6);
    }

    public static List getAvatar2(){
        return Arrays.asList(R.drawable.avatarjose,R.drawable.avatarjose2,R.drawable.avatarjose3,R.drawable.avatarjose4,R.drawable.avatarjose5,R.drawable.avatarjose6);
    }

    public static List getAvatar3(){
        return Arrays.asList(R.drawable.avatarafro,R.drawable.avatarafro2,R.drawable.avatarafro3,R.drawable.avatarafro4,R.drawable.avatarafro5,R.drawable.avatarafro6);
    }

    public static List getAvatar4(){
        return Arrays.asList(R.drawable.avatarana,R.drawable.avatarana2,R.drawable.avatarana3,R.drawable.avatarana4,R.drawable.avatarana5,R.drawable.avatarana6);
    }

    public static List getAvatar5(){
        return Arrays.asList(R.drawable.avatarmama,R.drawable.avatarmama2,R.drawable.avatarmama3,R.drawable.avatarmama4,R.drawable.avatarmama5,R.drawable.avatarmama6);
    }

    public static List getAvatar6(){
        return Arrays.asList(R.drawable.avatarpapa,R.drawable.avatarpapa2,R.drawable.avatarpapa3,R.drawable.avatarpapa4,R.drawable.avatarpapa5,R.drawable.avatarpapa6);
    }

    public static List getAvatar7(){
        return Arrays.asList(R.drawable.avatarabuelo,R.drawable.avatarabuelo2,R.drawable.avatarabuelo3,R.drawable.avatarabuelo4,R.drawable.avatarabuelo5,R.drawable.avatarabuelo6);
    }

    public static List getNomAvatars(){
        return Arrays.asList("susan","jose","afro","ana","mama","papa","abuelo");
    }



}
