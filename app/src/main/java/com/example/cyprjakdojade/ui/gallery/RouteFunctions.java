package com.example.cyprjakdojade.ui.gallery;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.squareup.okhttp.Route;

public class RouteFunctions {
    static void search(String szukany, DataSnapshot snapshot) {
        System.out.println("----------- poczatek search ----------");
        System.out.println("snaphot -> " + snapshot.getValue());
        for (DataSnapshot ds : snapshot.getChildren()) {
            System.out.println("snapshot: " + ds);
            if (ds.getValue().toString().equals(szukany) || ds.getKey().equals(szukany)) {
                System.out.println("$$$$$$$$$$$$$$$$$$$$$ odp koncowy: " + szukany);
            } else {
               search(szukany,ds);
            }
        }
        System.out.println("-----------koniec search -------------");
    }

}
