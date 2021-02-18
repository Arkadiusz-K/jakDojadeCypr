package com.example.cyprjakdojade.ui.gallery;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.squareup.okhttp.Route;

public class RouteFunctions {
    static String search(DatabaseReference ref, String szukany, DataSnapshot snapshot) {
        DataSnapshot kopiaSnapshot = snapshot;
        System.out.println("---------------");
        System.out.println(ref.getKey());
        System.out.println("snaphot -> " + snapshot.getValue());

        for (DataSnapshot ds : snapshot.getChildren()) {
            System.out.println("dziecko: " + ds);
            if (ds.getValue().toString().equals(szukany)) {
                System.out.println("$$$$$$$$$$$$$$$$$$$$$ odp koncowy: " + szukany);
                return szukany;
            } else {
                for (DataSnapshot d : ds.getChildren()) {
                    if (d.getValue().toString().equals(szukany)) {
                        System.out.println("$$$$$$$$$$$$$$$$$$$$$ odp koncowy: " + szukany);
                        return szukany;
                    } else{
                        RouteFunctions.searching(d,szukany);
                    }
                }

            }
            System.out.println("||||||||||||||||||||||||||||");
        }
        return "";
    }

    static String searching(DataSnapshot d, String szukany){
        if (d.getValue().toString().equals(szukany)) {
            System.out.println("$$$$$$$$$$$$$$$$$$$$$ odp koncowy: " + szukany);
            return szukany;
        } else{
            RouteFunctions.searching(d,szukany);
        }
        return "";
    }
}
