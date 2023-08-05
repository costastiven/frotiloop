package com.example.costa.frotiloop;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity_Guia0 extends AppCompatActivity {

    public MediaPlayer mp;
    public Button btn_contenido, btn_actividades, btn_salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Esto es para colocar el activity solo en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main2__guia0);

        btn_contenido=(Button)findViewById(R.id.btn_contenido);
        btn_actividades=(Button)findViewById(R.id.btn_actividades);
        btn_salir=(Button)findViewById(R.id.btn_salir_guia);

        mp = MediaPlayer.create(this, R.raw.levels_e);
        mp.start();
        mp.setLooping(true);

        //icono para el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        Typeface fuente = Typeface.createFromAsset(getAssets(), "fuentes/patito.ttf");
        btn_contenido.setTypeface(fuente);
        btn_actividades.setTypeface(fuente);
        btn_salir.setTypeface(fuente);
    }

    public void Contenido(View view){
        Intent contenido = new Intent(this, Main2Activity_Guia1.class);
        startActivity(contenido);
        //mp.stop();
        //mp.release();
        //finish();
    }

    public void Actividades(View view){
        Intent actividades = new Intent(this, Main2Activity_Guia2.class);
        startActivity(actividades);
        //mp.stop();
        //mp.release();
        //finish();
    }

    public void salir_guia(View view){
        Intent salir = new Intent(this, MainActivity.class);
        startActivity(salir);
        mp.stop();
        mp.release();
        finish();
    }
}
