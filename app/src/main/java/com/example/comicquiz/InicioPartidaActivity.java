package com.example.comicquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//Zona de importaciones de diferentes elementos y funcionalidades de la activity
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.comicquiz.basedatos.dbHelper;
import com.example.comicquiz.basedatos.gestorPreguntas;
import com.example.comicquiz.clases.Pregunta;

import java.util.ArrayList;

public class InicioPartidaActivity extends AppCompatActivity {

    //Declaración de las variables del spinner
    private Spinner numPreguntas;
    private Spinner categoria;
    private int numPreguntasFinal;

    //Declaración de las referencias de los botones del XML
    private Button comenzarBoton;
    private CheckBox mostrarCorecta;
    private EditText nombreUsuario;

    //Botones Pruebas
    private Button mostrarPreguntas;

    //A través del metodo para cuando se cree la activity realizará lo esencial de este menú
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Con esto forzamos el modo vertical del móvil
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_partida);

        //Se inicializa el spinner para poder elegir el número de opciones
        initSpinner();

        //Se define el comportamiento del CheckBox que indica si queremos ver la respuesta correcta al fallar o no
        mostrarCorecta = (CheckBox) findViewById(R.id.mostrarCorrecta);

        //Se define el comportamiento del EditText que indica el nombre del usuario
        nombreUsuario = (EditText) findViewById(R.id.enterPlayerName);

        //Se define el comportamiento del boton para comenzar el juego
        comenzarBoton = (Button)findViewById(R.id.startGameButton);
        comenzarBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombreUsuario.getText().toString().equals("NoName")){
                    AlertDialog.Builder builderNoName = new AlertDialog.Builder(InicioPartidaActivity.this);
                    builderNoName.setMessage(R.string.mensajeNoName);
                    builderNoName.setCancelable(false);
                    builderNoName.setPositiveButton(R.string.textSi, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            iniciarPartida();
                        }
                    });
                    builderNoName.setNegativeButton(R.string.textNo, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builderNoName.create().show();
                }else{
                    iniciarPartida();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent toMainActivity = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(toMainActivity);
        finish();
    }

    private void iniciarPartida(){
        String num = numPreguntas.getSelectedItem().toString();
        numPreguntasFinal = Integer.parseInt(num);

        String cat = categoria.getSelectedItem().toString();

        String userName = nombreUsuario.getText().toString();

        //Se crea un Intent para pasar los datos seleccionados a la actividad del juego
        Intent toJuegoActivity = new Intent(getApplicationContext(),JuegoActivity.class);
        toJuegoActivity.putExtra("NombreUsuario", userName);
        toJuegoActivity.putExtra("CategoriaElegida", cat);
        toJuegoActivity.putExtra("NumeroPreguntas", numPreguntasFinal);
        toJuegoActivity.putExtra("MostrarCorrecta", mostrarCorecta.isChecked());
        startActivity(toJuegoActivity);
        finish();
    }

    private void initSpinner() {
        //Creación e inicialización del spinner que permite elegir el número de preguntas
        numPreguntas = (Spinner)findViewById(R.id.spinnerPreguntas);

        //Creación e inicialización del spinner que permite elegir la categoría
        categoria = (Spinner)findViewById(R.id.spinnerCategory);

        //Establece las posibilidades de preguntas que habrá mediante un array e inicializamos por defecto
        String [] opcionesPreguntas = {"5","10", "15", "20"};
        numPreguntasFinal = 0;

        //Establece las posibles categorías escogibles
        String[] categorias = {"Marvel", "DC Comics", "Manga", "Adaptaciones"};

        //Se elabora el adaptador para poner el spinner por pantalla
        ArrayAdapter auxPreguntas = new ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesPreguntas);
        ArrayAdapter auxCategorias = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias);

        auxPreguntas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        auxCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        numPreguntas.setAdapter(auxPreguntas);
        categoria.setAdapter(auxCategorias);
    }

}