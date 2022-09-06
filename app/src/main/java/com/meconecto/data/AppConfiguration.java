package com.meconecto.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppConfiguration implements Serializable {
    private Long version;
    private HashMap<String,Categoria> categorias;

    AppConfiguration(){
        this.categorias = new HashMap<String,Categoria>();
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setCategorias(Map<String,Object> listCats) {
        for(Map.Entry<String, Object> entry: listCats.entrySet()){
            Map<String,Object> catConfig = (Map<String,Object>)entry.getValue();
            Categoria c = new Categoria();
            c.setNombre((String)catConfig.get("nombre"));
            c.setDescription((String)catConfig.get("description"));
            c.setSubtitle((String)catConfig.get("subtitle"));
            c.setVideourl((String)catConfig.get("videourl"));
            c.setActividades((Map<String,Object>)catConfig.get("actividades"));
            this.categorias.put(entry.getKey(),c);
        }
        //this.categorias = categorias;
    }

    public Categoria getCategory(String catName){
        return categorias.get(catName);
    }

    public ArrayList<Url4Download> getUrls(){
        ArrayList<Url4Download> resultado=new ArrayList<>();
        for(Map.Entry<String, Categoria> entry: categorias.entrySet()){
            resultado.addAll(entry.getValue().getUrls());
        }
        return resultado;
    }
}
