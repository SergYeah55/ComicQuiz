package com.example.comicquiz.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {

    public static int databaseVersion = 1;
    private static final String databaseName = "listaPreguntas.basedatos";
    public static final String tablePreguntas = "tabla_preguntas";

    public dbHelper(@Nullable Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ tablePreguntas + "("+
                "categoria TEXT,"+
                "numero INTEGER,"+
                "imagenEnunciado INTEGER,"+
                "imagenRespuestas INTEGER,"+
                "sonidoPregunta INTEGER,"+
                "videoPregunta INTEGER,"+
                "enunciado TEXT NOT NULL,"+
                "respuesta1 TEXT NOT NULL,"+
                "respuesta2 TEXT NOT NULL,"+
                "respuesta3 TEXT NOT NULL,"+
                "respuesta4 TEXT NOT NULL," +
                "correcta INTEGER,"+
                "codImagenEn TEXT,"+
                "codImagenRes1 TEXT,"+
                "codImagenRes2 TEXT,"+
                "codImagenRes3 TEXT,"+
                "codImagenRes4 TEXT,"+
                "codigoSonido TEXT,"+
                "codigoVideo TEXT"+
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //databaseVersion = newVersion;
        sqLiteDatabase.execSQL("DROP TABLE " + tablePreguntas);
        onCreate(sqLiteDatabase);
    }
}
