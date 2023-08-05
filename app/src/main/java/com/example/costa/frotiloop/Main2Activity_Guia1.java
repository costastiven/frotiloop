package com.example.costa.frotiloop;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity_Guia1 extends AppCompatActivity {

    //array de backgrounds
    int backId[] = {R.drawable.guia_000, R.drawable.guia_002,
            R.drawable.guia_003, R.drawable.guia_004, R.drawable.guia_005,
            R.drawable.guia_006, R.drawable.guia_007, R.drawable.guia_008,
            R.drawable.guia_009, R.drawable.guia_010, R.drawable.guia_011,
            R.drawable.guia_011_1, R.drawable.guia_012, R.drawable.guia_013,
            R.drawable.guia_014, R.drawable.guia_015};

    //contador
    int i = 0;

    //total de imagenes
    int total;

    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Esto es para colocar el activity solo en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main2__guia1);
        layout = findViewById(R.id.rela_lay);

        //icono para el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //anterior = findViewById(R.id.btn_anterior);

        //total de imagenes
        total = backId.length;

    }

    //metodo para mostrar el menu del juego con lapiz incluido
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.guia_contenido, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_salir) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void Anterior(View view) {
        int id = view.getId();
        if (id == R.id.btn_anterior) {
            i--;
            if (i == -1) {
                toastIncorrecto("Primero debes avanzar", 4000);
                i = 0;
                return;
            } else if (i == 0) {
                i = total - 1;
            }
        }
        layout.setBackgroundResource(backId[i]);
    }

    ///QUIEEEEEEDOOOOO FUNCIONANDO LA GUIA
    public void Siguiente(View view) {
        int id = view.getId();
        if (id == R.id.btn_siguiente) {
            i++;
            if (i == total) {
                finish();
                return;
            }
        }
        layout.setBackgroundResource(backId[i]);
    }

    public void toastCorrecto(String msg, int duracion) {
        LayoutInflater inflater_lay = getLayoutInflater();
        View view_lay = inflater_lay.inflate(R.layout.custom_toast_ok, (ViewGroup) findViewById(R.id.toast_ok));
        TextView txt_toast = view_lay.findViewById(R.id.text_toast_ok);
        txt_toast.setText(msg);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, (int) getResources().getDimension(R.dimen.toast_margin));
        toast.setDuration(duracion);
        toast.setView(view_lay);
        toast.show();
    }

    public void toastIncorrecto(String msg, int duracion) {
        LayoutInflater inflater_lay = getLayoutInflater();
        View view_lay = inflater_lay.inflate(R.layout.custom_toast_error, (ViewGroup) findViewById(R.id.toast_error));
        TextView txt_toast = view_lay.findViewById(R.id.text_toast_error);
        txt_toast.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, (int) getResources().getDimension(R.dimen.toast_margin));
        toast.setDuration(duracion);
        toast.setView(view_lay);
        toast.show();
    }

    //Ahora vamos a crear un metodo para que el usuario no pueda regresar para atras
    //porque una vez iniciado el juego no se debe volver hacia atras
    @Override
    public void onBackPressed() {

    }
}
