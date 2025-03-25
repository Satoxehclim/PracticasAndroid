package com.quetzalcodeescom.botondirectodesdeelxml;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
public class MainActivity extends Activity{
    Button jbn4;
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
    }
    public void xbn4DesdeXML(View v) { // MÉTODO 4
        showToastMessage("Boton digitado: xbn4\nInvoca al método desde el XML.");
    }
    private void showToastMessage(String msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
