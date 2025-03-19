package com.quetzalcodeescom.algoritmokaprekar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tvKaprekar,tvDesarrollo;
    Button btnGenerar;
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
        tvKaprekar = (TextView) findViewById(R.id.Id_tv_kaprekar);
        tvDesarrollo = (TextView) findViewById(R.id.Id_tv_desarrollo);
        btnGenerar = (Button) findViewById(R.id.Id_btn_generar);
        Random numberRandom = new Random();
        int numeroKap = numberRandom.nextInt(9000)+1000;
        tvKaprekar.setText("Numero analizado: "+numeroKap);
        algoritmoKaprekar(numeroKap);
        btnGenerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random numberRandom = new Random();
                int numeroKap = numberRandom.nextInt(9000)+1000;
                tvKaprekar.setText("Numero analizado: "+numeroKap);
                tvDesarrollo.setText("");
                algoritmoKaprekar(numeroKap);
            }
        });
    }

    private void algoritmoKaprekar(int numeroKap1){
        int[] digitos = new int[4];
        for (int i=0; i<4; i++){
            digitos[i] = numeroKap1 % 10;
            numeroKap1/=10;
        }
        Arrays.sort(digitos);
        int numeroAscendente = 0;
        for (int i = 0; i < 4; i++) {
            numeroAscendente = numeroAscendente * 10 + digitos[i];
        }
        int numeroDescendente = 0;
        for (int i = 3; i >= 0; i--) {
            numeroDescendente = numeroDescendente * 10 + digitos[i];
        }
        int nuevoKaprekar = numeroDescendente - numeroAscendente;
        tvDesarrollo.append(numeroDescendente+" - "+numeroAscendente+" = "+nuevoKaprekar+"\n");
        if (nuevoKaprekar == 6174){
            numeroKap1 = nuevoKaprekar;
            for (int i=0; i<4; i++){
                digitos[i] = numeroKap1 % 10;
                numeroKap1/=10;
            }
            Arrays.sort(digitos);
            numeroAscendente = 0;
            for (int i = 0; i < 4; i++) {
                numeroAscendente = numeroAscendente * 10 + digitos[i];
            }
            numeroDescendente = 0;
            for (int i = 3; i >= 0; i--) {
                numeroDescendente = numeroDescendente * 10 + digitos[i];
            }
            nuevoKaprekar = numeroDescendente - numeroAscendente;
            tvDesarrollo.append(numeroDescendente+" - "+numeroAscendente+" = "+nuevoKaprekar+"\n");
            return;
        }else{
            algoritmoKaprekar(nuevoKaprekar);
        }
    }
}