package com.example.rickandmorty;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    // store the data that needs to be shared between fragments
    // getter and setter
    private MutableLiveData<Character> information = new MutableLiveData<>();

    public void setInformation(Character userInformation) {
        information.setValue(userInformation);
    }

    public MutableLiveData<Character> getInformation() {
        return information;
    }

}
