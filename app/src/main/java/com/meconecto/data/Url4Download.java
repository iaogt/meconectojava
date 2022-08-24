package com.meconecto.data;

public class Url4Download {

    private String url;
    private String ID;

    public Url4Download(String url,String id){
        this.url = url;
        this.ID = id;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getID() {
        return ID;
    }

    public String getUrl() {
        return url;
    }
}
