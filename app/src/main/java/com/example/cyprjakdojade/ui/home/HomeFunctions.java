package com.example.cyprjakdojade.ui.home;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFunctions {
    public static ArrayList<Integer> najblizszaGodzina(ArrayList<Integer> list, int i){
        if(i<=list.get(0)) {
            System.out.println("i wieksze niz list.get(0)");
            return list;
        }
        ArrayList<Integer> resultList = new ArrayList<>();
        int doWpisania = 3;
        for(int j : list){
            if(i<=j && doWpisania>0){
                resultList.add(j);
                doWpisania--;
            }
        }
        int index = 0;
        if(doWpisania>0){
           for(int j=doWpisania; j>0; j--){
               resultList.add(list.get(index));
               index++;
           }
       }
        return resultList;
    }

    public static String walidacja(String przystanek){
        if(przystanek.equals("")) {
            System.out.println("NIE PODANO PRZYSTANKU POCZĄTKOWEGO");
            return "error";
        }
        return przystanek.toLowerCase();
    }

    public static int czasWalidacja(String czas){
        int czasOdjazdu=999999; // kod bledu
        if(czas.length() != 5 ) {
            System.out.println("Podaj czas w formacie: hh:mm !!!");
        }
        else {
            String[] podzielonyCzas = czas.split(":");
            czas = podzielonyCzas[0] + podzielonyCzas[1];
            try {
                czasOdjazdu = Integer.parseInt(czas);
            } catch (NumberFormatException e) {
                System.out.println("NumberFormatException" + e.getMessage());
            }
        }
        if(czasOdjazdu>2359 || czasOdjazdu<0 || czasOdjazdu%100>59) {
            czasOdjazdu=999999;
            System.out.println("Podaj prawidłowy czas !!!");
        }
        return czasOdjazdu;
    }

    static String edytujDoWyswietlenia(int godz){
        String s = Integer.toString(godz);
        if(godz>999){
            s = Integer.toString(godz).substring(0,2)+":"+Integer.toString(godz).substring(2);
        } else{
            s = Integer.toString(godz).substring(0,1)+":"+Integer.toString(godz).substring(1);
        }
        return s;
    }
}
