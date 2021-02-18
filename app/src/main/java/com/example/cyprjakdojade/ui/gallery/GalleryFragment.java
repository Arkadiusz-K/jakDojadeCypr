package com.example.cyprjakdojade.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cyprjakdojade.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.okhttp.Route;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.trasa);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        EditText trasaPrzystanekPoczatkowy = root.findViewById(R.id.trasaPoczatkowy);
        EditText trasaPrzystanekKoncowy = root.findViewById(R.id.trasaKoncowy);
        Button button = (Button)root.findViewById(R.id.buttonTrasa);
        String wynik1;
        ArrayList<String> listaPrzystankow = new ArrayList<String>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String przystanekPoczatkowy = trasaPrzystanekPoczatkowy.getText().toString();
                String przystanekKoncowy = trasaPrzystanekKoncowy.getText().toString();
                DatabaseReference ref = database.getReference("trasa").child(przystanekPoczatkowy);
                final DatabaseReference[] ref2 = {database.getReference("trasa").child(przystanekPoczatkowy)};
                ValueEventListener valueEventListener = new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        System.out.println("jestem w onDataCgange: "+ref.getKey());
                        System.out.println("@@@@@@@@@@@@@@ ilosc dzieci: "+snapshot.getChildrenCount());
                        for(DataSnapshot ds : snapshot.getChildren()){
                            //if(ds.getValue().toString().equals(przystanekKoncowy)){
                            if(ds.getValue().toString().equals("4")){
                                System.out.println("odp: poczatkowy: "+przystanekPoczatkowy+" , koncowy: "+przystanekKoncowy);
                            } else{
                                String klucz = ds.getKey();
                                System.out.println("KLUCZ KLUCZ KLUCZ ----> "+klucz);
                                assert klucz != null;
                                ref2[0] = ref2[0].child(klucz);
                                RouteFunctions.search(ref2[0],przystanekKoncowy,snapshot);
                            }
                            System.out.println("1. ds: "+ds);
                            System.out.println("2. ds.getValue: "+ds.getValue());
                            System.out.println("3. koncowy: "+przystanekKoncowy);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                ref.addListenerForSingleValueEvent(valueEventListener);
            }
            });

        return root;
    }
}