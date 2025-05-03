package com.quetzalcodeescom.spinnerejercicio1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etNombre;
    private Button btnAgregar;
    private TextView tvContador;
    private Spinner spinnerNombres;

    private final ArrayList<String> listaNombres = new ArrayList<>();
    private int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        etNombre = findViewById(R.id.etNombre);
        btnAgregar = findViewById(R.id.btnAgregar);
        tvContador = findViewById(R.id.tvContador);
        spinnerNombres = findViewById(R.id.spinnerNombres);

        spinnerNombres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSeleccionado = (String) parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this,
                        "Seleccionaste: " + itemSeleccionado,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarNombre();
            }
        });
    }

    private void agregarNombre() {
        String nombre = etNombre.getText().toString().trim();

        if (nombre.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese un nombre", Toast.LENGTH_SHORT).show();
            return;
        }

        if (contador < 10) {
            listaNombres.add(nombre);
            contador++;
            tvContador.setText("Nombres ingresados: " + contador + "/10");
            etNombre.setText("");
            mostrarSpinner();
            if (contador == 10) {
                btnAgregar.setEnabled(false);
                etNombre.setEnabled(false);
                Toast.makeText(this, "¡10 nombres ingresados!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void mostrarSpinner() {
        if (!listaNombres.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    listaNombres
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerNombres.setAdapter(adapter);
            spinnerNombres.setVisibility(View.VISIBLE);
        }
    }
}