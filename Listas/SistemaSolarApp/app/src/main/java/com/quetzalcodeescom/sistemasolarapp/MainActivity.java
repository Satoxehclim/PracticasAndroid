package com.quetzalcodeescom.sistemasolarapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ListView lv;
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.listado);
        ArrayList<ListaEntrada> al = new ArrayList<ListaEntrada>();
        al.add(new ListaEntrada(R.drawable.mercurio,
                "MERCURIO", "Mercurio es el planeta más cercano al Sol y el más pequeño del sistema solar. " +
                "Su superficie está cubierta de cráteres debido a impactos de meteoritos. No tiene atmósfera " +
                "significativa, lo que hace que las temperaturas varíen drásticamente entre el día y la noche."));

        al.add(new ListaEntrada(R.drawable.venus,
                "VENUS", "Venus es el segundo planeta desde el Sol y el más caliente del sistema solar. " +
                "Su densa atmósfera de dióxido de carbono atrapa el calor mediante un efecto invernadero extremo. " +
                "Tiene una superficie rocosa cubierta de volcanes y densas nubes de ácido sulfúrico."));

        al.add(new ListaEntrada(R.drawable.tierra,
                "TIERRA", "La Tierra es el único planeta conocido con vida. Posee una atmósfera rica en oxígeno " +
                "y agua en estado líquido, lo que permite la existencia de diversos ecosistemas. Su superficie " +
                "está compuesta por continentes y océanos."));

        al.add(new ListaEntrada(R.drawable.marte,
                "MARTE", "Conocido como el planeta rojo debido a su suelo rico en óxido de hierro. Marte posee " +
                "las montañas más altas y los valles más profundos del sistema solar, incluyendo el Monte Olimpo. " +
                "Se cree que tuvo agua líquida en el pasado y es el principal candidato para la exploración humana."));

        al.add(new ListaEntrada(R.drawable.jupiter,
                "JÚPITER", "El planeta más grande del sistema solar, compuesto principalmente de hidrógeno y helio. " +
                "Tiene una gran tormenta llamada la Gran Mancha Roja, que ha existido durante siglos. También " +
                "posee más de 75 lunas, incluidas Ío, Europa, Ganimedes y Calisto."));

        al.add(new ListaEntrada(R.drawable.saturno,
                "SATURNO", "Conocido por sus impresionantes anillos compuestos de hielo y roca. Es el segundo " +
                "planeta más grande del sistema solar y tiene una atmósfera de hidrógeno y helio. Titán, su " +
                "luna más grande, tiene una atmósfera densa y lagos de hidrocarburos."));

        al.add(new ListaEntrada(R.drawable.urano,
                "URANO", "Un gigante helado inclinado de lado con respecto a su órbita. Su color azul verdoso " +
                "se debe a la presencia de metano en su atmósfera. Urano tiene un sistema de anillos delgado " +
                "y es el planeta más frío del sistema solar."));

        al.add(new ListaEntrada(R.drawable.neptuno,
                "NEPTUNO", "El planeta más distante del Sol y el más ventoso del sistema solar. Posee un clima " +
                "extremo con vientos supersónicos. Su color azul intenso se debe al metano en su atmósfera. " +
                "Neptuno también tiene un sistema de anillos y una luna gigante llamada Tritón."));

        lv = (ListView) findViewById(R.id.ListView_listado);
        lv.setAdapter(new ListaAdapter(this, R.layout.activity_main, al) {
            public void onEntrada(Object o, View v) {
                if (o != null) {
                    TextView texto_superior_entrada = (TextView)
                            v.findViewById(R.id.textView_superior);
                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((ListaEntrada) o).get_textoEncima());
                    TextView texto_inferior_entrada = (TextView)
                            v.findViewById(R.id.textView_inferior);
                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((ListaEntrada) o).get_textoDebajo());
                    ImageView imagen_entrada = (ImageView) v.findViewById(R.id.imageView_imagen);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((ListaEntrada) o).get_idImagen());
                }
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el elemento seleccionado
                ListaEntrada itemSeleccionado = (ListaEntrada) parent.getItemAtPosition(position);

                // Crear un TextView para el mensaje
                TextView textView = new TextView(MainActivity.this);
                textView.setText("Seleccionaste: " + itemSeleccionado.get_textoEncima() +
                        "\n\nDescripción: " + itemSeleccionado.get_textoDebajo());
                textView.setPadding(20, 20, 20, 20);
                textView.setTextSize(16);
                textView.setTextColor(Color.WHITE);
                textView.setBackgroundColor(Color.parseColor("#B0000000")); // Negro opaco

                // Crear y mostrar el Toast personalizado
                Toast toast = new Toast(MainActivity.this);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(textView);
                toast.show();
            }
        });
    }
}