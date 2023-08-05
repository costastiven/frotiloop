package com.example.costa.frotiloop;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class Main2Activity_ModificarSonidos extends AppCompatActivity {

    private AudioManager am;

    public SeekBar seek_bar_sound;

    public TextView txt_sonido;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Esto es para colocar el activity solo en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main2__modificar_sonidos);

        //colocar el icono del juego en el actionBar del nivel
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        txt_sonido=(TextView)findViewById(R.id.txt_seek_sonido);
        seek_bar_sound=(SeekBar)findViewById(R.id.seekBar_Sonido);

        Typeface fuente_txt = Typeface.createFromAsset(getAssets(), "fuentes/patito.ttf");
        txt_sonido.setTypeface(fuente_txt);

        am=(AudioManager)getSystemService(AUDIO_SERVICE);
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        int audioVolume = sharedPreferences.getInt("progreso", 5);
        txt_sonido.setText(String.valueOf(audioVolume));

        seek_bar_sound.setProgress(audioVolume);
        seek_bar_sound.setMax(100);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, audioVolume, 0);

        seek_bar_sound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                txt_sonido.setText(String.valueOf(progress));
                editor.putInt("progreso", progress);
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    //public void Encender(View view){
    //    am.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
    //}

    //public void Apagar(View view){
    //    am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
    //}

    public void Salir(View view){
        finish();
    }

}
