package com.quetzalcodeescom.tacometro;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private WebSocket webSocket;
    private OkHttpClient client;
    private TextView estadoMotorText, frecuenciaText, rpmText;
    private Button btnConectar, btnEncender, btnApagar;
    private EditText ipEditText;
    private boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar cliente OkHttp con timeout
        client = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)
                .connectTimeout(3, TimeUnit.SECONDS)
                .build();

        // Inicializar vistas
        estadoMotorText = findViewById(R.id.estadoMotorText);
        frecuenciaText = findViewById(R.id.frecuenciaText);
        rpmText = findViewById(R.id.rpmText);
        btnConectar = findViewById(R.id.btnConectar);
        btnEncender = findViewById(R.id.btnEncender);
        btnApagar = findViewById(R.id.btnApagar);
        ipEditText = findViewById(R.id.editTextText);

        // Configurar listeners
        btnConectar.setOnClickListener(v -> toggleConnection());
        btnEncender.setOnClickListener(v -> sendMotorCommand("Activado"));
        btnApagar.setOnClickListener(v -> sendMotorCommand("Desactivado"));

        updateUI();
    }

    private void toggleConnection() {
        if (isConnected) {
            disconnectWebSocket();
        } else {
            String ip = ipEditText.getText().toString().trim();
            if (ip.isEmpty()) {
                Toast.makeText(this, "Ingresa una dirección IP", Toast.LENGTH_SHORT).show();
                return;
            }
            connectWebSocket(ip);
        }
    }

    private void connectWebSocket(String ip) {
        String wsUrl = "ws://" + ip + ":81";
        Request request = new Request.Builder()
                .url(wsUrl)
                .build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, okhttp3.Response response) {
                runOnUiThread(() -> {
                    isConnected = true;
                    updateUI();
                    Toast.makeText(MainActivity.this, "Conectado al ESP8266", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                runOnUiThread(() -> updateSensorData(text));
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                runOnUiThread(() -> {
                    isConnected = false;
                    updateUI();
                    Toast.makeText(MainActivity.this, "Conexión cerrada", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
                runOnUiThread(() -> {
                    estadoMotorText.setText("Error de conexión");
                    isConnected = false;
                    updateUI();
                    Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void updateSensorData(String message) {
        try {
            JSONObject json = new JSONObject(message);
            int freq = json.getInt("freq");
            int rpm = json.getInt("rpm");
            frecuenciaText.setText(String.format("Frecuencia: %d Hz", freq));
            rpmText.setText(String.format("RPM: %d", rpm));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendMotorCommand(String command) {
        if (webSocket != null && isConnected) {
            webSocket.send(command);
            estadoMotorText.setText("Estado: " +
                    (command.equals("Activado") ? "MOTOR ACTIVADO" : "MOTOR APAGADO"));
        }
    }

    private void disconnectWebSocket() {
        if (webSocket != null) {
            webSocket.close(1000, "Cerrado por la app");
        }
        isConnected = false;
        updateUI();
    }

    private void updateUI() {
        btnConectar.setText(isConnected ? "Desconectar" : "Conectar");
        btnEncender.setEnabled(isConnected);
        btnApagar.setEnabled(isConnected);
        estadoMotorText.setText(isConnected ? "Estado: CONECTADO" : "Estado: DESCONECTADO");
        ipEditText.setEnabled(!isConnected);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnectWebSocket();
        client.dispatcher().executorService().shutdown();
    }
}