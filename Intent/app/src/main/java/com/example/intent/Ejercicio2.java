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
                int width = tvDivisior.getWidth();

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
        double cociente = 0;
        long residuo = 0;
        long digitoActual;
        long cocienteParcial;
        long producto;
        long nuevoResiduo;
        String dividendoStr = String.valueOf(dividendo)+"0";

        for (int i = 0; i < dividendoStr.length(); i++) {
            digitoActual = Long.parseLong(dividendoStr.substring(i, i + 1));
            residuo = residuo * 10 + digitoActual;

            cocienteParcial = residuo / divisor;
            cociente = cociente * 10 + cocienteParcial;

            producto = cocienteParcial * divisor;
            nuevoResiduo = residuo - producto;
            if (cocienteParcial==0){
                residuoStr.append(residuo);
            }else{
                residuoStr.append("\n");
                for (int j=0; j<i; j++){
                    residuoStr.append(" ");
                }
                residuoStr.append(residuo);
            }
            residuo = nuevoResiduo;
        }
        tvResiduo.append(residuoStr);
        tvCociente.append(cociente+"");
    }

}
