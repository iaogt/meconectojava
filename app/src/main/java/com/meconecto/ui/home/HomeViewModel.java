package com.meconecto.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meconecto.data.UserGameData;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<UserGameData> userGData;

    public HomeViewModel() {
        userGData = new MutableLiveData<>();
    }

    public LiveData<UserGameData> getuserGData() {
        return userGData;
    }

    public void setmText(UserGameData t){
        userGData.setValue(t);
    }
}