package com.meconecto.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppConfiguration implements Serializable {
    private Long version;
    private HashMap<String,Categoria> categorias;
    private HashMap<String,Nivel> niveles;
    private HashMap<String,Tool> herramientas;
    private HashMap<String,Logro> logros;

    AppConfiguration(){
        this.categorias = new HashMap<String,Categoria>();
        this.niveles = new HashMap<String,Nivel>();
        this.herramientas = new HashMap<String, Tool>();
        this.logros = new HashMap<String,Logro>();
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

    public void setNiveles(Map<String,Object> listNives) {
        for(Map.Entry<String, Object> entry: listNives.entrySet()){
            Map<String,Object> nivConfig = (Map<String,Object>)entry.getValue();
            Nivel n = new Nivel();
            n.setId((String)nivConfig.get("id"));
            n.setDescripcion((String)nivConfig.get("descripcion"));
            n.setNombre((String)nivConfig.get("nombre"));
            n.setPuntos_mayor((Long)nivConfig.get("puntos_mayor"));
            n.setPuntos_menor((Long)nivConfig.get("puntos_menor"));
            this.niveles.put(entry.getKey(),n);
        }
        //this.categorias = categorias;
    }

    public Map<String,Nivel> getNiveles(){
        return this.niveles;
    }

    public void setTools(Map<String,Object> listTools) {
        for(Map.Entry<String, Object> entry: listTools.entrySet()){
            Map<String,Object> nivConfig = (Map<String,Object>)entry.getValue();
            Tool n = new Tool();
            n.setId((String)nivConfig.get("id"));
            n.setDescripcion((String)nivConfig.get("descripcion"));
            n.setNombre((String)nivConfig.get("nombre"));
            n.setCantidad((Long)nivConfig.get("cantidad"));
            this.herramientas.put(entry.getKey(),n);
        }
        //this.categorias = categorias;
    }

    public void setLogros(Map<String,Object> logros) {
        for(Map.Entry<String, Object> entry: logros.entrySet()){
            Map<String,Object> logroConfig = (Map<String,Object>)entry.getValue();
            Logro n = new Logro();
            n.setId((String)entry.getKey());
            n.setDesc((String)logroConfig.get("desc"));
            n.setNombre((String)logroConfig.get("nombre"));
            this.logros.put(entry.getKey(),n);
        }
        //this.categorias = categorias;
    }

    public Map<String,Tool> getTools(){
        return this.herramientas;
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
