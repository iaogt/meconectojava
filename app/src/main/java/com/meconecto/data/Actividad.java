package com.meconecto.data;

import java.io.Serializable;

public class Actividad implements Serializable {
    private String titulo;
    private String desc;
    private Integer exito;
    private Integer fracaso;
    private String id;

    Actividad(){}

    public void setId(String id) {
        this.id = id;
    }

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

    public void setExito(Integer exito) {
        this.exito = exito;
    }

    public void setFracaso(Integer fracaso) {
        this.fracaso = fracaso;
    }
}
