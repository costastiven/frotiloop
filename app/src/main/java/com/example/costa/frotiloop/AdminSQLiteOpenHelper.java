package com.example.costa.frotiloop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BD) {
        //BD.execSQL("create table puntaje(id int PRIMARY KEY AUTOINCREMENT, nombre text, score int)");
        BD.execSQL("CREATE TABLE puntaje(codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "score INTEGER," +
                "completado INTEGER)");

        BD.execSQL("CREATE TABLE AciertosFallos(codigo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "aciertos1 INTEGER, fallos1 INTEGER, " +
                "aciertos2 INTEGER, fallos2 INTEGER, " +
                "aciertos3 INTEGER, fallos3 INTEGER, " +
                "aciertos4 INTEGER, fallos4 INTEGER)");

        BD.execSQL("CREATE TABLE meses(codigo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "meses1 INTEGER)");

        BD.execSQL("INSERT INTO AciertosFallos (aciertos1, aciertos2, aciertos3, aciertos4) VALUES(0,0,0,0);");
        BD.execSQL("INSERT INTO AciertosFallos (fallos1, fallos2, fallos3, fallos4) VALUES(0,0,0,0);");
        BD.execSQL("INSERT INTO meses (meses1) VALUES (0);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
