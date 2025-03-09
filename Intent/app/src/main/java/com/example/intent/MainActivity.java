package com.example.intent;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.*;
import android.content.Intent;
import android.widget.*;
public class MainActivity extends Activity{
    EditText jetEjemplo,jetCoeficienteA,jetCoeficienteB,jetCoeficienteC,jetDivisor,jetDividendo;
    Button jbnEjemplo,jbnEjercicio1,jbnEjercicio2;
    Bundle bdlEjemplo,bdlEjercicio1,bdlEjercicio2;
    Intent itnEjemplo,itnEjercicio1,itnEjercicio2;
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        //Ejemplo de intents
        jetEjemplo = (EditText) findViewById(R.id.xet);
        jbnEjemplo = (Button) findViewById(R.id.xbn);
        jbnEjemplo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                itnEjemplo = new Intent(MainActivity.this, SegundaActivity.class);
                bdlEjemplo = new Bundle();
                bdlEjemplo.putString("NOMBRE", jetEjemplo.getText().toString());
                itnEjemplo.putExtras(bdlEjemplo);
                startActivity(itnEjemplo);
            }
        });
        /*Ejercicio 1: Diseñar una aplicación móvil que
        resuelva una ecuación de segundo grado ax2 + bx + c = 0.
        En una primera actividad, el usuario ingresa los valores para a, b, c
        y con un botón se envían a una segunda actividad.
        La segunda actividad calcula las raíces de la solución
         y las muestra en su plantilla.*/
        jetCoeficienteA = (EditText) findViewById(R.id.Id_EditText_CoeficienteA);
        jetCoeficienteB = (EditText) findViewById(R.id.Id_EditText_CoeficienteB);
        jetCoeficienteC = (EditText) findViewById(R.id.Id_EditText_CoeficienteC);
        jbnEjercicio1 = (Button) findViewById(R.id.Id_btn_Ejercicio1);
        jbnEjercicio1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                itnEjercicio1 = new Intent(MainActivity.this,Ejercicio1.class);
                bdlEjercicio1 = new Bundle();
                if (ValidarEjercicio2()){
                    bdlEjercicio1.putDouble("COEFICIENTE A",
                            Double.parseDouble(jetCoeficienteA.getText().toString().trim()));
                    bdlEjercicio1.putDouble("COEFICIENTE B",
                            Double.parseDouble(jetCoeficienteB.getText().toString().trim()));
                    bdlEjercicio1.putDouble("COEFICIENTE C",
                            Double.parseDouble(jetCoeficienteC.getText().toString().trim()));
                    itnEjercicio1.putExtras(bdlEjercicio1);
                    startActivity(itnEjercicio1);
                }
            }
        });
        /*Ejercicio 2: Diseñar una aplicación que permita calcular
         el cociente de dos enteros, desde cantidades pequeñas hasta
         cantidades mayoresa 264. En una primera actividad, el usuario ingresa
         los valores para el dividendo y el divisor.
         La segunda actividad calcula el cociente y muestra el resultado
         hasta el primer decimal si es posible, en su plantilla,
         acomodado en notación de división larga*/
    }
    private boolean ValidarEjercicio2(){
        if(!validarNumero(jetCoeficienteA, "Coeficiente A")){
            return false;
        }
        if(!validarNumero(jetCoeficienteB, "Coeficiente B")){
            return false;
        }
        if(!validarNumero(jetCoeficienteC, "Coeficiente C")){
            return false;
        }
        return true;
    }

    private boolean validarNumero(EditText editText, String nombre){
        String input = editText.getText().toString().trim();
        if (input.isEmpty()){
            editText.setError("El campo " + nombre + " no puede estar vacio");
            return false;
        }
        try{
            double number = Double.parseDouble(input);
            editText.setError(null);
            return true;
        }catch (NumberFormatException e){
            editText.setError("El campo " +  nombre + " debe ser un numero valido");
            return false;
        }
    }
}