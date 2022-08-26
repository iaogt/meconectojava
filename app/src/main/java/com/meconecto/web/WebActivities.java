package com.meconecto.web;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.meconecto.data.Actividad;

public class WebActivities {
    Context mContext;
    Actividad selActiv;
    Long punteo;

    public WebActivities(Context c,Actividad a){
        mContext = c;
        selActiv = a;
        punteo = new Long(0);
    }

    @JavascriptInterface
    public void terminarExitosa(){
        punteo = punteo + selActiv.getExito();
    }

    @JavascriptInterface
    public void terminarFallida(){
        punteo = punteo - selActiv.getFracaso();
    }

    public Long getPunteo() {
        return punteo;
    }
}
