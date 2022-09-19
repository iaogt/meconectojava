package com.meconecto.data;

public class Nivel {
    private String id;
    private String descripcion;
    private String nombre;
    private Long puntos_mayor;
    private Long puntos_menor;

    public String getId() {
        return id;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public String getNombre(){
        return nombre;
    }

    public Long getPuntos_mayor(){
        return puntos_mayor;
    }

    public Long getPuntos_menor(){
        return puntos_menor;
    }

    public void setId(String id){
        id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPuntos_mayor(Long puntos_mayor) {
        this.puntos_mayor = puntos_mayor;
    }

    public void setPuntos_menor(Long puntos_menor) {
        this.puntos_menor = puntos_menor;
    }
}
