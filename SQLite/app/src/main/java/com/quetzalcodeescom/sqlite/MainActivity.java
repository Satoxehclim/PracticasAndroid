package com.quetzalcodeescom.sqlite;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
public class MainActivity extends Activity {
    EditText jetI, jetN;
    Button jbnA, jbnL, jbnE, jbnC;
    TextView jtvL;
    SQLiteDatabase sqld;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jetI = (EditText) findViewById(R.id.xetI);
        jetN = (EditText) findViewById(R.id.xetN);
        jbnA = (Button) findViewById(R.id.xbnA);
        jbnL = (Button) findViewById(R.id.xbnL);
        jbnE = (Button) findViewById(R.id.xbnE);
        jbnC = (Button) findViewById(R.id.xbnC);
        jtvL = (TextView) findViewById(R.id.xtvL);
        DbmsSQLiteHelper dsqlh = new DbmsSQLiteHelper(this, "DBContactos", null, 1);
        sqld = dsqlh.getWritableDatabase();
        jbnA.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String nombre = jetN.getText().toString().trim();
                if (!nombre.isEmpty()){
                    ContentValues cv = new ContentValues();
                    cv.put("nombre", nombre);
                    sqld.insert("Contactos", null, cv);
                    jetI.setText(""); jetN.setText("");
                    String id;
                    Cursor c = sqld.rawQuery("SELECT id,nombre FROM Contactos", null);
                    jtvL.setText("");
                    if (c.moveToFirst()) {
                        do {
                            id = c.getString(0);
                            nombre = c.getString(1);
                            jtvL.append(" " + id + "\t" + nombre + "\n");
                        } while(c.moveToNext());
                    }
                }else Toast.makeText(MainActivity.this,"El campo de Nombre no puede ser vacio!",Toast.LENGTH_SHORT).show();
            }
        });
        jbnL.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String id, nombre;
                Cursor c = sqld.rawQuery("SELECT id,nombre FROM Contactos", null);
                jtvL.setText("");
                if (c.moveToFirst()) {
                    do {
                        id = c.getString(0);
                        nombre = c.getString(1);
                        jtvL.append(" " + id + "\t" + nombre + "\n");
                    } while(c.moveToNext());
                }
            }
        });
        jbnE.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = jetI.getText().toString().trim();
                if (!id.isEmpty()){
                    int filasAfectadas = sqld.delete(
                            "Contactos",
                            "id = ?",
                            new String[]{id}
                    );
                    jetI.setText(""); jetN.setText("");
                    Toast.makeText(MainActivity.this,"Se han eliminado: "+filasAfectadas+" filas",Toast.LENGTH_SHORT).show();
                    String  nombre;
                    Cursor c = sqld.rawQuery("SELECT id,nombre FROM Contactos", null);
                    jtvL.setText("");
                    if (c.moveToFirst()) {
                        do {
                            id = c.getString(0);
                            nombre = c.getString(1);
                            jtvL.append(" " + id + "\t" + nombre + "\n");
                        } while(c.moveToNext());
                    }
                }else Toast.makeText(MainActivity.this,"El campo ID no debe estar vacio!",Toast.LENGTH_SHORT).show();
            }
        });
        jbnC.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("nombre",jetN.getText().toString().trim());
                int filasAfectadas = sqld.update(
                        "Contactos",
                        contentValues,
                        "id = ?",
                        new String[]{jetI.getText().toString()}
                );
                jetI.setText(""); jetN.setText("");
                Toast.makeText(MainActivity.this,"Se han Actualizado: "+filasAfectadas+" filas",Toast.LENGTH_SHORT).show();
                String id, nombre;
                Cursor c = sqld.rawQuery("SELECT id,nombre FROM Contactos", null);
                jtvL.setText("");
                if (c.moveToFirst()) {
                    do {
                        id = c.getString(0);
                        nombre = c.getString(1);
                        jtvL.append(" " + id + "\t" + nombre + "\n");
                    } while(c.moveToNext());
                }
            }
        });
    }
}