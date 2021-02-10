package com.example.cyprjakdojade.ui.home;

import java.util.ArrayList;

public class HomeFunctions {
    public static ArrayList<Integer> najblizszaGodzina(ArrayList<Integer> list, int i){
        System.out.println("GODZINA I OUTPUT POWINIEN BYC OD NIEJ WIEKSZY: "+i);
        System.out.println("PIERWSZA WARTOSC Z LISTY: "+list.get(0));
        if(i<=list.get(0)) {
            System.out.println("i wieksze niz list.get(0)");
            return list;
        }
        ArrayList<Integer> resultList = new ArrayList<>();
        int doWpisania = 3;
        for(int j : list){
            if(i<=j && doWpisania>0){
                System.out.println("i: "+i+" ,na liście:"+j+"j dodane do listy");
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

}
