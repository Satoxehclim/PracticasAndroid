package com.quetzalcodeescom.sensores3;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {
    private int updateCount = 0;
    private boolean isRunning = true;
    private double x = 0, y = 0, z = 0, magneticFieldStrength = 0, maxMagneticFieldStrength = 0;
    private final double minEarthMagneticField = SensorManager.MAGNETIC_FIELD_EARTH_MIN;
    private final double maxEarthMagneticField = SensorManager.MAGNETIC_FIELD_EARTH_MAX;

    private SensorManager sensorManager;
    private Sensor magneticFieldSensor;

    private TextView jtvX, jtvY, jtvZ, jtvB, jtvM, jtvT;

    private Handler handler = new Handler();
    private Runnable updateTask = new Runnable() {
        @Override
        public void run() {
            // Actualizar la interfaz de usuario
            jtvX.setText(String.format("X: %.2f", x));
            jtvY.setText(String.format("Y: %.2f", y));
            jtvZ.setText(String.format("Z: %.2f", z));
            jtvB.setText(String.format("Campo Magnético: %.2f", magneticFieldStrength));
            jtvM.setText(String.format("Máximo Campo Magnético: %.2f", maxMagneticFieldStrength));
            jtvT.setText(String.format("Rango Terrestre: %.2f - %.2f | Actualización: %d (ms)",
                    minEarthMagneticField, maxEarthMagneticField, updateCount));

            updateCount++;

            // Programar la siguiente actualización si la actividad está en ejecución
            if (isRunning) {
                handler.postDelayed(this, 200); // Actualizar cada 200 ms
            }
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
        jtvB = findViewById(R.id.xtvB);
        jtvM = findViewById(R.id.xtvM);
        jtvT = findViewById(R.id.xtvT);

        // Inicializar SensorManager y sensor de campo magnético
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            magneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        }

        // Verificar si el sensor de campo magnético está disponible
        if (magneticFieldSensor == null) {
            Toast.makeText(this, "El dispositivo no tiene sensor de campo magnético", Toast.LENGTH_SHORT).show();
            finish(); // Cerrar la actividad si no hay sensor
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;

        // Registrar el listener del sensor
        if (magneticFieldSensor != null) {
            sensorManager.registerListener(this, magneticFieldSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        // Iniciar la tarea de actualización
        handler.post(updateTask);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;

        // Desregistrar el listener del sensor
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }

        // Detener la tarea de actualización
        handler.removeCallbacks(updateTask);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Obtener los valores del sensor de campo magnético
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];

        // Calcular la fuerza del campo magnético
        magneticFieldStrength = Math.sqrt(x * x + y * y + z * z);

        // Actualizar la fuerza máxima del campo magnético
        if (magneticFieldStrength > maxMagneticFieldStrength) {
            maxMagneticFieldStrength = magneticFieldStrength;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No es necesario implementar esto para el sensor de campo magnético
    }
}