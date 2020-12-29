package com.example.cyprjakdojade;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OdjazdModel {
    public String odjazdId;
    public String przystanekPoczatkowy;
    public String przystanekKoncowy;
    public String godzinaOdjazdu;
}
