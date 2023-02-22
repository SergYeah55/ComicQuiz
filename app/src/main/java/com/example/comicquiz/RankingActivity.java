package com.example.comicquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.comicquiz.clases.CompararJugadores;
import com.example.comicquiz.clases.Jugador;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class RankingActivity extends AppCompatActivity {

    private Spinner spinnerCategorias;
    private Button buttonMainMenu;
    private TextView rankingNombres;
    private TextView rankingAciertos;
    private TextView rankingTiempo;
    private TextView titleCategoria;

    private String categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        rankingNombres = (TextView) findViewById(R.id.rankingNombres);
        rankingAciertos = (TextView) findViewById(R.id.rankingAciertos);
        rankingTiempo = (TextView) findViewById(R.id.rankingTiempo);
        titleCategoria = (TextView) findViewById(R.id.titleCategoria);

        initSpinner();

        categoria = getIntent().getStringExtra("Categoria");
        Log.i("Categoría", categoria+"");

        if(categoria == null){
            titleCategoria.setVisibility(View.GONE);
            spinnerCategorias.setVisibility(View.VISIBLE);
            spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    categoria = spinnerCategorias.getSelectedItem().toString();
                    mostrarRanking(categoria);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }else{
            titleCategoria.setVisibility(View.VISIBLE);
            spinnerCategorias.setVisibility(View.GONE);
            titleCategoria.setText(categoria);
            mostrarRanking(categoria);
        }

        buttonMainMenu = (Button) findViewById(R.id.buttonRankingMenu);
        buttonMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMenuActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(toMenuActivity);
                finish();
            }
        });
    }

    private void mostrarRanking(String cat){

        ArrayList<Jugador> jugadores = new ArrayList<>();

        FileInputStream rankingFileInput = null;
        BufferedReader lectorAux = null;
        try{
            switch (cat){
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

        Collections.sort(jugadores, new CompararJugadores());

        String textoNombres = "";
        String textoAciertos = "";
        String textoTiempo = "";
        if (jugadores.size()>=5){
            for (int i = 0; i< 5; i++){
                textoNombres = textoNombres + jugadores.get(i).getNombre()+"\n";
                textoAciertos = textoAciertos + jugadores.get(i).getCorrectas()+"\n";
                textoTiempo = textoTiempo + jugadores.get(i).getTiempo()+"\n";
            }
        }else{
            for (int i = 0; i< jugadores.size(); i++){
                textoNombres = textoNombres + jugadores.get(i).getNombre()+"\n";
                textoAciertos = textoAciertos + jugadores.get(i).getCorrectas()+"\n";
                textoTiempo = textoTiempo + jugadores.get(i).getTiempo()+"\n";
            }
        }
        rankingNombres.setText(textoNombres);
        rankingAciertos.setText(textoAciertos);
        rankingTiempo.setText(textoTiempo);
    }

    @Override
    public void onBackPressed() {
        Intent toMenuActivity = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(toMenuActivity);
        finish();
    }

    private void initSpinner() {
        //Creación e inicialización del spinner que permite elegir la categoría
        spinnerCategorias = (Spinner)findViewById(R.id.spinnerCategoriasRanking);

        //Establece las posibles categorías escogibles
        String[] categorias = {"Marvel", "DC Comics", "Manga", "Adaptaciones"};

        //Se elabora el adaptador para poner el spinner por pantalla
        ArrayAdapter auxCategorias = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias);

        auxCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategorias.setAdapter(auxCategorias);
    }
}