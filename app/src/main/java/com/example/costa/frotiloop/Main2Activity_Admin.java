package com.example.costa.frotiloop;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity_Admin extends AppCompatActivity {

    private MediaPlayer mpRank;
    private TextView tv_panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Esto es para colocar el activity solo en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main2__admin);

        tv_panel=(TextView)findViewById(R.id.textView_admin);

        Typeface fuente = Typeface.createFromAsset(getAssets(), "fuentes/patito.ttf");
        tv_panel.setTypeface(fuente);

        //icono para el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        mpRank=MediaPlayer.create(this, R.raw.levels_e);
        mpRank.start();
        //con este metodo hacemos que se repita una y otra vez
        mpRank.setLooping(true);

    }

    public void Modificar(View view) {
        Intent modificacion=new Intent(this, Main2Activity_Modificar.class);
        startActivity(modificacion);
    }

    public void Eliminar(View view) {
        Intent eliminacion =new Intent(this, Main2Activity_Eliminar.class);
        startActivity(eliminacion);
    }

    public void Salir(View view) {
        Intent menuPrin = new Intent(this, MainActivity.class);
        startActivity(menuPrin);
        mpRank.stop();
        mpRank.release();
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
