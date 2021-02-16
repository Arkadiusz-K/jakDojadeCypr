package com.example.cyprjakdojade.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class HomeFragment extends Fragment {

    private static final String TAG = "blad";
    private HomeViewModel homeViewModel;
    EditText tv;
    EditText tv2;
    EditText time;
    ArrayList<Integer> timetable = new ArrayList<>();
    int check = 0;
    int godzinaInt, czasOdjazdu;

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

        tv = root.findViewById(R.id.EditTextSearch);
        tv2 = root.findViewById(R.id.EditTextSearch2);
        time = root.findViewById(R.id.editTextTime);
        Button button = (Button)root.findViewById(R.id.buttonSearch);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // wczytanie danych
                String przystanekPoczatkowy = tv.getText().toString();
                String przystanekKoncowy = tv2.getText().toString();
                String czas = time.getText().toString();


                // walidacja danych
                przystanekPoczatkowy = HomeFunctions.walidacja(przystanekPoczatkowy);
                przystanekKoncowy = HomeFunctions.walidacja(przystanekKoncowy);
                czasOdjazdu = HomeFunctions.czasWalidacja(czas);


                // tutaj bedzie szukanie najblizszego polaczenia i kolejnych po nim
                DatabaseReference ref = database.getReference(przystanekPoczatkowy).child("Dionysios Solomos Square").child(przystanekKoncowy).child("ponToPia");
                final String[] czyIstniejeWBazie = {""};
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot != null && dataSnapshot.getChildren()!=null && dataSnapshot.getChildren().iterator().hasNext()){
                            int i=1;
                            int godzinaInt=0;
                            for(DataSnapshot ds : dataSnapshot.getChildren()){
                                String godzOdjazdu = (String)ds.getValue();
                                String[] podzielonaGodzina = godzOdjazdu.split(":");
                                String godzina = podzielonaGodzina[0]+podzielonaGodzina[1];
                                try {
                                    godzinaInt = Integer.parseInt(godzina);
                                } catch (NumberFormatException e){
                                    System.out.println("NumberFormatException"+e.getMessage());
                                }
                                timetable.add(godzinaInt);
                                i++;
                            }
                            check=1;
                        } else{
                            // jesli jest tutaj, to oznacza, ze pobieranego nie ma w bazie danych
                            System.out.println("Jeden z przystankow nie wystepuje w bazie");
                            czyIstniejeWBazie[0] = "error";
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        System.out.println("error");
                    }
                };
                if(!przystanekPoczatkowy.equals("error") && !przystanekKoncowy.equals("error") && czasOdjazdu != 999999 && !czyIstniejeWBazie[0].equals("error"))
                {
                    ref.addListenerForSingleValueEvent(valueEventListener);
                    if(check==0) System.out.println("NIE ZDAZYLO SIE DODAC");
                    else {
                        int i = 1;
                        if (timetable.size() == 0)
                            System.out.println("Pusta lista????????????????????????");
                        for (int time : timetable) {
                            System.out.println("nr: " + i + " ,godzina odj: " + time);
                            i++;
                        }
                        i = 0;
                        System.out.println("GODZINA INT: " + czasOdjazdu);
                        timetable = HomeFunctions.najblizszaGodzina(timetable, czasOdjazdu);
                        System.out.println("---------------------------------------------------");
                        for (int time : timetable) {
                            System.out.println("nr: " + i + " ,po powrocie z funkcji: " + time);
                            i++;
                        }
                        System.out.println("Przystanek poczatkowy: " + przystanekPoczatkowy);
                        System.out.println("Przystanek koncowy: " + przystanekKoncowy);
                        System.out.println("Wpisany czas to: " + czas);
                    }
                }
            }
        });
        return root;
    }
}