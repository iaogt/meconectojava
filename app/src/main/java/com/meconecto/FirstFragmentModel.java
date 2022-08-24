package com.meconecto;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meconecto.data.Actividad;
import com.meconecto.data.Categoria;

public class FirstFragmentModel extends ViewModel {
    private final MutableLiveData<Categoria> category;
    private final MutableLiveData<Actividad> selectedActivity;

    public FirstFragmentModel(){
        category = new MutableLiveData<>();
        selectedActivity = new MutableLiveData<>();
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
}
