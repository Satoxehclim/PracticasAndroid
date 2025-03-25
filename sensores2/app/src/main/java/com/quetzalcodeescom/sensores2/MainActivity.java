package com.quetzalcodeescom.sensores2;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private int updateCount = 0;
    private double x, y, z, acceleration, maxAcceleration;
    private final double standardGravity = SensorManager.STANDARD_GRAVITY;

    private TextView jtvX, jtvY, jtvZ, jtvA, jtvM, jtvG;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable updateTask = new Runnable() {
        @Override
        public void run() {
            // Actualizar la interfaz de usuario
            jtvX.setText(String.format("X: %.2f", x));
            jtvY.setText(String.format("Y: %.2f", y));
            jtvZ.setText(String.format("Z: %.2f", z));
            jtvA.setText(String.format("Aceleración: %.2f", acceleration));
            jtvM.setText(String.format("Máxima Aceleración: %.2f", maxAcceleration));
            jtvG.setText(String.format("Gravedad estándar: %.2f | Actualización: %d (ms)", standardGravity, updateCount));

            // Programar la siguiente actualización
            handler.postDelayed(this, 100); // Actualizar cada 100 ms
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        jtvX = findViewById(R.id.xtvX);
        jtvY = findViewById(R.id.xtvY);
        jtvZ = findViewById(R.id.xtvZ);
        jtvA = findViewById(R.id.xtvA);
        jtvM = findViewById(R.id.xtvM);
        jtvG = findViewById(R.id.xtvG);

        // Inicializar SensorManager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager == null) {
            Toast.makeText(this, "SensorManager no está disponible", Toast.LENGTH_SHORT).show();
            finish(); // Cerrar la actividad si no hay SensorManager
            return;
        }

        // Obtener el sensor de acelerómetro
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer == null) {
            Toast.makeText(this, "El dispositivo no tiene acelerómetro", Toast.LENGTH_SHORT).show();
            finish(); // Cerrar la actividad si no hay acelerómetro
            return;
        }

        // Iniciar la tarea de actualización
        handler.post(updateTask);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registrar el listener del sensor
        try {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        } catch (Exception e) {
            Toast.makeText(this, "Error al registrar el listener del sensor"+e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Desregistrar el listener del sensor
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Obtener los valores del acelerómetro
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];

        // Calcular la aceleración total
        acceleration = Math.sqrt(x * x + y * y + z * z);

        // Actualizar la aceleración máxima
        if (acceleration > maxAcceleration) {
            maxAcceleration = acceleration;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No es necesario implementar esto para el acelerómetro
    }
}