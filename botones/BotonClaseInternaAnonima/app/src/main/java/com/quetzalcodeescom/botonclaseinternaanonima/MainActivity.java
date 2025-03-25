package com.quetzalcodeescom.botonclaseinternaanonima;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
public class MainActivity extends Activity{
    Button jbn2;
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        jbn2 = (Button)findViewById(R.id.xbn2);	// MÉTODO 2
        jbn2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastMessage("Botón digitado: xbn2\nUtiliza: new OnClickListener{}");
            }
        });
    }
    private void showToastMessage(String msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}