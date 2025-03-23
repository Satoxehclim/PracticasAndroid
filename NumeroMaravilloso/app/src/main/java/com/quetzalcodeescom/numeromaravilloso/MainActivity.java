package com.quetzalcodeescom.numeromaravilloso;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView tvDesarrollo;
    EditText etNumero;
    Button btnAnalizar;
    String actual;
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
        tvDesarrollo = (TextView) findViewById(R.id.Id_tv_Desarrollo);
        etNumero = (EditText) findViewById(R.id.Id_et_Numero);
        btnAnalizar = (Button) findViewById(R.id.Id_btn_analizar);
        btnAnalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numeroMaravilloso = etNumero.getText().toString().trim();
                if (!numeroMaravilloso.isEmpty()){
                    tvDesarrollo.setText("Desarrollo:\n"+numeroMaravilloso+"\n");
                    long numeroMaravillosoLong =  Long.parseLong(numeroMaravilloso);
                    analissiNumeroMaravilloso(numeroMaravillosoLong);
                    if (esPrimo(numeroMaravillosoLong)){
                        tvDesarrollo.append("El numero es primo\n");
                    }else{
                        tvDesarrollo.append("El numero no es primo\n");
                    }
                    if (esFubonacci(numeroMaravillosoLong)){
                        tvDesarrollo.append("El numero es Fubonacci\n");
                    }else{
                        tvDesarrollo.append("El numero no es Fubonacci\n");
                    }
                }
            }
        });
    }

    private void analissiNumeroMaravilloso(long numero) {
        if (numero != 1){
            if (esPar(numero)){
                numero /= 2;
            }else {
                numero = (numero*3)+1;
            }
            actual = String.valueOf(numero)+"\n";
            tvDesarrollo.append(actual);
            analissiNumeroMaravilloso(numero);
        }else{
            tvDesarrollo.append("El numero es maravilloso\n");
        }
    }

    private boolean esPar(long numero) {
        long aux = numero % 2;
        return aux == 0;
    }
    private boolean esPrimo(long numero){
        if (numero <= 1) {
            return false;
        }

        for (long i = 2; i * i <= numero; i++) {
            if (numero % i == 0) {
                return false;
            }
        }
        return true;
    }
    private boolean esFubonacci(long numero){
        return esCuadradoPerfecto(5 * numero * numero + 4) || esCuadradoPerfecto(5 * numero * numero - 4);
    }
    private boolean esCuadradoPerfecto(long x) {
        long raiz = (long) Math.sqrt(x);
        return raiz * raiz == x;
    }
}