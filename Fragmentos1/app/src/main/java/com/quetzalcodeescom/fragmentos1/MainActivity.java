package com.quetzalcodeescom.fragmentos1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Cargar el primer fragmento
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Agregar el fragmento de mensaje
        ft.add(R.id.message_container, new MessageFragment());
        // Agregar el fragmento de botón
        ft.add(R.id.button_container, new ButtonFragment());
        // Confirmar la transacción
        ft.commit();
    }
}
