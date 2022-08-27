package com.meconecto.ui.amigos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meconecto.data.UserGameData;

public class AmigosViewModel extends ViewModel {

    private final MutableLiveData<UserGameData> userGData;

    public AmigosViewModel() {
        userGData = new MutableLiveData<>();
    }

    public LiveData<UserGameData> getAmigos() {
        return userGData;
    }

    public void setAmigos(UserGameData ugd){
        userGData.setValue(ugd);
    }
}
