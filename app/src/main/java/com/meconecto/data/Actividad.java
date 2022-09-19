package com.meconecto.data;

import java.io.Serializable;

public class Actividad implements Serializable {
    private String titulo;
    private String desc;
    private Long exito;
    private Long fracaso;
    private String objid;
    private String id;
    private String url;
    private Boolean completada;

    Actividad(){
        completada=false;
    }

    public void setObjId(String id) {
        this.objid = id;
    }

    public void setId(String id){ this.id = id;}

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setExito(Long exito) {
        this.exito = exito;
    }

    public void setFracaso(Long fracaso) {
        this.fracaso = fracaso;
    }

    public void setUrl(String uri){ this.url=uri;}

    public String getUrl(){ return this.url;}

    public String getId() {
        return id;
    }

    public Long getExito() {
        return exito;
    }

    public Long getFracaso() {
        return fracaso;
    }

    public Boolean getCompletada(){
        return completada;
    }

    public void setCompletada(Boolean t){
        this.completada = t;
    }
}
