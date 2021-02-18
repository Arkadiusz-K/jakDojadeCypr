package com.example.cyprjakdojade.ui.gallery;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.squareup.okhttp.Route;

import java.util.ArrayList;

public class RouteFunctions {
    static ArrayList<String> przystankiPomocnicza = new ArrayList<String>();
    static void search(String szukany, DataSnapshot snapshot, ArrayList<String> przystanki) {
        System.out.println("----------- poczatek search ----------");
        System.out.println("snaphot -> " + snapshot.getValue());
        for (DataSnapshot ds : snapshot.getChildren()) {
            System.out.println("snapshot: " + ds);
            przystankiPomocnicza.add(ds.getKey());
            if (ds.getValue().toString().equals(szukany) || ds.getKey().equals(szukany)) {
                System.out.println("$$$$$$$$$$$$$$$$$$$$$ odp koncowy: " + szukany);
                przystanki.addAll(przystankiPomocnicza);
            } else {
               search(szukany,ds,przystanki);
            }
        }
        System.out.println("-----------koniec search -------------");
    }
}
