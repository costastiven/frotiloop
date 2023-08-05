package com.example.costa.frotiloop;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Main2Activity_Ranking extends AppCompatActivity {

    private TextView tv1, tv2, txt_lista_rank;
    private ListView lv1;
    private MediaPlayer mpRank;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Esto es para colocar el activity solo en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main2__ranking);

        tv1 = (TextView) findViewById(R.id.textView_Rank);
        tv2 = (TextView) findViewById(R.id.textView_jugadores);
        lv1 = (ListView) findViewById(R.id.lv1);
        txt_lista_rank = (TextView) findViewById(R.id.txt_lista_ranking);

        Typeface fuente = Typeface.createFromAsset(getAssets(), "fuentes/patito.ttf");
        tv1.setTypeface(fuente);
        tv2.setTypeface(fuente);
        //icono a la izquierda y derecha
        tv1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.medalla_h, 0, 0, 0);

        cargarListViewCustom();

        //icono para el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

    }

    private void cargarListView() {
        ArrayList<String> lista = new ArrayList<String>();
        AdminSQLiteOpenHelper admin1 = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase db = admin1.getReadableDatabase();
        Cursor agregar = db.rawQuery("SELECT nombre, score, completado FROM puntaje ORDER BY score DESC LIMIT 10", null);
        int id = 0;
        while (agregar.moveToNext()) {
            id++;
            lista.add(" " + id + " ) " + agregar.getString(0) + "  " + agregar.getString(1) + " " + agregar.getString(2));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_ranking, lista);
        lv1.setAdapter(adapter);
        db.close();
        admin1.close();
    }

    public void cargarListViewCustom() {
        ArrayList<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
        AdminSQLiteOpenHelper admin1 = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase db = admin1.getReadableDatabase();
        Cursor agregar = db.rawQuery("SELECT nombre, score, completado FROM puntaje ORDER BY score DESC LIMIT 10", null);
        int id = 0;
        while (agregar.moveToNext()) {
            id++;
            String nombre = agregar.getString(0);
            String score = agregar.getString(1);
            int valor = agregar.getInt(2);
            int icono = valor == 1 ? R.drawable.a_completado : R.drawable.a_rank2;
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put("icono", icono);
            item.put("texto1", " " + id + " ) " + nombre+ " " + score);
            lista.add(item);
        }
        String[] de = {"icono", "texto1"};
        int[] para = {R.id.icono_ranking, R.id.text1_custom};
        SimpleAdapter adaptador = new SimpleAdapter(this, lista, R.layout.ranking_custom, de, para);
        lv1.setAdapter(adaptador);
        db.close();
        admin1.close();
    }


    public void cerrarRank(View view) {
        onBackPressed();
        finish();
    }

    //Ahora vamos a crear un metodo para que el usuario no pueda regresar para atras
    //porque una vez iniciado el juego no se debe volver hacia atras
    @Override
    public void onBackPressed() {

    }
}
