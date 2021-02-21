package com.example.cyprjakdojade.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cyprjakdojade.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        Button button1 = (Button)root.findViewById(R.id.buttonCalaTrasa1);
        Button button2 = (Button)root.findViewById(R.id.buttonCalaTrasa2);
        Button button3 = (Button)root.findViewById(R.id.buttonCalaTrasa3);
        button1.setText("26");
        button2.setText("42");
        button3.setText("56");
        ArrayList<String> trasa = new ArrayList<>();
        TextView wyniki = root.findViewById(R.id.wynikiCalegoRozkladu);
        TextView tv1 = root.findViewById(R.id.textViewCalaTrasa1);
        TextView tv2 = root.findViewById(R.id.textViewCalaTrasa2);
        TextView tv3 = root.findViewById(R.id.textViewCalaTrasa3);
        TextView tv4 = root.findViewById(R.id.textViewCalaTrasa4);
        TextView tv5 = root.findViewById(R.id.textViewCalaTrasa5);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> pomocnicza = Arrays.asList("Nicosia","Agios Georgios Chavouzas","Episkopiana","Pafos");
                trasa.addAll(pomocnicza);
                for(int i = trasa.size();i<7;i++) trasa.add(i,"");
                CalaTrasaFunctions.ustawWyniki(trasa,tv1,tv2,tv3,tv4,tv5,wyniki,26);
                trasa.clear();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> pomocnicza = Arrays.asList("Larnaca","Alampra","Nicosia");
                trasa.addAll(pomocnicza);
                for(int i = trasa.size();i<7;i++) trasa.add(i,"");
                wyniki.setText("Rozkład trasy nr: 42");
                CalaTrasaFunctions.ustawWyniki(trasa,tv1,tv2,tv3,tv4,tv5,wyniki,42);
                trasa.clear();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> pomocnicza = Arrays.asList("Limassol","Episkopiana","Pafos");
                trasa.addAll(pomocnicza);
                for(int i = trasa.size();i<7;i++) trasa.add(i,"");
                wyniki.setText("Rozkład trasy nr: 56");
                CalaTrasaFunctions.ustawWyniki(trasa,tv1,tv2,tv3,tv4,tv5,wyniki,56);
                trasa.clear();
            }
        });



        return root;
    }
}