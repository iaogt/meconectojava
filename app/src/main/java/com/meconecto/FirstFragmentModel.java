package com.meconecto;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meconecto.data.Categoria;

public class FirstFragmentModel extends ViewModel {
    private final MutableLiveData<Categoria> category;

    public FirstFragmentModel(){
        category = new MutableLiveData<>();
    }

    public LiveData<Categoria> getCategory(){
        return category;
    }

    public void setCategory(Categoria c){
        category.setValue(c);
    }
}
