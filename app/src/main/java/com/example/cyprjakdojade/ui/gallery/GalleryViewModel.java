package com.example.cyprjakdojade.ui.gallery;

import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Tutaj będą rozkłady");
    }

    public LiveData<String> getText() {
        return mText;
    }
}