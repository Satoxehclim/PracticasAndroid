package com.example.proyectomoviles2;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBarLeftHorizontal;
    private SeekBar seekBarRightHorizontal;
    private SeekBar seekBarLeftVertical;
    private SeekBar seekBarRightVertical;
    private TextView tvLeftHorizontal;
    private TextView tvRightHorizontal;
    private TextView tvLeftVertical;
    private TextView tvRightVertical;

    private static final String SERVER_IP = "192.168.4.1"; // IP del Wemos (AP)
    private static final int UDP_PORT = 8888;

    // Flags de control para evitar bucles infinitos
    private boolean isSyncingLeft = false;
    private boolean isSyncingRight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        seekBarLeftHorizontal = findViewById(R.id.seekBar_servo_left_horizontal);
        seekBarRightHorizontal = findViewById(R.id.seekBar_servo_right_horizontal);
        seekBarLeftVertical = findViewById(R.id.seekBar_servo_left_vertical);
        seekBarRightVertical = findViewById(R.id.seekBar_servo_right_vertical);
        tvLeftHorizontal = findViewById(R.id.tv_servo_left_horizontal);
        tvRightHorizontal = findViewById(R.id.tv_servo_right_horizontal);
        tvLeftVertical = findViewById(R.id.tv_servo_left_vertical);
        tvRightVertical = findViewById(R.id.tv_servo_right_vertical);

        // Configurar listeners cruzados
        setupSyncedSeekBars(seekBarLeftHorizontal, seekBarLeftVertical, tvLeftHorizontal, tvLeftVertical, 1, true);
        setupSyncedSeekBars(seekBarLeftVertical, seekBarLeftHorizontal, tvLeftVertical, tvLeftHorizontal, 1, false);

        setupSyncedSeekBars(seekBarRightHorizontal, seekBarRightVertical, tvRightHorizontal, tvRightVertical, 2, true);
        setupSyncedSeekBars(seekBarRightVertical, seekBarRightHorizontal, tvRightVertical, tvRightHorizontal, 2, false);
    }

    private void setupSyncedSeekBars(SeekBar mainSeekBar, SeekBar linkedSeekBar, TextView mainTextView, TextView linkedTextView, int servoNumber, boolean isHorizontal) {
        mainSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (servoNumber == 1) {
                    if (isSyncingLeft) return;
                    isSyncingLeft = true;
                } else {
                    if (isSyncingRight) return;
                    isSyncingRight = true;
                }

                String servoName = (servoNumber == 1) ? "Izquierdo" : "Derecho";
                String text = "Servo " + servoName + ": " + progress + "°";
                mainTextView.setText(text);
                linkedTextView.setText(text);  // Sincronizar también el TextView

                linkedSeekBar.setProgress(progress); // Actualizar la SeekBar vinculada
                sendUdpCommand(servoNumber, progress);

                if (servoNumber == 1) {
                    isSyncingLeft = false;
                } else {
                    isSyncingRight = false;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // No hacer nada
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // No hacer nada
            }
        });
    }

    private void sendUdpCommand(int servo, int angle) {
        new Thread(() -> {
            try {
                DatagramSocket socket = new DatagramSocket();
                InetAddress address = InetAddress.getByName(SERVER_IP);
                String message = servo + "," + angle;
                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, UDP_PORT);
                socket.send(packet);
                socket.close();
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
