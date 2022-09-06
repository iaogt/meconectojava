package com.meconecto.web;

import android.content.Context;
import android.view.View;
import android.webkit.JavascriptInterface;

import androidx.fragment.app.FragmentManager;

import com.meconecto.data.Actividad;
import com.meconecto.ui.modals.Modal1;
import com.meconecto.ui.modals.Modal2;

public class WebActivities {
    FragmentManager fragMana;
    Actividad selActiv;
    Long punteo;
    View.OnClickListener cerrarClickExitoso;
    View.OnClickListener cerrarClickFallido;

    public WebActivities(FragmentManager fm, Actividad a, View.OnClickListener c, View.OnClickListener f){
        fragMana = fm;
        selActiv = a;
        punteo = new Long(0);
        cerrarClickExitoso = c;
        cerrarClickFallido = f;
    }

    @JavascriptInterface
    public void terminarExitosa(){
        punteo = punteo + selActiv.getExito();
        Modal1 m = new Modal1();
        m.setPuntos(punteo);
        m.setCerrarClick(cerrarClickExitoso);
        m.show(fragMana, Modal1.TAG);
    }

    @JavascriptInterface
    public void terminarFallida(){
        punteo = punteo - selActiv.getFracaso();
        Modal2 m = new Modal2();
        m.setPuntos(punteo);
        m.setCerrarClick(cerrarClickFallido);
        m.show(fragMana, Modal1.TAG);
    }

    public Long getPunteo() {
        return punteo;
    }
}
