package com.example.intent;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.widget.*;

public class Ejercicio1 extends Activity{
    TextView textViewX1,textViewX2,tvTituloEjercicio1;
    Bundle bdl;
    double Ca,Cb,Cc;
    String x1,x2;
    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_ejercicio1);
        bdl = getIntent().getExtras();
        textViewX1 = (TextView) findViewById(R.id.Id_TextView_X1);
        textViewX2 = (TextView) findViewById(R.id.Id_TextView_X2);
        tvTituloEjercicio1 = (TextView) findViewById(R.id.Id_tv_Ejercicico1TituloActivity);
        tvTituloEjercicio1.setText(Html.fromHtml("La ecuacion resuelta es:\n"
                +bdl.getDouble("COEFICIENTE A")+"x<sup><small>2</small></sup>+"
                +bdl.getDouble("COEFICIENTE B")+"x+"
                +bdl.getDouble("COEFICIENTE C")+"=0"));
        Ca = bdl.getDouble("COEFICIENTE A");
        Cb = bdl.getDouble("COEFICIENTE B");
        Cc = bdl.getDouble("COEFICIENTE C");
        x1 = chicharronera(Ca,Cb,Cc,1);
        x2 = chicharronera(Ca,Cb,Cc,2);
        textViewX1.append("La solucion x1 es: " + x1);
        textViewX2.append("La solucion x2 es: " + x2);
    }

    private String chicharronera (double a,double b,double c,int x){
        double discriminante = Math.pow(b,2) - (4*a*c);
        double result=0.0;
        if (discriminante > 0){
            if (x==1){
                result = ((-b + Math.sqrt(discriminante)) / (2*a));
                return ""+result;
            }if(x==2){
                result = (-b - Math.sqrt(discriminante)) / (2*a);
                return ""+result;
            }
        }if(discriminante == 0) {
            result = -b / (2*a);
            return ""+result;
        }if (discriminante < 0 ){
            discriminante *= -1;
            double imaginario = Math.sqrt(discriminante);
            if (x==1){
                result = -b / (2*a);
                imaginario /= (2*a);
                String aux=""+result;
                return aux+"+"+imaginario+"i";
            }if (x==2){
                result = -b / (2*a);
                imaginario /= (2*a);
                String aux=""+result;
                return aux+"-"+imaginario+"i";
            }
        }
        return "No se puede resolver";
    }
}