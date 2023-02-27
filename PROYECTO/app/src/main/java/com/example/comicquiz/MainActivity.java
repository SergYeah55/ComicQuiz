package com.example.comicquiz;

import androidx.appcompat.app.AppCompatActivity;

//Zona de importaciones de diferentes elementos y funcionalidades de la activity
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.comicquiz.basedatos.dbHelper;
import com.example.comicquiz.basedatos.gestorPreguntas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private Button salirBoton;
    private Button comenzarBoton;
    private Button creditosBoton;
    private Button rankingBoton;

    //A través del metodo para cuando se cree la activity realizará lo esencial de este menú
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Con esto forzamos el modo vertical del móvil
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comenzarBoton = (Button)findViewById(R.id.startButton);
        comenzarBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbHelper baseDatos = new dbHelper(MainActivity.this);
                baseDatos.onUpgrade(baseDatos.getWritableDatabase(), baseDatos.databaseVersion, baseDatos.databaseVersion+1);
                SQLiteDatabase refBase = baseDatos.getWritableDatabase();

                Log.i("Database", "Se ha creado la base de datos");

                rellenarBaseDatos();

                Intent toInicioActivity = new Intent(getApplicationContext(),InicioPartidaActivity.class);
                startActivity(toInicioActivity);
                finish();
            }


        });

        //Se define el comportamiento para el boton de salida
        salirBoton = (Button)findViewById(R.id.exitButton);
        salirBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String despedida = getResources().getString(R.string.mensajeSalida);
                Toast.makeText(MainActivity.this, despedida, Toast.LENGTH_LONG).show();
                finish();
            }
        });

        creditosBoton = (Button)findViewById(R.id.creditsButton);
        creditosBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCreditsActivity = new Intent(getApplicationContext(),CreditsActivity.class);
                startActivity(toCreditsActivity);
                finish();
            }
        });

        rankingBoton = (Button)findViewById(R.id.rankingButton);
        rankingBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRankingActivity = new Intent(getApplicationContext(),RankingActivity.class);
                startActivity(toRankingActivity);
                finish();
            }
        });

    }

    public void rellenarBaseDatos(){
        gestorPreguntas prueba = new gestorPreguntas(MainActivity.this);

        BufferedReader lectorAux = null;
        try{
            lectorAux = new BufferedReader(new InputStreamReader(MainActivity.this.getAssets().open("Preguntas.json"),"UTF-8"));
            String contenido = "";
            String linea;

            while((linea = lectorAux.readLine())!= null){
                contenido = contenido + linea;
            }

            JSONArray arrayAux = new JSONArray(contenido);

            for (int i = 0; i<arrayAux.length(); i++){
                JSONObject objetoAux = arrayAux.getJSONObject(i);
                String categoria = objetoAux.getString("categoria");
                int numero = objetoAux.getInt("numero");
                int imagenEnunciado = objetoAux.getInt("imagenEnunciado");
                int imagenRespuestas = objetoAux.getInt("imagenRespuestas");
                int sonidoPregunta = objetoAux.getInt("sonidoPregunta");
                int videoPregunta = objetoAux.getInt("videoPregunta");
                String enunciado = objetoAux.getString("enunciado");
                String respuesta1 = objetoAux.getString("respuesta1");
                String respuesta2 = objetoAux.getString("respuesta2");
                String respuesta3 = objetoAux.getString("respuesta3");
                String respuesta4 = objetoAux.getString("respuesta4");
                int correcta = objetoAux.getInt("correcta");
                String codigoImagenEnunciado = objetoAux.getString("codigoImagenEnunciado");
                String codigoImagenRespuesta1 = objetoAux.getString("codigoImagenRespuesta1");
                String codigoImagenRespuesta2= objetoAux.getString("codigoImagenRespuesta2");
                String codigoImagenRespuesta3 = objetoAux.getString("codigoImagenRespuesta3");
                String codigoImagenRespuesta4 = objetoAux.getString("codigoImagenRespuesta4");
                String codigoSonido = objetoAux.getString("codigoSonido");
                String codigoVideo = objetoAux.getString("codigoVideo");

                long id = prueba.insertarPregunta(categoria, numero, imagenEnunciado, imagenRespuestas,
                        sonidoPregunta, videoPregunta, enunciado, respuesta1, respuesta2, respuesta3,
                        respuesta4, correcta, codigoImagenEnunciado, codigoImagenRespuesta1,
                        codigoImagenRespuesta2,codigoImagenRespuesta3,codigoImagenRespuesta4,
                        codigoSonido, codigoVideo);
                //Log.i("Rellenado", "Enunciado: "+enunciado);
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    //Se inutiliza el botón hacia atrás de Android
    @Override
    public void onBackPressed() { }

}