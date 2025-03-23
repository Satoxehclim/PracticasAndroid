package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0,btnMas,btnMenos,btnDividir,btnMultiplicar,btnPunto,btnLimpiar,btnResultado,btnBorrar;
    TextView tvResultado,tvOperaciones;
    double resultadoCalculadora;
    boolean OperacionIniciada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn1 = (Button) findViewById(R.id.Id_btn_1);
        btn2 = (Button) findViewById(R.id.Id_btn_2);
        btn3 = (Button) findViewById(R.id.Id_btn_3);
        btn4 = (Button) findViewById(R.id.Id_btn_4);
        btn5 = (Button) findViewById(R.id.Id_btn_5);
        btn6 = (Button) findViewById(R.id.Id_btn_6);
        btn7 = (Button) findViewById(R.id.Id_btn_7);
        btn8 = (Button) findViewById(R.id.Id_btn_8);
        btn9 = (Button) findViewById(R.id.Id_btn_9);
        btn0 = (Button) findViewById(R.id.Id_btn_0);
        btnMas = (Button) findViewById(R.id.Id_btn_mas);
        btnMenos = (Button) findViewById(R.id.Id_btn_menos);
        btnDividir = (Button) findViewById(R.id.Id_btn_dividir);
        btnMultiplicar = (Button) findViewById(R.id.Id_btn_multiplicar);
        btnPunto = (Button) findViewById(R.id.Id_btn_Punto);
        btnLimpiar = (Button) findViewById(R.id.Id_btn_limpiar);
        btnResultado = (Button) findViewById(R.id.Id_btn_resolver);
        btnBorrar = (Button) findViewById(R.id.Id_btn_borrar);
        tvResultado =(TextView) findViewById(R.id.Id_tv_resultado);
        tvOperaciones =(TextView) findViewById(R.id.Id_tv_operaciones);
        tvOperaciones.append("\n");
        resultadoCalculadora=0.0;
        OperacionIniciada=false;
        //Funcion
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.append("0");
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.append("1");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.append("2");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.append("3");
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.append("4");
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.append("5");
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.append("6");
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.append("7");
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.append("8");
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.append("9");
            }
        });
        btnPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numeroActual = tvResultado.getText().toString().trim();
                if (numeroActual.isEmpty()){
                    tvResultado.append("0.");
                }else{
                    tvResultado.append(".");
                }
            }
        });
        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numeroActual = tvResultado.getText().toString().trim();
                if (numeroActual.isEmpty()){
                    tvOperaciones.append("0+");
                }else {
                    tvOperaciones.append(numeroActual+"+");
                    //resultadoCalculadora += Double.parseDouble(numeroActual);
                }
                tvResultado.setText("");
            }
        });
        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numeroActual = tvResultado.getText().toString().trim();
                if (numeroActual.isEmpty()){
                    tvOperaciones.append("0-");
                }else {
                    tvOperaciones.append(numeroActual+"-");
                    //resultadoCalculadora -= Double.parseDouble(numeroActual);
                }
                tvResultado.setText("");
            }
        });
        btnMultiplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numeroActual = tvResultado.getText().toString().trim();
                if (numeroActual.isEmpty()){
                    tvOperaciones.append("0*");
                }else {
                    tvOperaciones.append(numeroActual+"*");
                    //resultadoCalculadora *= Double.parseDouble(numeroActual);
                }
                tvResultado.setText("");
            }
        });
        btnMultiplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numeroActual = tvResultado.getText().toString().trim();
                if (numeroActual.isEmpty()){
                    tvOperaciones.append("0*");
                }else {
                    tvOperaciones.append(numeroActual+"*");
                   //resultadoCalculadora *= Double.parseDouble(numeroActual);
                }
                tvResultado.setText("");
            }
        });
        btnDividir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numeroActual = tvResultado.getText().toString().trim();
                if (numeroActual.isEmpty()){
                    tvOperaciones.append("0/");
                }else {
                    tvOperaciones.append(numeroActual+"/");
                    //resultadoCalculadora /= Double.parseDouble(numeroActual);
                }
                tvResultado.setText("");
            }
        });
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numeroActual = tvResultado.getText().toString().trim();
                if (!numeroActual.isEmpty()){
                   numeroActual = numeroActual.substring(0,numeroActual.length()-1);
                   tvResultado.setText(numeroActual);
                }
            }
        });
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultadoCalculadora=0.0;
                tvResultado.setText("");
                tvOperaciones.setText("Las operaciones realizadas:\n");
            }
        });
    }
}