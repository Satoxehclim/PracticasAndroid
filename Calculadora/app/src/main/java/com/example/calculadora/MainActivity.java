package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0,btnMas,btnMenos,btnDividir,btnMultiplicar,btnPunto,btnLimpiar,btnResultado,btnBorrar;
    TextView tvResultado,tvOperaciones;
    private double resultadoParcial = 0;
    private String operacionActual = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn1 = (Button) findViewById(R.id.Id_btn_1);
        btn2 = (Button) findViewById(R.id.Id_btn_2);
        btn3 = (Button) findViewById(R.id.Id_btn_3);
        btn4 = (Button) findViewById(R.id.Id_btn_4);
        btn5 = (Button) findViewById(R.id.Id_btn_5);
        btn6 = (Button) findViewById(R.id.Id_btn_6);
        btn7 = (Button) findViewById(R.id.Id_btn_7);
        btn8 = (Button) findViewById(R.id.Id_btn_8);
        btn9 = (Button) findViewById(R.id.Id_btn_9);
        btn0 = (Button) findViewById(R.id.Id_btn_0);
        btnMas = (Button) findViewById(R.id.Id_btn_mas);
        btnMenos = (Button) findViewById(R.id.Id_btn_menos);
        btnDividir = (Button) findViewById(R.id.Id_btn_dividir);
        btnMultiplicar = (Button) findViewById(R.id.Id_btn_multiplicar);
        btnPunto = (Button) findViewById(R.id.Id_btn_Punto);
        btnLimpiar = (Button) findViewById(R.id.Id_btn_limpiar);
        btnResultado = (Button) findViewById(R.id.Id_btn_resolver);
        btnBorrar = (Button) findViewById(R.id.Id_btn_borrar);
        tvResultado =(TextView) findViewById(R.id.Id_tv_resultado);
        tvOperaciones =(TextView) findViewById(R.id.Id_tv_operaciones);
        tvOperaciones.append("\n");
        //Funcion
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.append("0");
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.append("1");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.append("2");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.append("3");
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.append("4");
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.append("5");
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.append("6");
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.append("7");
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.append("8");
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.append("9");
            }
        });
        btnPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numeroActual = tvResultado.getText().toString().trim();
                if (numeroActual.isEmpty()){
                    tvResultado.append("0.");
                }else if (!numeroActual.contains(".")) {
                    tvResultado.append(".");
                }
            }
        });
        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manejarOperacion("+");
            }
        });

        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manejarOperacion("-");
            }
        });

        btnMultiplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manejarOperacion("*");
            }
        });

        btnDividir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manejarOperacion("/");
            }
        });
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numeroActual = tvResultado.getText().toString().trim();
                if (!numeroActual.isEmpty()){
                   numeroActual = numeroActual.substring(0,numeroActual.length()-1);
                   tvResultado.setText(numeroActual);
                }
            }
        });
        btnBorrar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tvResultado.setText("");
                return false;
            }
        });
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResultado.setText("");
                tvOperaciones.setText("Las operaciones realizadas:\n");
            }
        });
        btnResultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularResultado();
            }
        });
    }
    private void manejarOperacion(String operador) {
        String numeroActual = tvResultado.getText().toString().trim();

        if (numeroActual.isEmpty()) {
            numeroActual = "0"; // Si no hay número, se asume 0
        }

        try {
            double numero = Double.parseDouble(numeroActual);

            // Si es la primera operación, guardar el número como resultado parcial
            if (operacionActual.isEmpty()) {
                resultadoParcial = numero;
            } else {
                // Realizar la operación acumulada
                switch (operacionActual) {
                    case "+":
                        resultadoParcial += numero;
                        break;
                    case "-":
                        resultadoParcial -= numero;
                        break;
                    case "*":
                        resultadoParcial *= numero;
                        break;
                    case "/":
                        if (numero == 0) {
                            Toast.makeText(MainActivity.this, "No se puede dividir por 0", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        resultadoParcial /= numero;
                        break;
                }
            }

            // Actualizar la operación actual
            operacionActual = operador;

            // Mostrar la operación en tvOperaciones
            tvOperaciones.append(numero + operador);

            // Vaciar tvResultado para la siguiente entrada
            tvResultado.setText("");

        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, "Número inválido", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para calcular el resultado final
    private void calcularResultado() {
        String numeroActual = tvResultado.getText().toString().trim();

        if (numeroActual.isEmpty()) {
            numeroActual = "0"; // Si no hay número, se asume 0
        }

        try {
            double numero = Double.parseDouble(numeroActual);

            // Realizar la última operación pendiente
            switch (operacionActual) {
                case "+":
                    resultadoParcial += numero;
                    break;
                case "-":
                    resultadoParcial -= numero;
                    break;
                case "*":
                    resultadoParcial *= numero;
                    break;
                case "/":
                    if (numero == 0) {
                        Toast.makeText(MainActivity.this, "No se puede dividir por 0", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    resultadoParcial /= numero;
                    break;
            }

            // Mostrar el resultado final en tvResultado
            String operacionFinal = String.valueOf(numero) + "=" + String.valueOf(resultadoParcial) + "\n";
            tvOperaciones.append(operacionFinal);
            tvResultado.setText(String.valueOf(resultadoParcial));

            // Reiniciar la operación actual y el resultado parcial
            operacionActual = "";
            resultadoParcial = 0;

        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, "Número inválido", Toast.LENGTH_SHORT).show();
        }
    }

}