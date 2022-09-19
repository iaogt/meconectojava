package com.meconecto;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meconecto.data.Actividad;
import com.meconecto.data.Categoria;

public class FirstFragmentModel extends ViewModel {
    private final MutableLiveData<Categoria> category;
    private final MutableLiveData<Actividad> selectedActivity;
    private final MutableLiveData<String> completedActivs;

    public FirstFragmentModel(){
        category = new MutableLiveData<>();
        selectedActivity = new MutableLiveData<>();
        completedActivs = new MutableLiveData<>();
    }

    public LiveData<Categoria> getCategory(){
        return category;
    }

    public void setCategory(Categoria c){
        category.setValue(c);
    }

    public void setSelectedActivity(Actividad a){
        selectedActivity.setValue(a);
    }

    public LiveData<Actividad> getSelectedActivity(){
        return selectedActivity;
    }

    public void setCompletedActivs(String c){ completedActivs.setValue(c);}

    public LiveData<String> getCompletedActivs(){ return completedActivs;}

}
