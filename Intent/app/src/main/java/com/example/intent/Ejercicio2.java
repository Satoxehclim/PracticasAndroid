package com.example.intent;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Space;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Ejercicio2 extends AppCompatActivity {

    TextView tvDivisior,tvDividendo,tvCociente,tvResiduo;
    Space spacioCociente,spacioCociente2;
    View lineaHorizontal;
    Bundle bdl;
    long dividendo,divisor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio2);
        bdl = getIntent().getExtras();
        spacioCociente = (Space) findViewById(R.id.id_space);
        spacioCociente2 = (Space) findViewById(R.id.id_space2);
        lineaHorizontal = (View) findViewById(R.id.id_LineaHorizontal);
        tvDivisior = (TextView) findViewById(R.id.Id_tv_divisior);
        tvDividendo = (TextView) findViewById(R.id.Id_tv_dividendo);
        tvCociente = (TextView) findViewById(R.id.Id_tv_cociente);
        tvResiduo = (TextView) findViewById(R.id.Id_tv_Residuo);
        dividendo = bdl.getLong("DIVIDENDO");
        divisor = bdl.getLong("DIVISOR");
        tvDivisior.append(""+divisor);
        tvDividendo.append(dividendo+".0");
        tvDivisior.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()  {
            @Override
            public void onGlobalLayout(){
                int width = tvDivisior.getWidth()+8;

                ViewGroup.LayoutParams params = spacioCociente.getLayoutParams();
                ViewGroup.LayoutParams params2 = spacioCociente2.getLayoutParams();
                params.width = width;
                params2.width = width;
                spacioCociente.setLayoutParams(params);
                spacioCociente2.setLayoutParams(params2);
                tvDivisior.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        tvDividendo.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()  {
            @Override
            public void onGlobalLayout(){
                int width = tvDividendo.getWidth();

                ViewGroup.LayoutParams params = lineaHorizontal.getLayoutParams();
                params.width = width;
                lineaHorizontal.setLayoutParams(params);
                tvDividendo.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        DivisionLarga(dividendo,divisor);
    }

    private void DivisionLarga(long dividendo, long divisor) {
        StringBuilder residuoStr = new StringBuilder();
        StringBuilder cocienteStr = new StringBuilder();
        double cociente = 0;
        long residuo = 0;
        long digitoActual;
        long cocienteParcial;
        long producto;
        long nuevoResiduo;
        String residuor;
        String dividendoStr = String.valueOf(dividendo)+".0";
        int cont=dividendoStr.length(),aux=0;
        boolean flagpoint=false;
        if (divisor==0){
            tvResiduo.setText("Error: No se puede dividir entre cero.");
            tvCociente.setText("");
            return;
        }
        for (int i = 0; i < cont-1; i++) {
            if (dividendoStr.charAt(0)=='.'){
                dividendoStr = dividendoStr.substring(1);
                cocienteStr.append(".");
                flagpoint=true;
            }
            digitoActual = Character.getNumericValue(dividendoStr.charAt(0));
            dividendoStr = dividendoStr.substring(1);
            residuo = residuo * 10 + digitoActual;

            cocienteParcial = residuo / divisor;
            cociente = cociente * 10 + cocienteParcial;

            producto = cocienteParcial * divisor;
            nuevoResiduo = residuo - producto;

            residuor = String.valueOf(residuo);
            if (cocienteParcial!=0){
                for (int j=0; j<=i-residuor.length(); j++){
                    residuoStr.append(" ");
                }
                cocienteStr.append(cocienteParcial);
                if (flagpoint){
                    double auxresiduo = residuo/10.0;
                    residuoStr.append(auxresiduo);
                }else{
                    residuoStr.append(residuo);
                }

                residuoStr.append("\n");
            }
            residuo = nuevoResiduo;

        }
        dividendoStr = String.valueOf(dividendo)+".0";
        residuor = String.valueOf(residuo);
        for (int j=0; j<(dividendoStr.length()-residuor.length())-1; j++){
            residuoStr.append(" ");
        }
        double res = residuo/10.0;
        residuoStr.append(res);
        tvResiduo.append(residuoStr);
        tvCociente.append(cocienteStr);
    }

}
