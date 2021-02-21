package com.example.cyprjakdojade.ui.slideshow;

import android.widget.TextView;

import java.util.ArrayList;

public class CalaTrasaFunctions {
    static void ustawWyniki(ArrayList<String> trasa,TextView tv1, TextView tv2, TextView tv3, TextView tv4, TextView tv5, TextView wynik, int nr){
        wynik.setText("Rokzlad trasy nr: "+nr);
        tv1.setText(trasa.get(0));
        tv2.setText(trasa.get(1));
        tv3.setText(trasa.get(2));
        tv4.setText(trasa.get(3));
        tv5.setText(trasa.get(4));

    }
}
