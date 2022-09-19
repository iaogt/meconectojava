package com.meconecto.data;

public class Logro {
    private String id;
    private String nombre;
    private String desc;

    public String getId(){
        return id;
    }

    public void setId(String i){
        this.id = i;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
