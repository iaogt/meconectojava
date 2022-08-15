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
            Categoria c = new Categoria();
            c.setNombre(entry.getKey());
            c.setActividades((Map<String,Object>)entry.getValue());
            this.categorias.put(entry.getKey(),c);
        }
        //this.categorias = categorias;
    }

    public Categoria getCategory(String catName){
        return categorias.get(catName);
    }
}
