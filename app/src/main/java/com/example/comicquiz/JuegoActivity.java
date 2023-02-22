package com.example.comicquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//Zona de importaciones de diferentes elementos y funcionalidades de la activity
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.comicquiz.basedatos.gestorPreguntas;
import com.example.comicquiz.clases.Pregunta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JuegoActivity extends AppCompatActivity {

    //Se crean las futuras referencias a los elementos del layout
    private RadioGroup respuestas;
    private Button siguiente;
    private ImageView imagenPregunta;
    private ImageView imageSound;
    private VideoView video;
    private ImageButton respuestaImagen1;
    private ImageButton respuestaImagen2;
    private ImageButton respuestaImagen3;
    private ImageButton respuestaImagen4;
    private ImageButton buttonPlaySound;
    private ImageButton buttonPlayVideo;
    private ImageButton buttonPauseVideo;
    private TextView cabecera;
    private TextView enunciado;
    private TextView textRespImagen1;
    private TextView textRespImagen2;
    private TextView textRespImagen3;
    private TextView textRespImagen4;
    private TextView contCorrectas;
    private TextView contIncorrectas;
    private MediaPlayer sonido;
    private Chronometer cronometro;

    //Se declaran las variables necesarias para gestionar y mostrar las preguntas
    private int idRespuestas[] = {R.id.opcionA, R.id.opcionB, R.id.opcionC, R.id.opcionD};
    private int preguntaActual;
    private ArrayList<Pregunta> preguntasTotales;
    private String guardaCorrecta;
    private int[] respuestasUser;
    private int correcta;
    private boolean[] correctas;
    private int numPreguntas;
    private String categoria;
    private String userName;
    private boolean mostrarCorrecta;
    private boolean haySonido;
    private boolean hayVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Con esto forzamos el modo vertical del móvil
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        //Se recibe el número de preguntas escogidas en la MainActivity
        numPreguntas = getIntent().getIntExtra("NumeroPreguntas",0);

        //Se recibe la categoría escogida para las preguntas
        categoria = getIntent().getStringExtra("CategoriaElegida");

        //Se recibe si queremos mostrar la respuesta correcta al fallar o no
        mostrarCorrecta = getIntent().getBooleanExtra("MostrarCorrecta",false);

        //Se recibe el nombre de usuario
        userName = getIntent().getStringExtra("NombreUsuario");

        //Se referencian los diferentes elementos del layouts
        cabecera = (TextView) findViewById(R.id.textTituloPregunta);
        enunciado = (TextView) findViewById(R.id.textPregunta);
        respuestas = (RadioGroup) findViewById(R.id.opciones);
        siguiente = (Button) findViewById(R.id.buttonSiguiente);
        imagenPregunta = (ImageView) findViewById(R.id.imagenPregunta);
        imageSound = (ImageView) findViewById(R.id.imageSound);
        video = (VideoView) findViewById(R.id.videoPregunta);
        respuestaImagen1 = (ImageButton) findViewById(R.id.r0);
        respuestaImagen2 = (ImageButton) findViewById(R.id.r1);
        respuestaImagen3 = (ImageButton) findViewById(R.id.r2);
        respuestaImagen4 = (ImageButton) findViewById(R.id.r3);
        buttonPlaySound = (ImageButton) findViewById(R.id.buttonPlaySound) ;
        buttonPlayVideo = (ImageButton) findViewById(R.id.buttonPlayVideo);
        buttonPauseVideo = (ImageButton) findViewById(R.id.buttonPauseVideo);
        textRespImagen1 = (TextView) findViewById(R.id.textImagen1);
        textRespImagen2 = (TextView) findViewById(R.id.textImagen2);
        textRespImagen3 = (TextView) findViewById(R.id.textImagen3);
        textRespImagen4 = (TextView) findViewById(R.id.textImagen4);
        contCorrectas = (TextView) findViewById(R.id.correctQCounter);
        contIncorrectas = (TextView) findViewById(R.id.wrongQCounter);
        cronometro = (Chronometer) findViewById(R.id.cronometro);

        cronometro.setBase(SystemClock.elapsedRealtime());
        cronometro.start();

        //Se aplica la recogida de preguntas que va a haber entre el conjunto total
        preguntasTotales = recogidaPreguntas(numPreguntas,categoria);

        //Se inicializa algunos datos importantes de los arrays
        inicializarDatos();

        //Se comienza la muestra de la primera pregunta
        mostrarPregunta();

        //Se gestiona el evento al clickar en el boton de siguiente o en los botones de imagen, en función de si
        //es una pregunta de texto o de imágenes
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarRespuestaTexto();
                if (haySonido){
                    sonido.stop();
                }
                if (hayVideo){
                    video.stopPlayback();
                    video.seekTo(0);
                }
                preguntaActual++;
            }
        });
        respuestaImagen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = 0;
                comprobarRespuestaImagen(id);
                if (haySonido){
                    sonido.stop();
                }
                preguntaActual++;
            }
        });
        respuestaImagen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = 1;
                comprobarRespuestaImagen(id);
                if (haySonido){
                    sonido.stop();
                }
                preguntaActual++;
            }
        });
        respuestaImagen3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = 2;
                comprobarRespuestaImagen(id);
                if (haySonido){
                    sonido.stop();
                }
                preguntaActual++;
            }
        });
        respuestaImagen4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = 3;
                comprobarRespuestaImagen(id);
                if (haySonido){
                    sonido.stop();
                }
                preguntaActual++;
            }
        });
        buttonPlaySound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sonido.start();
            }
        });
        buttonPlayVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                video.start();
            }
        });
        buttonPauseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                video.pause();
            }
        });

    }

    //Se declara el método para inhabilitar el botón de atras de Android
    @Override
    public void onBackPressed() { }

    //Método que inicializa algunas de las variables necesarias en la activity
    private void inicializarDatos() {
        correctas = new boolean[preguntasTotales.size()];
        respuestasUser = new int[preguntasTotales.size()];
        for (int i = 0; i<respuestasUser.length; i++){
            respuestasUser[i] = -1;
        }
        preguntaActual = 0;
    }

    //Método que recoge, de forma aleatoria, las preguntas escogidas por el usuario entre todas
    private ArrayList<Pregunta> recogidaPreguntas(int nPreguntas, String cat){

        gestorPreguntas gestPreguntas = new gestorPreguntas(JuegoActivity.this);
        ArrayList <Pregunta> preguntasAux = gestPreguntas.recogerPreguntaCategoria(cat);
        ArrayList <Pregunta> listaDefinitiva = new ArrayList<>();

        Random generador = new Random();

        List<Integer> listaAux = new ArrayList<>();

        while (listaAux.size()<nPreguntas){
            int nuevoIndice = generador.nextInt(20-0);
            if (listaAux.contains(nuevoIndice) == false){
                listaAux.add(nuevoIndice);
                listaDefinitiva.add(preguntasAux.get(nuevoIndice));
            }
        }
        return listaDefinitiva;
    }

    private void comprobarRespuestaImagen(int id){
        correctas[preguntaActual] = (id == correcta);

        AlertDialog.Builder builderim1 = new AlertDialog.Builder(this);
        if (correctas[preguntaActual] == true){
            builderim1.setMessage(R.string.mensajeCorrecto);
        }else{
            if(mostrarCorrecta){
                builderim1.setMessage(getResources().getString(R.string.mensajeIncorrectoConImagenCorrecta)+(correcta+1));
            }else{
                builderim1.setMessage(R.string.mensajeIncorrecto);
            }

        }
        builderim1.setCancelable(false);
        builderim1.setPositiveButton(R.string.buttonSiguiente, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (preguntaActual < numPreguntas){
                    mostrarPregunta();
                }else{
                    if (haySonido){
                        sonido.stop();
                    }
                    if (hayVideo){
                        video.stopPlayback();
                        video.seekTo(0);
                    }
                    cronometro.stop();
                    String tiempo = cronometro.getText().toString();
                    Intent toRankingActivity = new Intent(getApplicationContext(), ResultadosActivity.class);
                    toRankingActivity.putExtra("TotalCorrectas", correctas);
                    toRankingActivity.putExtra("NombreUsuario", userName);
                    toRankingActivity.putExtra("Categoria", categoria);
                    toRankingActivity.putExtra("TiempoTotal", tiempo);
                    startActivity(toRankingActivity);
                    cronometro.setBase(SystemClock.elapsedRealtime());
                    finish();
                }

            }
        });
        builderim1.create().show();
    }

    //Método que va a comprobar si la elección del jugador es la correcta
    private void comprobarRespuestaTexto(){
        int id = respuestas.getCheckedRadioButtonId();
        int index = -1;
        for (int i = 0; i<idRespuestas.length;i++){
            if(idRespuestas[i] == id){
                index = i;
            }
        }
        correctas[preguntaActual] = (index == correcta);
        respuestasUser[preguntaActual] = index;

        //Si la respuesta es correcta anuncia que es correcta y si es incorrecta indica que no
        AlertDialog.Builder buildertx = new AlertDialog.Builder(this);
        if (correctas[preguntaActual] == true){
            buildertx.setMessage(R.string.mensajeCorrecto);
        }else{
            if(mostrarCorrecta){
                buildertx.setMessage(getResources().getString(R.string.mensajeIncorrectoConCorrecta)+guardaCorrecta+")");
            }else{
                buildertx.setMessage(R.string.mensajeIncorrecto);
            }

        }
        buildertx.setCancelable(false);
        buildertx.setPositiveButton(R.string.buttonSiguiente, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (preguntaActual < numPreguntas){
                    mostrarPregunta();
                }else{
                    if (haySonido){
                        sonido.stop();
                    }
                    if (hayVideo){
                        video.stopPlayback();
                        video.seekTo(0);
                    }
                    cronometro.stop();
                    String tiempo = cronometro.getText().toString();
                    Intent toRankingActivity = new Intent(getApplicationContext(), ResultadosActivity.class);
                    toRankingActivity.putExtra("TotalCorrectas", correctas);
                    toRankingActivity.putExtra("NombreUsuario", userName);
                    toRankingActivity.putExtra("Categoria", categoria);
                    toRankingActivity.putExtra("TiempoTotal", tiempo);
                    startActivity(toRankingActivity);
                    cronometro.setBase(SystemClock.elapsedRealtime());
                    finish();
                }

            }
        });
        buildertx.create().show();
    }

    //Método que permite mostrar por pantalla la pregunta actual
    private void mostrarPregunta(){

        Pregunta pregunta = preguntasTotales.get(preguntaActual);

        cabecera.setText(getResources().getText(R.string.textPregunta)+" "+(preguntaActual+1)+"/"+numPreguntas);
        enunciado.setText(pregunta.getEnunciado());

        int correctasActuales = 0;
        for (int i = 0; i<correctas.length; i++){
            if (correctas[i]==true){
                correctasActuales++;
            }
        }

        contCorrectas.setText(""+correctasActuales);
        contIncorrectas.setText(""+(preguntaActual-correctasActuales));

        if (pregunta.isSonidoPregunta()){
            haySonido = true;
            imageSound.setVisibility(View.VISIBLE);
            buttonPlaySound.setVisibility(View.VISIBLE);
            switch (pregunta.getCodigoSonido()){
                case "sound1":
                    sonido = MediaPlayer.create(this,R.raw.s1_antmanaudio);
                    break;
                case "sound2":
                    sonido = MediaPlayer.create(this,R.raw.s2_batmanaudio);
                    break;
                case "sound3":
                    sonido = MediaPlayer.create(this,R.raw.s3_shingekiaudio);
                    break;
                case "sound4":
                    sonido = MediaPlayer.create(this,R.raw.s4_supermanaudio);
                    break;
                case "sound5":
                    sonido = MediaPlayer.create(this,R.raw.s5_luffyaudio);
                    break;
            }
        }else{
            haySonido = false;
            imageSound.setVisibility(View.GONE);
            buttonPlaySound.setVisibility(View.GONE);
        }

        if (pregunta.isVideoPregunta()){
            hayVideo = true;
            video.setVisibility(View.VISIBLE);
            buttonPlayVideo.setVisibility(View.VISIBLE);
            buttonPauseVideo.setVisibility(View.VISIBLE);
            switch (pregunta.getCodigoVideo()){
                case "video1":
                    String path1 = "android.resource://"+getPackageName()+"/"+R.raw.v1_memejojos;
                    video.setVideoURI(Uri.parse(path1));
                    break;
                case "video2":
                    String path2 = "android.resource://"+getPackageName()+"/"+R.raw.v2_spidermancivilwar;
                    video.setVideoURI(Uri.parse(path2));
                    break;
                case "video3":
                    String path3 = "android.resource://"+getPackageName()+"/"+R.raw.v3_batpod;
                    video.setVideoURI(Uri.parse(path3));
                    break;
                case "video4":
                    String path4 = "android.resource://"+getPackageName()+"/"+R.raw.v4_marthabvs;
                    video.setVideoURI(Uri.parse(path4));
                    break;
                case "video5":
                    String path5 = "android.resource://"+getPackageName()+"/"+R.raw.v5_ironmanjet;
                    video.setVideoURI(Uri.parse(path5));
                    break;
            }
        }else{
            hayVideo = false;
            video.setVisibility(View.GONE);
            buttonPlayVideo.setVisibility(View.GONE);
            buttonPauseVideo.setVisibility(View.GONE);
        }

        if (pregunta.isImagenRespuestas()==false){
            respuestaImagen1.setVisibility(View.GONE);
            respuestaImagen2.setVisibility(View.GONE);
            respuestaImagen3.setVisibility(View.GONE);
            respuestaImagen4.setVisibility(View.GONE);
            textRespImagen1.setVisibility(View.GONE);
            textRespImagen2.setVisibility(View.GONE);
            textRespImagen3.setVisibility(View.GONE);
            textRespImagen4.setVisibility(View.GONE);
            siguiente.setVisibility(View.VISIBLE);
            respuestas.setVisibility(View.VISIBLE);
            respuestas.clearCheck();

            if(pregunta.isImagenEnunciado()==false){
                imagenPregunta.setVisibility(View.GONE);
            }else{
                imagenPregunta.setVisibility(View.VISIBLE);
                switch (pregunta.getCodigoImagenEnunciado()){
                    case "A1":
                        imagenPregunta.setImageResource(R.drawable.hulk);
                        break;
                    case "B1":
                        imagenPregunta.setImageResource(R.drawable.havok);
                        break;
                    case "C1":
                        imagenPregunta.setImageResource(R.drawable.redhulk);
                        break;
                    case "D1":
                        imagenPregunta.setImageResource(R.drawable.cmarvel_logo);
                        break;
                    case "E1":
                        imagenPregunta.setImageResource(R.drawable.novahelmet);
                        break;
                    case "F1":
                        imagenPregunta.setImageResource(R.drawable.robin);
                        break;
                    case "G1":
                        imagenPregunta.setImageResource(R.drawable.f1_superman);
                        break;
                    case "H1":
                        imagenPregunta.setImageResource(R.drawable.alfred);
                        break;
                    case "I1":
                        imagenPregunta.setImageResource(R.drawable.lafosa);
                        break;
                    case "J1":
                        imagenPregunta.setImageResource(R.drawable.blackmanta);
                        break;
                    case "K1":
                        imagenPregunta.setImageResource(R.drawable.saitama);
                        break;
                    case "M1":
                        imagenPregunta.setImageResource(R.drawable.tomioka);
                        break;
                    case "N1":
                        imagenPregunta.setImageResource(R.drawable.shiganshina);
                        break;
                    case "O1":
                        imagenPregunta.setImageResource(R.drawable.kenshiro);
                        break;
                    case "P1":
                        imagenPregunta.setImageResource(R.drawable.kero);
                        break;
                    case "R1":
                        imagenPregunta.setImageResource(R.drawable.markv);
                        break;
                    case "S1":
                        imagenPregunta.setImageResource(R.drawable.andrewlincoln);
                        break;
                }
            }

            for (int i = 0; i < idRespuestas.length; i++){
                RadioButton radioAux = (RadioButton) findViewById(idRespuestas[i]);
                String res = null;

                correcta = pregunta.getCorrecta();

                switch (i){
                    case 0:
                        res = pregunta.getRespuesta1();
                        if (correcta == 0){
                            guardaCorrecta = res;
                        }
                        break;
                    case 1:
                        res = pregunta.getRespuesta2();
                        if (correcta == 1){
                            guardaCorrecta = res;
                        }
                        break;
                    case 2:
                        res = pregunta.getRespuesta3();
                        if (correcta == 2){
                            guardaCorrecta = res;
                        }
                        break;
                    case 3:
                        res = pregunta.getRespuesta4();
                        if (correcta == 3){
                            guardaCorrecta = res;
                        }
                        break;
                }

                radioAux.setText(res);
            }
        }else{
            respuestas.setVisibility(View.GONE);
            imagenPregunta.setVisibility(View.GONE);
            siguiente.setVisibility(View.GONE);
            respuestaImagen1.setVisibility(View.VISIBLE);
            respuestaImagen2.setVisibility(View.VISIBLE);
            respuestaImagen3.setVisibility(View.VISIBLE);
            respuestaImagen4.setVisibility(View.VISIBLE);
            textRespImagen1.setVisibility(View.VISIBLE);
            textRespImagen2.setVisibility(View.VISIBLE);
            textRespImagen3.setVisibility(View.VISIBLE);
            textRespImagen4.setVisibility(View.VISIBLE);

            for (int i = 0; i < idRespuestas.length; i++){

                correcta = pregunta.getCorrecta();

                switch (pregunta.getCodigoImagenRespuesta1().charAt(0)){
                    case 'a':
                        respuestaImagen1.setImageResource(R.drawable.a1c3_capitanamerica);
                        respuestaImagen2.setImageResource(R.drawable.a2c1_ironman);
                        respuestaImagen3.setImageResource(R.drawable.a3_hulk);
                        respuestaImagen4.setImageResource(R.drawable.a4_antman);
                        break;
                    case 'b':
                        respuestaImagen1.setImageResource(R.drawable.b1_xmen);
                        respuestaImagen2.setImageResource(R.drawable.b2_inhumans);
                        respuestaImagen3.setImageResource(R.drawable.b3_vengadores);
                        respuestaImagen4.setImageResource(R.drawable.b4_fantastic4);
                        break;
                    case 'c':
                        respuestaImagen1.setImageResource(R.drawable.a2c1_ironman);
                        respuestaImagen2.setImageResource(R.drawable.c2_avispa);
                        respuestaImagen3.setImageResource(R.drawable.a1c3_capitanamerica);
                        respuestaImagen4.setImageResource(R.drawable.c4_thor);
                        break;
                    case 'd':
                        respuestaImagen1.setImageResource(R.drawable.d1_trajesimbionte);
                        respuestaImagen2.setImageResource(R.drawable.d2_ironspider);
                        respuestaImagen3.setImageResource(R.drawable.d3_trajeblanco);
                        respuestaImagen4.setImageResource(R.drawable.d4_trajeclasico);
                        break;
                    case 'e':
                        respuestaImagen1.setImageResource(R.drawable.e1_defenders);
                        respuestaImagen2.setImageResource(R.drawable.e2_newavengers);
                        respuestaImagen3.setImageResource(R.drawable.e3_secretavengers);
                        respuestaImagen4.setImageResource(R.drawable.e4_originaldefenders);
                        break;
                    case 'f':
                        respuestaImagen1.setImageResource(R.drawable.f1_superman);
                        respuestaImagen2.setImageResource(R.drawable.f2_flash);
                        respuestaImagen3.setImageResource(R.drawable.f3_batman);
                        respuestaImagen4.setImageResource(R.drawable.f4_wonderwoman);
                        break;
                    case 'g':
                        respuestaImagen1.setImageResource(R.drawable.g1_supermanprime);
                        respuestaImagen2.setImageResource(R.drawable.g2_arkillo);
                        respuestaImagen3.setImageResource(R.drawable.g3_lyssadark);
                        respuestaImagen4.setImageResource(R.drawable.g4_siniestro);
                        break;
                    case 'h':
                        respuestaImagen1.setImageResource(R.drawable.h1_espectro);
                        respuestaImagen2.setImageResource(R.drawable.h2_wilfcat);
                        respuestaImagen3.setImageResource(R.drawable.h3_hourman);
                        respuestaImagen4.setImageResource(R.drawable.h4_drfate);
                        break;
                    case 'i':
                        respuestaImagen1.setImageResource(R.drawable.i1_redlanterns);
                        respuestaImagen2.setImageResource(R.drawable.i2_greenlanterns);
                        respuestaImagen3.setImageResource(R.drawable.i3_orangelanterns);
                        respuestaImagen4.setImageResource(R.drawable.i4_yellowlanterns);
                        break;
                    case 'j':
                        respuestaImagen1.setImageResource(R.drawable.j1_amoespejos);
                        respuestaImagen2.setImageResource(R.drawable.j2_capitanfrio);
                        respuestaImagen3.setImageResource(R.drawable.j3_flautista);
                        respuestaImagen4.setImageResource(R.drawable.j4_heatwave);
                        break;
                    case 'k':
                        respuestaImagen1.setImageResource(R.drawable.k1_onepiece);
                        respuestaImagen2.setImageResource(R.drawable.k2_dragonball);
                        respuestaImagen3.setImageResource(R.drawable.k3_shingeki);
                        respuestaImagen4.setImageResource(R.drawable.k4_naruto);
                        break;
                    case 'm':
                        respuestaImagen1.setImageResource(R.drawable.m1_kochikame);
                        respuestaImagen2.setImageResource(R.drawable.m2_kinnikumman);
                        respuestaImagen3.setImageResource(R.drawable.m3_yuyuhakusho);
                        respuestaImagen4.setImageResource(R.drawable.m4_princetennis);
                        break;
                    case 'n':
                        respuestaImagen1.setImageResource(R.drawable.n1_honjo);
                        respuestaImagen2.setImageResource(R.drawable.n2_reira);
                        respuestaImagen3.setImageResource(R.drawable.n3_nana);
                        respuestaImagen4.setImageResource(R.drawable.n4_takumi);
                        break;
                    case 'o':
                        respuestaImagen1.setImageResource(R.drawable.o1_kimblee);
                        respuestaImagen2.setImageResource(R.drawable.o2_scar);
                        respuestaImagen3.setImageResource(R.drawable.o3_mustang);
                        respuestaImagen4.setImageResource(R.drawable.o4_edward);
                        break;
                    case 'p':
                        respuestaImagen1.setImageResource(R.drawable.p1_jonathanjoestar);
                        respuestaImagen2.setImageResource(R.drawable.p2_giorno);
                        respuestaImagen3.setImageResource(R.drawable.p3_josephjoestar);
                        respuestaImagen4.setImageResource(R.drawable.p4_josuke);
                        break;
                    case 'r':
                        respuestaImagen1.setImageResource(R.drawable.r1_valkilmer);
                        respuestaImagen2.setImageResource(R.drawable.r2_michaelkeaton);
                        respuestaImagen3.setImageResource(R.drawable.r3_johnhamm);
                        respuestaImagen4.setImageResource(R.drawable.r4_georgeclooney);
                        break;
                    case 's':
                        respuestaImagen1.setImageResource(R.drawable.s1_chalamet);
                        respuestaImagen2.setImageResource(R.drawable.s2_chanriggs);
                        respuestaImagen3.setImageResource(R.drawable.s3_davidmazouz);
                        respuestaImagen4.setImageResource(R.drawable.s4_steveyeun);
                        break;
                    case 't':
                        respuestaImagen1.setImageResource(R.drawable.t1_mindstone);
                        respuestaImagen2.setImageResource(R.drawable.t2_spacestone);
                        respuestaImagen3.setImageResource(R.drawable.t3_soulstone);
                        respuestaImagen4.setImageResource(R.drawable.t4_powerstone);
                        break;
                }

            }
        }
    }
}