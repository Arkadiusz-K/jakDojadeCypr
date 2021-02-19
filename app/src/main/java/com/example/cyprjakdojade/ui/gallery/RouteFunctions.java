package com.example.cyprjakdojade.ui.gallery;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.squareup.okhttp.Route;

import java.util.ArrayList;

public class RouteFunctions {
    static ArrayList<String> przystankiPomocnicza = new ArrayList<String>();
    static ArrayList<String> referencjeDoPoczatkowego = new ArrayList<String>();

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

    static void searchFirst(String szukany, DataSnapshot snapshot,DatabaseReference ref, ArrayList<String> wynik){
        System.out.println("        Search first");
        for (DataSnapshot ds : snapshot.getChildren()) {
            //referencjeDoPoczatkowego.clear();
            System.out.println("snapshot: " + ds);
            boolean czyZnaleziono = false;
            referencjeDoPoczatkowego.add(ds.getKey());
            if (ds.getValue().toString().equals(szukany) || ds.getKey().equals(szukany)) {
                System.out.println("ZNALEZIONO SZUKANY PRZYSTANEK W BAZIE!");
                System.out.println("KKKKKKKKKKKKKKKKKK");
                int j=0;
                for(String s : referencjeDoPoczatkowego){
                    j++;
                    System.out.println("nr ref: "+j+", nazwa: "+s);
                }
                czyZnaleziono = true;
                System.out.println("KKKKKKKKKKKKKKKKKK");
            } else{
                searchFirst(szukany,ds,ref,wynik);
            }
            if(czyZnaleziono) {
                System.out.println("333333333333 TUTAJ USTAWIENIA REFA <33333333333333333");
                System.out.println("Lista na koniec :D");
                for(String s : referencjeDoPoczatkowego){
                    System.out.println("NAZWA: "+s);
                }
                wynik.addAll(referencjeDoPoczatkowego);
            }
            else
                referencjeDoPoczatkowego.clear();
        }
    }
}
