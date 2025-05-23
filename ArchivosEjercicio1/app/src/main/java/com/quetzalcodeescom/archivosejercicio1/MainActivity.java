package com.quetzalcodeescom.archivosejercicio1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class MainActivity extends Activity {
    TextView tv;
    String s;
    InputStream is;
    InputStreamReader isr;
    BufferedReader br;
    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.xtv);
        tv.append("\nAbriendo: res/raw/misdatos.txt"); // NOTA: Este es su archivo.
        is = getResources().openRawResource(R.raw.misdatos);
        isr = new InputStreamReader(is);
        br = new BufferedReader(isr, 8192);
        try{
            while( null != (s=br.readLine()) )
                tv.append("\n" + s);
            is.close();
            isr.close();
            br.close();
        } catch(Exception e){
            tv.append("\n " + e);
        }
        tv.append("\nEnd of file.");
    }
}