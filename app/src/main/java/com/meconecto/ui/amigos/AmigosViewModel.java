package com.meconecto.ui.amigos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meconecto.data.UserGameData;

public class AmigosViewModel extends ViewModel {

    private final MutableLiveData<UserGameData> userGData;
    private final MutableLiveData<String> nuevoAmigo;

    public AmigosViewModel() {
        userGData = new MutableLiveData<>();
        nuevoAmigo = new MutableLiveData<>();
    }

    public LiveData<UserGameData> getAmigos() {
        return userGData;
    }

    public void setAmigos(UserGameData ugd){
        userGData.setValue(ugd);
    }

    public void setNuevoAmigo(String p){ nuevoAmigo.setValue(p); }

    public LiveData<String> getNuevoAmigo(){ return nuevoAmigo; }
}
