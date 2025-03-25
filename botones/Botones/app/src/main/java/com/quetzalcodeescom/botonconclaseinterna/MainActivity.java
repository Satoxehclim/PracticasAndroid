package com.quetzalcodeescom.botonconclaseinterna;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
public class MainActivity extends Activity{
    Button jbn1;
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        jbn1 = (Button) findViewById(R.id.xbn1);  // MÉTODO 1
        jbn1.setOnClickListener(bn1Listener);
    }
    private OnClickListener bn1Listener = new OnClickListener() {
        public void onClick(View v) {
            showToastMessage("Botón digitado: xbn1\nUtiliza: clase btn1Listener");
        }
    };
    private void showToastMessage(String msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}