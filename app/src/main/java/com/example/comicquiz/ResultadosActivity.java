package com.example.comicquiz;

import androidx.appcompat.app.AppCompatActivity;

//Zona de importaciones de diferentes elementos y funcionalidades de la activity
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comicquiz.clases.Jugador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ResultadosActivity extends AppCompatActivity {

    //Se crean las variables que nos permitirán conocer las respuestas correctas y las que no
    boolean[] respCorrectas;
    String tiempoTotal;
    String userName;
    String categoria;
    int numCorrectas;

    Jugador player = new Jugador();

    //Se declaran las variables que servirán para hacer las referencias a los elementos de layout
    private TextView contCorrectas;
    private TextView contIncorrectas;
    private TextView contTime;
    private TextView showName;
    private Button botonMainMenu;
    private Button botonSalir;
    private Button botonRanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Con esto forzamos el modo vertical del móvil
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        //Se recoge el número de respuestas correctas de la activity anterior
        respCorrectas = getIntent().getBooleanArrayExtra("TotalCorrectas");

        //Se recoge el tiempo que ha tardado el jugador
        tiempoTotal = getIntent().getStringExtra("TiempoTotal");

        //Se recoge el nombre de usuario
        userName = getIntent().getStringExtra("NombreUsuario");

        //Se recoge la categoría
        categoria = getIntent().getStringExtra("Categoria");

        //Se recoge el número de correctas en total
        recuentoCorrectas();

        //Se referencian todos los elementos del layout
        contCorrectas = (TextView)findViewById(R.id.contCorrectas);
        contIncorrectas = (TextView)findViewById(R.id.contIncorrectas);
        contTime = (TextView) findViewById(R.id.contTiempoTotal);
        showName = (TextView) findViewById(R.id.nameShow);
        botonMainMenu = (Button) findViewById(R.id.botonReinicio);
        botonSalir = (Button) findViewById(R.id.botonSalir);
        botonRanking = (Button) findViewById(R.id.botonRanking);

        //Se le agregan todos los datos dela partida al elemento jugador
        player.setCorrectas(numCorrectas);
        player.setFallos(respCorrectas.length-numCorrectas);
        player.setNombre(userName);
        player.setTiempo(tiempoTotal);

        //Se mopdifican los textos de los resultados
        contCorrectas.setText(""+player.getCorrectas());
        contIncorrectas.setText(""+player.getFallos());
        contTime.setText(player.getTiempo());
        showName.setText(player.getNombre());

        //Se llama al método que actualiza los JSON
        try {
            actualizarRanking();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        //Se establece el comportamiento al pulsar el botón de salir.
        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String despedida = getResources().getString(R.string.mensajeSalida);
                Toast.makeText(ResultadosActivity.this, despedida, Toast.LENGTH_LONG).show();
                finish();
            }
        });

        //Se establece el comportamiento al pulsar el botón de ir al menu
        botonMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMainActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(toMainActivity);
                finish();
            }
        });

        //Se establece el comportamiento al pulsar el botón de ir al ranking
        botonRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRankingActivity = new Intent(getApplicationContext(),RankingActivity.class);
                toRankingActivity.putExtra("Categoria", categoria);
                startActivity(toRankingActivity);
                finish();
            }
        });

    }

    public void actualizarRanking () throws JSONException, IOException {

        //Lectura de los ranking
        ArrayList<Jugador> jugadores = new ArrayList<>();

        FileInputStream rankingFileInput = null;
        BufferedReader lectorAux = null;
        try{
            switch (categoria){
                case "Marvel":
                    rankingFileInput = openFileInput("RankingMarvel.json");
                    break;
                case "DC Comics":
                    rankingFileInput = openFileInput("RankingDC.json");
                    break;
                case "Manga":
                    rankingFileInput = openFileInput("RankingManga.json");
                    break;
                case "Adaptaciones":
                    rankingFileInput = openFileInput("RankingAdaptaciones.json");
                    break;
            }
            InputStreamReader rankingReader = new InputStreamReader(rankingFileInput);
            lectorAux = new BufferedReader(rankingReader);
            String contenido = "";
            String linea;

            while((linea = lectorAux.readLine())!= null){
                contenido = contenido + linea;
            }
            lectorAux.close();

            JSONArray arrayAux = new JSONArray(contenido);

            for (int i = 0; i<arrayAux.length(); i++){
                JSONObject objetoAux = arrayAux.getJSONObject(i);
                String nombre = objetoAux.getString("nombre");
                String tiempo = objetoAux.getString("tiempo");
                int correctas = objetoAux.getInt("correctas");
                int fallos = objetoAux.getInt("fallos");

                Jugador playerAux = new Jugador();
                playerAux.setNombre(nombre);
                playerAux.setTiempo(tiempo);
                playerAux.setCorrectas(correctas);
                playerAux.setFallos(fallos);

                jugadores.add(playerAux);
            }
            Log.i("Jugadores",""+jugadores.toString());
        }catch (Exception e){
            Log.i("Database","Creando nueva base de datos");
        }finally {
            if(rankingFileInput != null){
                try {
                    rankingFileInput.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        jugadores.add(player);

        //Escritura en los ranking
        JSONArray arrayAux = new JSONArray();
        JSONObject objAux;

        for (int i = 0; i< jugadores.size(); i++){
            objAux = new JSONObject();
            objAux.put("nombre",jugadores.get(i).getNombre());
            objAux.put("tiempo",jugadores.get(i).getTiempo());
            objAux.put("correctas",jugadores.get(i).getCorrectas());
            objAux.put("fallos",jugadores.get(i).getFallos());
            arrayAux.put(objAux);
        }

        String JSONtext = arrayAux.toString();
        Log.i("JSONText",""+JSONtext);
        FileOutputStream flujoEscritura = null;

        try{
            switch (categoria){
                case "Marvel":
                    flujoEscritura = openFileOutput("RankingMarvel.json",MODE_PRIVATE);
                    break;
                case "DC Comics":
                    flujoEscritura = openFileOutput("RankingDC.json",MODE_PRIVATE);
                    break;
                case "Manga":
                    flujoEscritura = openFileOutput("RankingManga.json",MODE_PRIVATE);
                    break;
                case "Adaptaciones":
                    flujoEscritura = openFileOutput("RankingAdaptaciones.json",MODE_PRIVATE);
                    break;
            }
            flujoEscritura.write(JSONtext.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (flujoEscritura != null){
                try{
                    flujoEscritura.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    //Se realiza una declaración a este método para inhabilitar el botón de atrás de Android
    @Override
    public void onBackPressed() { }

    //Método para hacer recuento de las preguntas correctas
    private void recuentoCorrectas(){
        numCorrectas = 0;
        for (int i = 0; i<respCorrectas.length; i++){
            if (respCorrectas[i]==true){
                numCorrectas++;
            }
        }
    }
}