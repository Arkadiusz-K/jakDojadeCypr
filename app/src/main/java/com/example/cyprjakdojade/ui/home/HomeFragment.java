package com.example.cyprjakdojade.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.widget.Button;

import com.example.cyprjakdojade.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    private static final String TAG = "blad";
    private HomeViewModel homeViewModel;
    EditText tv;
    EditText tv2;
    String name;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("nicosia").child("Dionysios Solomos Square").child("paphos").child("ponToPia");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=1;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String godzOdjazdu = (String)ds.getValue();
                    System.out.println("nr: "+i+" ,godzina odj: "+godzOdjazdu);
                    i++;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("BLAAAAAD");
            }
        };
        ref.addListenerForSingleValueEvent(valueEventListener);

        tv = root.findViewById(R.id.EditTextSearch);
        tv2 = root.findViewById(R.id.EditTextSearch2);
        Button button = (Button)root.findViewById(R.id.buttonSearch);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                System.out.println("----------------NAPIS NAPIS-----------------");
                String przystanekPoczatkowy = tv.getText().toString();
                System.out.println("poczatkowy: "+przystanekPoczatkowy);
                String przystanekKoncowy = tv2.getText().toString();
                System.out.println("koncowy: "+przystanekKoncowy);
            }
        });
        return root;
    }
}