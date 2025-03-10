package com.example.intent;

import android.os.Bundle;
import android.app.Activity;
import android.widget.*;

public class Ejercicio1 extends Activity{
    TextView textViewX1,textViewX2;
    Bundle bdl;
    double x1,x2,Ca,Cb,Cc;
    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_ejercicio1);
        bdl = getIntent().getExtras();
        textViewX1 = (TextView) findViewById(R.id.Id_TextView_X1);
        textViewX2 = (TextView) findViewById(R.id.Id_TextView_X2);
        Ca = bdl.getDouble("COEFICIENTE A");
        Cb = bdl.getDouble("COEFICIENTE B");
        Cc = bdl.getDouble("COEFICIENTE C");
        x1 = chicharronera(Ca,Cb,Cc,1);
        x2 = chicharronera(Ca,Cb,Cc,2);
        if(x1 == 0 && x2 == 0){
            textViewX1.append("La solucion no es real");
            textViewX2.append("");
        }else{
            textViewX1.append("La solucion x1 es: " + x1);
            textViewX2.append("La solucion x2 es: " + x2);
        }
    }

    private double chicharronera (double a,double b,double c,int x){
        double discriminante = Math.pow(b,2) - (4*a*c);
        if (discriminante > 0){
            if (x==1){
                return (-b + Math.sqrt(discriminante)) / (2*a);
            }if(x==2){
                return (-b - Math.sqrt(discriminante)) / (2*a);
            }else{
                return 0;
            }
        }if(discriminante == 0) {
            return -b / (2*a);
        } else{
            return 0;
        }
    }
}