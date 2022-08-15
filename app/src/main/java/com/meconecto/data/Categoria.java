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
            a.setId(entry.getKey());
            a.setTitulo((String)vals.get("titulo"));
            a.setDesc((String)vals.get("desc"));
            this.actividades.put(entry.getKey(),a);
        }
    }

    public HashMap<String,Actividad> getActividades(){
        return actividades;
    }
}
