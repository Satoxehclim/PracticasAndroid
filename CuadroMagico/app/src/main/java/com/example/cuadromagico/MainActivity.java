package com.example.cuadromagico;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText etNumero;
    private TableLayout tbLayout;
    private Button btnGenerar;
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
        tbLayout = (TableLayout) findViewById(R.id.Id_tableLayout);
        etNumero = (EditText) findViewById(R.id.Id_et_numero);
        btnGenerar = (Button) findViewById(R.id.Id_btn_Generar);
        btnGenerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numero = etNumero.getText().toString().trim();
                if (!numero.isEmpty()){
                    if (!esPar(Integer.parseInt(numero))){
                        generarCuadroMagico(Integer.parseInt(numero));
                    }else{
                        Toast.makeText(MainActivity.this,"Se necesita que el numero sea impar",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private boolean esPar(int i) {
        return i % 2==0;
    }

    private void generarCuadroMagico(int n) {
        int[][] matrizDeDatos = new int[n][n];
        int fila = 0;
        int col = n/2;
        int nuevaFila,nuevaCol;
        tbLayout.removeAllViews();
        for (int i = 1; i <= n * n; i++){
            matrizDeDatos[fila][col] = i;
            nuevaFila = fila - 1;
            nuevaCol = col + 1;
            if (nuevaFila < 0){
                nuevaFila = n - 1;
            }
            if (nuevaCol >= n){
                nuevaCol = 0;
            }
            if (matrizDeDatos[nuevaFila][nuevaCol] != 0) {
                nuevaFila = fila + 1;
                nuevaCol = col;
            }
            fila = nuevaFila;
            col = nuevaCol;
        }
        for (int[] filaDeDatos : matrizDeDatos) {
            TableRow tableRow = new TableRow(this);

            for (int valor : filaDeDatos) {
                TextView textView = new TextView(this);
                textView.setText(String.valueOf(valor));
                textView.setPadding(30, 10, 10, 30);
                textView.setBackgroundResource(R.drawable.cell_border);
                tableRow.addView(textView);
            }

            tbLayout.addView(tableRow);
        }

    }
}