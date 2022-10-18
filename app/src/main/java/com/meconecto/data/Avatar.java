package com.meconecto.data;

public class Avatar {
    String nombre;
    String imgAvatar;

    public String getNombre() {
        return nombre;
    }

    public String getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(String img){
        imgAvatar=img;
    }

    public void setNombre(String s){
        nombre=s;
    }
}
