package com.example.cyprjakdojade.ui.gallery;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.squareup.okhttp.Route;

import java.util.ArrayList;

public class RouteFunctions {
    // przystankiPomocnicza - wczytuję przystanki po drodze, bo mogą być potrzebne, jeśli to dobra trasa
    static ArrayList<String> przystankiPomocnicza = new ArrayList<String>();
    // gdy przystanek początkowy nie jest pierwszy, trzeba ominąc od pierwszego do wpisanego przez uzytkownika
    static ArrayList<String> ominietePrzystanki = new ArrayList<String>();
    // czyZnaleziono - czy znaleziono w bazie pierwszy pierwszy przystanek
    static boolean czyZnaleziono = false;
    static boolean czyZnalezionoSearch = false;

    static void search(String szukany, DataSnapshot snapshot, ArrayList<String> przystanki) {
        System.out.println("----------- poczatek search ----------");
        System.out.println("snaphot -> " + snapshot.getValue()); // logi dla informacji

        for (DataSnapshot ds : snapshot.getChildren()) {
            czyZnalezionoSearch = false;
            System.out.println("snapshot: " + ds); // logi dla informacji
            przystankiPomocnicza.add(ds.getKey()); // dodawaj kolejne przystanki na trasie
            if (ds.getValue().toString().equals(szukany) || ds.getKey().equals(szukany)) {
                // jesli znaleziono to dodaj dotychczasowa trase do wyniku
                System.out.println("$$$$$$$$$$$$$$$$$$$$$ odp koncowy: " + szukany);
                przystanki.addAll(przystankiPomocnicza);
                czyZnalezionoSearch = true;
            } else {
                // przeszukuj dalej rekurencyjnie
               search(szukany,ds,przystanki);
            }
            if(!czyZnalezionoSearch) przystankiPomocnicza.clear();
            else break;

        }
        System.out.println("-----------koniec search -------------");
    }

    static boolean searchFirst(String szukany, DataSnapshot snapshot,DatabaseReference ref, ArrayList<String> wynik){
        System.out.println(" ------------ poczatek Search first ----------");
        for (DataSnapshot ds : snapshot.getChildren()) {
            System.out.println("snapshot: " + ds);
            ominietePrzystanki.add(ds.getKey());
            if (ds.getValue().toString().equals(szukany) || ds.getKey().equals(szukany)) {
                System.out.println("ZNALEZIONO SZUKANY PRZYSTANEK W BAZIE!");
                /*int j=0;
                for(String s : ominietePrzystanki){
                    j++;
                    System.out.println("nr ref: "+j+", nazwa: "+s);
                }*/
                czyZnaleziono = true;
            } else{
                searchFirst(szukany,ds,ref,wynik);
            }
            if(czyZnaleziono) {
                /*for(String s : ominietePrzystanki){
                    System.out.println("NAZWA: "+s);
                }*/
                wynik.addAll(ominietePrzystanki);
            }
            else
                ominietePrzystanki.clear();
        }
        System.out.println(" ------------ koniec Search first ----------");
        return czyZnaleziono;
    }

    static void clearArrays(){
        // czyszczenie ArrayList, żeby nie przechowywać starych wyników
        przystankiPomocnicza.clear();
        ominietePrzystanki.clear();
    }

    public static String walidacja(String przystanek){
        if(przystanek.equals("")) {
            System.out.println("NIE PODANO PRZYSTANKU POCZĄTKOWEGO");
            return "error";
        }
        return przystanek.toLowerCase();
    }
}
