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
        // do wyswietlania w aplikacji
        String wynik1;
        ArrayList<String> listaPrzystankow = new ArrayList<String>(); // lista przystankow na trasie
        ArrayList<String> ominietePrzystanki = new ArrayList<String>(); // nie zawsze uzytkownik wyszukuje od pierwszego przystanku, wiec czesc trzeba pominac
        final FirebaseDatabase database = FirebaseDatabase.getInstance(); // do pobierania z bazy danych Firebase
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String przystanekPoczatkowy = trasaPrzystanekPoczatkowy.getText().toString();
                String przystanekKoncowy = trasaPrzystanekKoncowy.getText().toString();
                // tutaj walidacja przystankow
                DatabaseReference ref = database.getReference("trasa");

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listaPrzystankow.clear(); // czyszczenie starego wyniku po nowym kliknieciu
                        ominietePrzystanki.clear(); // czyszczenie starego wyniku po nowym kliknieciu
                        RouteFunctions.clearArrays(); // czyszczenie starych wynikow w RouteFunctions
                        if (snapshot.child(przystanekPoczatkowy).exists() ) { // sprawdzamy czy przystanek poczatkowy jest jako pierwszy na trasie
                            System.out.println("exist: "+snapshot.child(przystanekPoczatkowy));
                            RouteFunctions.search(przystanekKoncowy, snapshot, listaPrzystankow); //znajdz trase dla przystanku koncowego
                            int i = 0;
                            System.out.println("-------TRASA---------");
                            for (String s : listaPrzystankow) {
                                System.out.println("nr na trasie: " + i + ", nazwa: " + s);
                                i++;
                            }
                        } else {
                            // jesli przystanku początkowego nie ma na poczatku trasy
                            boolean czyZnaleziono = RouteFunctions.searchFirst(przystanekPoczatkowy,snapshot,ref,ominietePrzystanki);
                            if(czyZnaleziono) {
                                RouteFunctions.search(przystanekKoncowy, snapshot, listaPrzystankow);
                                listaPrzystankow.removeAll(ominietePrzystanki);

                                if (listaPrzystankow.size() >= 1) {
                                    listaPrzystankow.add(0, przystanekPoczatkowy); // removeAll usunelo rowniez przystanek poczatkowy
                                    // trzeba go dodac tylko jesli w ArrayList sa inne przystanki (gdyz to swiadczy o poprawnosci trasy)
                                    for (String s : listaPrzystankow) {
                                        System.out.println("Wynik: " + s);
                                    }
                                    RouteFunctions.clearArrays();
                                }
                            } else{
                                System.out.println("error, this bus stop don't exist");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("warning, onCancelled");
                    }
                };
                ref.addListenerForSingleValueEvent(valueEventListener);
                TextView wynik1 = root.findViewById(R.id.trasaWynik1);
                wynik1.setText("Tutaj bedzie pierwszy wynik");
            }
            });

        return root;
    }
}