package com.example.comicquiz.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.comicquiz.clases.Pregunta;

import java.util.ArrayList;

public class gestorPreguntas extends dbHelper{

    Context contexto;

    public gestorPreguntas(@Nullable Context context) {
        super(context);
        this.contexto = context;
    }

    public long insertarPregunta (String categoria, int numero, int imagenEn,
                                  int imagenesRes, int sonidoPreg, int videoPreg,
                                  String enunciado, String res1, String res2, String res3,
                                  String res4, int correcta, String codEn, String codRes1,
                                  String codRes2, String codRes3, String codRes4, String codSon,
                                  String codVid){

        long id = 0;

        try{
            dbHelper auxDB = new dbHelper(contexto);
            SQLiteDatabase refDB = auxDB.getReadableDatabase();

            ContentValues valores = new ContentValues();
            valores.put("categoria", categoria);
            valores.put("numero", numero);
            valores.put("imagenEnunciado", imagenEn);
            valores.put("imagenRespuestas", imagenesRes);
            valores.put("sonidoPregunta", sonidoPreg);
            valores.put("videoPregunta", videoPreg);
            valores.put("enunciado", enunciado);
            valores.put("respuesta1", res1);
            valores.put("respuesta2", res2);
            valores.put("respuesta3", res3);
            valores.put("respuesta4", res4);
            valores.put("correcta", correcta);
            valores.put("codImagenEn", codEn);
            valores.put("codImagenRes1", codRes1);
            valores.put("codImagenRes2", codRes2);
            valores.put("codImagenRes3", codRes3);
            valores.put("codImagenRes4", codRes4);
            valores.put("codigoSonido", codSon);
            valores.put("codigoVideo", codVid);

            id = refDB.insert(tablePreguntas, null, valores);
        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public boolean convertToBoolean (Cursor cursorP, int i){
        int auxToBool = cursorP.getInt(i);
        if (auxToBool == 0){
            return false;
        }else{
            return true;
        }
    }

    public ArrayList<Pregunta> recogerPregunta(){
        dbHelper help = new dbHelper(contexto);
        SQLiteDatabase db = help.getWritableDatabase();

        ArrayList<Pregunta> listaPreguntas = new ArrayList<>();

        Pregunta preg = null;
        Cursor cursorPreguntas = null;

        cursorPreguntas = db.rawQuery("SELECT * FROM " + tablePreguntas, null);

        if (cursorPreguntas.moveToFirst()){

            do{

                preg = new Pregunta();
                preg.setCategoria(cursorPreguntas.getString(0));
                preg.setNumero(cursorPreguntas.getInt(1));
                preg.setImagenEnunciado(convertToBoolean(cursorPreguntas,2));
                preg.setImagenRespuestas(convertToBoolean(cursorPreguntas,3));
                preg.setSonidoPregunta(convertToBoolean(cursorPreguntas,4));
                preg.setVideoPregunta(convertToBoolean(cursorPreguntas,5));
                preg.setEnunciado(cursorPreguntas.getString(6));
                preg.setRespuesta1(cursorPreguntas.getString(7));
                preg.setRespuesta2(cursorPreguntas.getString(8));
                preg.setRespuesta3(cursorPreguntas.getString(9));
                preg.setRespuesta4(cursorPreguntas.getString(10));
                preg.setCorrecta(cursorPreguntas.getInt(11));
                preg.setCodigoImagenEnunciado(cursorPreguntas.getString(12));
                preg.setCodigoImagenRespuesta1(cursorPreguntas.getString(13));
                preg.setCodigoImagenRespuesta2(cursorPreguntas.getString(14));
                preg.setCodigoImagenRespuesta3(cursorPreguntas.getString(15));
                preg.setCodigoImagenRespuesta4(cursorPreguntas.getString(16));
                preg.setCodigoSonido(cursorPreguntas.getString(17));
                preg.setCodigoVideo(cursorPreguntas.getString(18));

                listaPreguntas.add(preg);

            } while (cursorPreguntas.moveToNext());

        }

        cursorPreguntas.close();

        return listaPreguntas;
    }

    public ArrayList<Pregunta> recogerPreguntaCategoria(String categoriaElegida){
        dbHelper help = new dbHelper(contexto);
        SQLiteDatabase db = help.getWritableDatabase();

        ArrayList<Pregunta> listaPreguntas = new ArrayList<>();

        Pregunta preg = null;
        Cursor cursorPreguntas = null;

        cursorPreguntas = db.rawQuery("SELECT * FROM " + tablePreguntas + " WHERE categoria = '" + categoriaElegida + "'", null);


        if (cursorPreguntas.moveToFirst()){

            do{

                preg = new Pregunta();
                preg.setCategoria(cursorPreguntas.getString(0));
                preg.setNumero(cursorPreguntas.getInt(1));
                preg.setImagenEnunciado(convertToBoolean(cursorPreguntas,2));
                preg.setImagenRespuestas(convertToBoolean(cursorPreguntas,3));
                preg.setSonidoPregunta(convertToBoolean(cursorPreguntas,4));
                preg.setVideoPregunta(convertToBoolean(cursorPreguntas,5));
                preg.setEnunciado(cursorPreguntas.getString(6));
                preg.setRespuesta1(cursorPreguntas.getString(7));
                preg.setRespuesta2(cursorPreguntas.getString(8));
                preg.setRespuesta3(cursorPreguntas.getString(9));
                preg.setRespuesta4(cursorPreguntas.getString(10));
                preg.setCorrecta(cursorPreguntas.getInt(11));
                preg.setCodigoImagenEnunciado(cursorPreguntas.getString(12));
                preg.setCodigoImagenRespuesta1(cursorPreguntas.getString(13));
                preg.setCodigoImagenRespuesta2(cursorPreguntas.getString(14));
                preg.setCodigoImagenRespuesta3(cursorPreguntas.getString(15));
                preg.setCodigoImagenRespuesta4(cursorPreguntas.getString(16));
                preg.setCodigoSonido(cursorPreguntas.getString(17));
                preg.setCodigoVideo(cursorPreguntas.getString(18));

                listaPreguntas.add(preg);

            } while (cursorPreguntas.moveToNext());

        }

        cursorPreguntas.close();

        return listaPreguntas;
    }

}
