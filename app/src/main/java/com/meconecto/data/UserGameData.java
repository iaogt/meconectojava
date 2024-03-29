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
    public String nombreavatar;

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
        nombreavatar="Ana";
    }

    public String getAvatar(){
        return avatar;
    }

    public void setImgAvatar(String s){
        avatar = s;
    }

    public String getNomAvatar(){ return nombreavatar; }

    public void setNomAvatar(String s){ nombreavatar=s;}

    public void sumarPuntos(Long puntos){
        punteo = punteo + puntos;
    }

    public Long getPunteo(){
        return punteo;
    }

    public Boolean evaluarNivel() {
        String n="";
        Boolean result = false;
        if ((punteo > 0) && (punteo <= 40)) {
            n = "nivel1";
        }
        if ((punteo > 40) && (punteo <= 70)) {
            n = "nivel2";
        }
        if ((punteo > 70) && (punteo <= 100)) {
            n = "nivel3";
        }
        if ((punteo > 100) && (punteo <= 140)) {
            n = "nivel4";
        }
        if ((punteo > 140) && (punteo <= 180)) {
            n = "nivel5";
        }
        if ((punteo > 180)) {
            n = "nivel6";
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

    public Boolean amigoExists(String a){
        Boolean encontro=false;
        if(amigos.containsKey(a)){
            encontro=true;
        }
        return encontro;
    }

    public void addAmigo(String a){
        amigos.put(a,a);
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

    /**
     * La primer actividad
     * */
    public boolean checkLogro1(){
        boolean result=false;
        if(punteo>0 && logros.length()==0){
            result=true;
        }
        return result;
    }

    /*
    * Todas completadas
    * */
    public boolean checkLogro2(ArrayList<String> idsActivs){
        boolean result=true;
        for(int i= 0;i<idsActivs.size();i++){
            if(actividadesCompletadas.indexOf(idsActivs.get(i))<0){
                result=false;
            }
        }
        return result;
    }

    public ArrayList checkLogros(ArrayList<String> idsActivs){
        ArrayList logros = new ArrayList();
        if(checkLogro1()){
            logros.add("logro1");
        }
        if(checkLogro2(idsActivs)){
            logros.add("logro2");
        }
        return logros;
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
        logro1.put("nombre","Primera Actividad");
        logro1.put("imagen", String.valueOf(R.drawable.insignia1));
        collLogros.put("logro1",logro1);
        HashMap<String,String> logro2 = new HashMap<>();
        logro2.put("nombre","Categoria completa");
        logro2.put("imagen", String.valueOf(R.drawable.insignia2));
        collLogros.put("logro2",logro2);
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
        return Arrays.asList(R.drawable.avatarluisa,R.drawable.avatarluisa2,R.drawable.avatarluisa3,R.drawable.avatarluisa4,R.drawable.avatarluisa5,R.drawable.avatarluisa6);
    }

    public static List getAvatar6(){
        return Arrays.asList(R.drawable.avatarmama,R.drawable.avatarmama2,R.drawable.avatarmama3,R.drawable.avatarmama4,R.drawable.avatarmama5,R.drawable.avatarmama6);
    }

    public static List getAvatar7(){
        return Arrays.asList(R.drawable.avatarpapa,R.drawable.avatarpapa2,R.drawable.avatarpapa3,R.drawable.avatarpapa4,R.drawable.avatarpapa5,R.drawable.avatarpapa6);
    }

    public static List getAvatar8(){
        return Arrays.asList(R.drawable.avatarabuelo,R.drawable.avatarabuelo2,R.drawable.avatarabuelo3,R.drawable.avatarabuelo4,R.drawable.avatarabuelo5,R.drawable.avatarabuelo6);
    }

    public static List getNomAvatars(){
        return Arrays.asList("susan","jose","afro","ana","luisa","mama","papa","abuelo");
    }



}
