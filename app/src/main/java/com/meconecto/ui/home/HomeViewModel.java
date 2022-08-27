package com.meconecto.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meconecto.data.UserGameData;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<UserGameData> userGData;
    private final MutableLiveData<String> userId;

    public HomeViewModel() {
        userGData = new MutableLiveData<>();
        userId = new MutableLiveData<>();
    }

    public LiveData<UserGameData> getuserGData() {
        return userGData;
    }

    public void setmText(UserGameData t){
        userGData.setValue(t);
    }

    public void setUserId(String ui){
        userId.setValue(ui);
    }

    public LiveData<String> getUserId(){
        return userId;
    }

}