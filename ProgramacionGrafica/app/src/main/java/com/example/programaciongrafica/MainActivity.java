package com.example.programaciongrafica;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button IdBtnBuscar, IdBtnConectar, IdBtnLed1on, IdBtnLed1off, IdBtnLed2on, IdBtnLed2off, IdBtnDesconectar;
    Spinner IdDisEncontrados;
    private String wemosIP = ""; // IP del Wemos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IdBtnBuscar = findViewById(R.id.IdBtnBuscar);
        IdBtnConectar = findViewById(R.id.IdBtnConectar);
        IdBtnLed1on = findViewById(R.id.IdBtnLed1On);
        IdBtnLed1off = findViewById(R.id.IdBtnLed1Off);
        IdBtnLed2on = findViewById(R.id.IdBtnLed2On);
        IdBtnLed2off = findViewById(R.id.IdBtnLed2Off);
        IdBtnDesconectar = findViewById(R.id.IdBtnDesconectar);
        IdDisEncontrados = findViewById(R.id.IdDisEncontrados);

        IdBtnBuscar.setOnClickListener(v -> buscarDispositivo());
        IdBtnConectar.setOnClickListener(v -> conectar());
        IdBtnLed1on.setOnClickListener(v -> enviarComando("on1"));
        IdBtnLed1off.setOnClickListener(v -> enviarComando("off1"));
        IdBtnLed2on.setOnClickListener(v -> enviarComando("on2"));
        IdBtnLed2off.setOnClickListener(v -> enviarComando("off2"));
        IdBtnDesconectar.setOnClickListener(v -> desconectar());
    }

    void buscarDispositivo() {
        // Lista para almacenar las IPs encontradas
        final ArrayList<String> dispositivosEncontrados = new ArrayList<>();

        // Cantidad de hilos
        int numeroDeHilos = 50;

        // Rango de IPs a buscar (por ejemplo, de 2 a 254)
        int rangoInicio = 2;
        int rangoFin = 255;

        // Crear un bloque para manejar los hilos
        Thread[] hilos = new Thread[numeroDeHilos];

        // Dividir el trabajo entre los hilos
        for (int i = 0; i < numeroDeHilos; i++) {
            final int hiloIndex = i;
            hilos[i] = new Thread(() -> {
                // Dividir el rango de IPs
                int inicio = rangoInicio + hiloIndex * (rangoFin - rangoInicio) / numeroDeHilos;
                int fin = rangoInicio + (hiloIndex + 1) * (rangoFin - rangoInicio) / numeroDeHilos;

                for (int j = inicio; j < fin; j++) {
                    String testIP = "192.168.100." + j;
                    try {
                        // Verificar si el puerto 80 está abierto (usamos socket)
                        Socket socket = new Socket();
                        socket.connect(new InetSocketAddress(testIP, 80), 1000); // Intentamos conectar al puerto 80
                        socket.close();
                        synchronized (dispositivosEncontrados) {
                            dispositivosEncontrados.add(testIP); // Agrega la IP a la lista
                        }
                    } catch (Exception ignored) {}
                }

                // Después de que todos los hilos terminen, actualizar la UI
                if (hiloIndex == numeroDeHilos - 1) {
                    runOnUiThread(() -> {
                        if (!dispositivosEncontrados.isEmpty()) {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, dispositivosEncontrados);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            IdDisEncontrados.setAdapter(adapter);
                            Toast.makeText(getApplicationContext(), "Dispositivos encontrados", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "No se encontraron dispositivos", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            hilos[i].start(); // Iniciar el hilo
        }
    }




    private void conectar() {
        try{
            String selectedIP = IdDisEncontrados.getSelectedItem().toString(); // Obtener la IP seleccionada del Spinner
            wemosIP = selectedIP; // Asigna la IP seleccionada al valor de wemosIP
            Toast.makeText(this, "Conectado a " + wemosIP, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "Primero busca el dispositivo", Toast.LENGTH_SHORT).show();
        }
    }


    private void enviarComando(String comando) {
        if (wemosIP.isEmpty()) {
            Toast.makeText(this, "No hay conexión", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://" + wemosIP + "/" + comando;

        new Thread(() -> {
            try {
                // Crear la conexión HTTP
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(1000); // Tiempo máximo para conectar

                int responseCode = con.getResponseCode(); // Obtener el código de respuesta

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Si la conexión fue exitosa, puedes leer la respuesta si lo deseas
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Comando enviado", Toast.LENGTH_SHORT).show());
                } else {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error al enviar comando", Toast.LENGTH_SHORT).show());
                }

            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error en la conexión", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }


    private void desconectar() {
        wemosIP = "";
        Toast.makeText(this, "Desconectado", Toast.LENGTH_SHORT).show();
    }
}
