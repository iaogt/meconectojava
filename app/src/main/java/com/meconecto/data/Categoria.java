package com.meconecto.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Categoria implements Serializable {
    private String nombre;
    private HashMap<String,Actividad> actividades;

    Categoria(){
        this.actividades = new HashMap<String,Actividad>();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setActividades(Map<String,Object> listActs) {
        for(Map.Entry<String, Object> entry: listActs.entrySet()){
            Actividad a = new Actividad();
            Map<String,Object> vals = (Map<String,Object>)entry.getValue();
            a.setObjId(entry.getKey());
            a.setId((String)vals.get("id"));
            a.setTitulo((String)vals.get("titulo"));
            a.setDesc((String)vals.get("desc"));
            a.setExito((Long)vals.get("exito"));
            a.setFracaso((Long)vals.get("fracaso"));
            a.setUrl((String)vals.get("url"));
            this.actividades.put(entry.getKey(),a);
        }
    }

    public HashMap<String,Actividad> getActividades(){
        return actividades;
    }

    public ArrayList<Url4Download> getUrls(){
        ArrayList<Url4Download> resultado = new ArrayList<>();
        for(Map.Entry<String, Actividad> entry: actividades.entrySet()){
            Url4Download url = new Url4Download(entry.getValue().getUrl(),entry.getValue().getId());
            resultado.add(url);
        }
        return resultado;
    }
}
