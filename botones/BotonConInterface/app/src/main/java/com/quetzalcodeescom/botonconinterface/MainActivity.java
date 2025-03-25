package com.quetzalcodeescom.botonconinterface;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
public class MainActivity extends Activity implements OnClickListener{
    Button jbn3;
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        jbn3 = (Button) findViewById(R.id.xbn3); // MÉTODO 3
        jbn3.setOnClickListener(this);
    }
    public void onClick(View v) {
        showToastMessage("Botón digitado: xbn3\nUtiliza: implements OnClickListener.");
    }
    private void showToastMessage(String msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}