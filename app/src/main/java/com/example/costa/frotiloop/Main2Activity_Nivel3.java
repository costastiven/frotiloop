package com.example.costa.frotiloop;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Path;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;

import static com.example.costa.frotiloop.Lienzo.drawPaint;
import static com.example.costa.frotiloop.Lienzo.drawPath;
import static com.example.costa.frotiloop.Lienzo.estado_pincel;
import static com.example.costa.frotiloop.Lienzo.goma_por_defecto;
import static com.example.costa.frotiloop.Lienzo.lapiz_por_defecto;
import static com.example.costa.frotiloop.Lienzo.pintar;
import static com.example.costa.frotiloop.Lienzo.goma;

public class Main2Activity_Nivel3 extends AppCompatActivity {

    private TextView tv_nombre, tv_extra, tv_score, tv_numUno, tv_numDos, tv_raya;
    private ImageView iv_vidas;
    private EditText et_respuesta;
    private MediaPlayer mp;
    //Dibujar
    int defaultcolor;
    Lienzo panel_dibujo;
    public static float grosor_pincel = 0;
    public MenuItem iconoDesactivar;

    private SoundPool mp_bien, mp_mal, game_over;
    int sonido_bien, sonido_mal, sonido_over;

    int score, numAleatorio_uno, numAleatorio_dos, resultado, vidas = 3;
    String nombre_jugador, rol_jugador, enviar_rol, string_score, string_vidas;

    int aciertos3, fallos3;

    String enviar_puntos_extra, recibir_puntos_extra;
    int contador_puntos_extra = 0;
    int conteo_extra_puntos = 0;

    int grosor_lapiz = 4;
    int grosor_goma = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Esto es para colocar el activity solo en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main2__nivel3);

        dialogo_inicio_nivel();

        tv_nombre = (TextView) findViewById(R.id.textView_nombre);
        tv_extra = (TextView) findViewById(R.id.tv_Puntos_Extra);
        tv_score = (TextView) findViewById(R.id.textView_score);
        iv_vidas = (ImageView) findViewById(R.id.imageView_vidas);
        et_respuesta = (EditText) findViewById(R.id.editText_resultado);
        tv_numUno = (TextView) findViewById(R.id.textView_numUno);
        tv_numDos = (TextView) findViewById(R.id.textView_numDos);
        tv_raya = (TextView) findViewById(R.id.textView_raya);
        //Objeto lienzo para poder hacer dibujos
        panel_dibujo = (Lienzo) findViewById(R.id.lienzo_dibujo_act);
        defaultcolor = drawPaint.getColor();

        Typeface fuente = Typeface.createFromAsset(getAssets(), "fuentes/boxi_bold.otf");
        tv_nombre.setTypeface(fuente);
        tv_extra.setTypeface(fuente);
        tv_score.setTypeface(fuente);

        //colocar el nombre del jugador en el nivel previamene recuperado del activity principal
        nombre_jugador = getIntent().getStringExtra("jugador");
        rol_jugador = getIntent().getStringExtra("rol");
        tv_nombre.setText("Jugador: " + nombre_jugador);

        tv_extra.setText("Puntos Extra: " + contador_puntos_extra);

        //colocar el icono del juego en el actionBar del nivel
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //colocar cancion en el nivel
        mp = MediaPlayer.create(this, R.raw.level_experto);
        mp.start();
        mp.setLooping(true);

        //colocar los audios representativos a las respuestas correctas e incorrectas
        mp_bien = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        sonido_bien = mp_bien.load(this, R.raw.correcto_a, 1);
        mp_mal = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        sonido_mal = mp_mal.load(this, R.raw.incorrecto_a, 1);

        game_over = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        sonido_over = game_over.load(this, R.raw.audio_game_over, 1);

        NumAleatorio();//con esto hacemos que al arrancar el nivel 1 ya se ejecuten las imagenes aleatorias
    }

    //metodo para mostrar el menu del juego con lapiz incluido
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow_juego, menu);
        iconoDesactivar = menu.findItem(R.id.item_desactivar);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_colores) {
            openDialog(true);
        } else if (id == R.id.item_desactivar) {
            Lienzo.setBorrado(false);
            pintar = 0;
            goma = 0;
            iconoDesactivar.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.a_des_pin));
        } else if (id == R.id.item_grosor) {
            Lapiz_Grosor();
        } else if (id == R.id.item_goma) {
            goma_grosor();
        } else if (id == R.id.i_borrar_todo) {
            Confirmacion_borrado();
        } else if (id == R.id.item_calculadora) {
            Lienzo.setBorrado(false);
            goma = 0;
            pintar = 0;
            iconoDesactivar.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.a_des_pin));
            Intent calculadora = new Intent(this, Main2Activity_Calculadora.class);
            startActivity(calculadora);
        }
        return super.onOptionsItemSelected(item);
    }

    public void Comparar(View view) {//con este metodo vamos a comparar los resultados del usuario si son correctos o incorrectos
        String respuesta = et_respuesta.getText().toString();
        //ahora vamos a validar si la respuesta del usuario es correcta o incorrecta

        if (!respuesta.equals("")) {//con esto decimos que si el campo no esta vacio entra por aca

            int respuesta_jugador = Integer.parseInt(respuesta);//con esto convertimos la respuesta de String a int
            if (resultado == respuesta_jugador)//si el resultado del ususario es correcto entra por aca
            {
                panel_dibujo.borrar_Completo();//con esto borramos el dibujo y empezamos uno nuevo
                mp_bien.play(sonido_bien, 1, 1, 1, 0, 0);
                score++;//con esto incrementamos el score en uno por cada respuesta correcta
                tv_score.setText("Score: " + score);
                et_respuesta.setText("");//con esto limpiamos el campo respuesta

                aciertos3++;
                conteo_extra_puntos++;//Con esto vamos contando la cantida de puntos extra
                if (conteo_extra_puntos == 5) {
                    dialogo_puntos_extra();
                    contador_puntos_extra = contador_puntos_extra + 10;
                    //Importante declara el tvextra aqui
                    tv_extra.setText("Puntos Extra: " + contador_puntos_extra);
                    conteo_extra_puntos = 0;
                }


            } else//si el resultado del ususario es Incorrecto entra por aca
            {
                panel_dibujo.borrar_Completo();//con esto borramos el dibujo y empezamos uno nuevo
                mp_mal.play(sonido_mal, 1, 1, 1, 0, 0);
                vidas--;//con esto decimos que las vida se reduzca en uno por cada respuesta incorrecta

                fallos3++;
                conteo_extra_puntos = 0;

                mostrarDialogo();//aqui se llamaria al metodo mostrar dialogo para que muestre el mensaje por cada fallo

                switch (vidas)//con esto vamos a definir los distintos casos que se pueden presentar para las vidas
                {
                    case 3://al empezar tendra tres vidas
                        iv_vidas.setImageResource(R.drawable.tresvidas);
                        break;
                    case 2://si falla se muestra un mensaje y se le descuenta una vida
                        toastIncorrecto("Te quedan dos oportunidades", 4000);
                        iv_vidas.setImageResource(R.drawable.dosvidas);
                        break;
                    case 1://si falla se muestra un mensaje de que solo le queda una vida
                        toastIncorrecto("Te queda una oportunidad", 4000);
                        iv_vidas.setImageResource(R.drawable.unavida);
                        break;
                    case 0://si ya no le quedan oportunidades entra en este caso
                        mostrarDialogo();
                        break;
                }
                et_respuesta.setText("");//con esto hacemos que se limpie el campo si el usuario escribe una respuesta incorrecta
            }

            NumAleatorio();//con esto llamamos al metodo para que se acutalizen las imagenes por cada respuesta

        } else {//si el campo esta vacio entra por aca
            toastIncorrecto("Escribe tu respuesta", 4000);
        }

    }

    public void toastCorrecto(String msg, int duracion) {
        LayoutInflater inflater_lay = getLayoutInflater();
        View view_lay = inflater_lay.inflate(R.layout.custom_toast_ok, (ViewGroup) findViewById(R.id.toast_ok));
        TextView txt_toast = view_lay.findViewById(R.id.text_toast_ok);
        txt_toast.setText(msg);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, (int) getResources().getDimension(R.dimen.toast_margin));
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
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, (int) getResources().getDimension(R.dimen.toast_margin));
        toast.setDuration(duracion);
        toast.setView(view_lay);
        toast.show();
    }

    //COLOCARLO ARRIBA DEL METODO NUMALEATIORIRRROOO
    public void dialogo_puntos_extra() {
        if (score <= 9) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View Viewlayout = inflater.inflate(R.layout.dialogo_puntos_extra, null);
            final TextView txt_ganaste_extras = (TextView) Viewlayout.findViewById(R.id.txt_ganaste_extras); // txtItem1
            final Button btn_aceptar_extras = (Button) Viewlayout.findViewById(R.id.btn_aceptar_extras);

            Typeface fuente = Typeface.createFromAsset(getAssets(), "fuentes/patito.ttf");
            txt_ganaste_extras.setTypeface(fuente);
            btn_aceptar_extras.setTypeface(fuente);

            builder.setView(Viewlayout);

            final AlertDialog dialog = builder.create();
            dialog.show();

            txt_ganaste_extras.setText(" ¡Felicidades, ganaste 10 puntos extra!");
            txt_ganaste_extras.setCompoundDrawablesWithIntrinsicBounds(R.drawable.a_inicial1, 0, 0, 0);

            btn_aceptar_extras.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setCancelable(false);
        }
    }

    public void NumAleatorio() {
        if (score <= 9) {//esto es para que se mantenga dentro del nivel 1 ya que el nivel 1 corresponde a un score maximo de 10
            numAleatorio_uno = (int) (Math.random() * 9000) + 997;
            numAleatorio_dos = (int) (Math.random() * 88) + 10;

            String valor1 = String.valueOf(numAleatorio_uno);
            String valor2 = String.valueOf(numAleatorio_dos);

            //ahora vamos a controlar si la operacion realizada es correcta
            resultado = numAleatorio_uno * numAleatorio_dos;
            String textoRespuesta = String.valueOf(resultado);

            //ahora vamos a controlar que no existan resultados negativos con esta condicion
            //La validacion que esta anajo del numAleatorio 2 es para verificar que no contenga un cero
            if (resultado >= 0 && numAleatorio_dos % 10 != 0) {
                //fuentes
                Typeface raya = Typeface.createFromAsset(getAssets(), "fuentes/boxi.otf");
                Typeface numbers = Typeface.createFromAsset(getAssets(), "fuentes/boxi_contorno.otf");
                tv_numUno.setTypeface(numbers);
                tv_numDos.setTypeface(numbers);
                tv_raya.setTypeface(raya);
                //valores
                tv_numUno.setText(valor1);
                tv_numDos.setText("x" + valor2);
                tv_raya.setText("____");
                et_respuesta.setText(textoRespuesta);

            } else {//si las imagenes que salen son negativas viene por aca
                NumAleatorio();//esto es recursividad lo usamos para ejecutar otra vez el codigo numAleatorio si salen imagenes negativas
            }


        } else {//con esto si el jugador lleva un score de mas de 10 avanza al nivel 2
            AciertosFallos();
            Intent level2 = new Intent(this, Main2Activity_Nivel4.class);

            string_score = String.valueOf(score);//con esto almacenamos el score en una variable string
            string_vidas = String.valueOf(vidas);//con esto almacenamos las vidas en una variable string
            enviar_puntos_extra = String.valueOf(contador_puntos_extra);

            level2.putExtra("jugador", nombre_jugador);//con esto enviamos el nombre del jugador al nivel2
            level2.putExtra("puntos_extra", enviar_puntos_extra);
            level2.putExtra("score", string_score);//con esto enviamos el puntaje del jugador al nivel2
            level2.putExtra("vidas", string_vidas);//con esto enviamos las vidas del jugador al nivel2

            startActivity(level2);//con esto iniciamos el nivel2
            finish();//finalizamos el nivel1
            mp.stop();//detenemos la musica del nivel1
            mp.release();//con esto destrumos la cancion y liberamos memoria
        }
    }

    //con esto vamos a almacenar los datos de cada jugador
    public void BaseDeDatos() {
        int suma_total;
        suma_total = score + contador_puntos_extra;
        int completado = 0;

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre_jugador);
        registro.put("score", suma_total);
        registro.put("completado", completado);
        db.insert("puntaje", null, registro);
        db.close();
    }

    public void AciertosFallos() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("aciertos3", aciertos3);
        registro.put("fallos3", fallos3);
        db.insert("AciertosFallos", null, registro);
        db.close();
    }

    public void dialogo_inicio_nivel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity_Nivel3.this);
        LayoutInflater inflater = getLayoutInflater();
        View vista = inflater.inflate(R.layout.dialogo_inicio_nivel, null);
        builder.setView(vista);
        builder.setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.show();

        final TextView txt_bienvenida = vista.findViewById(R.id.txt_inicio_1);
        final TextView txt_logro_dispo = vista.findViewById(R.id.txt_inicio_2);
        final TextView txt_logro1 = vista.findViewById(R.id.txt_inicio_3);
        final TextView txt_logro2 = vista.findViewById(R.id.txt_inicio_4);
        final TextView txt_vidas = vista.findViewById(R.id.txt_inicio_5);

        Typeface fuente = Typeface.createFromAsset(getAssets(), "fuentes/patito.ttf");
        txt_bienvenida.setTypeface(fuente);
        txt_logro_dispo.setTypeface(fuente);
        txt_logro1.setTypeface(fuente);
        txt_logro2.setTypeface(fuente);
        txt_vidas.setTypeface(fuente);

        txt_bienvenida.setText(" ¡Bienvenido al Nivel 3!");
        txt_logro_dispo.setText(" Logros disponibles: ");
        txt_logro1.setText(" Acierta 5 operaciones seguidas y gana 10 Puntos extra.");
        txt_logro2.setText(" Supera todos los desafiós del Nivel 3 y gana una vida extra.");
        txt_vidas.setText(" ¡Buena suerte!");
        //Iconos
        txt_bienvenida.setCompoundDrawablesWithIntrinsicBounds(R.drawable.a_inicial1, 0, 0, 0);
        txt_logro_dispo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.a_inicial2, 0, 0, 0);
        txt_logro1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.a_inicial3, 0, 0, 0);
        txt_logro2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.a_life, 0, 0, 0);
        txt_vidas.setCompoundDrawablesWithIntrinsicBounds(R.drawable.a_suerte, 0, 0, 0);

        Button btn_acepto_inicio = vista.findViewById(R.id.btn_aceptar_inicio);
        btn_acepto_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);

    }

    public void mostrarDialogo() {
        String respuesta = et_respuesta.getText().toString();
        int respuesta_jugador = Integer.parseInt(respuesta);

        AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity_Nivel3.this);
        LayoutInflater inflater = getLayoutInflater();
        View vista = inflater.inflate(R.layout.dialogo_error_multi, null);
        builder.setView(vista);
        builder.setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.show();

        final TextView txt1 = vista.findViewById(R.id.texto_dialogo);
        final TextView txt2 = vista.findViewById(R.id.texto_dialogo2);
        final TextView txt3 = vista.findViewById(R.id.texto_dialogo3);
        final TextView txt4 = vista.findViewById(R.id.texto_dialogo4);

        Typeface fuente = Typeface.createFromAsset(getAssets(), "fuentes/patito.ttf");
        txt1.setTypeface(fuente);
        txt2.setTypeface(fuente);
        txt3.setTypeface(fuente);
        txt4.setTypeface(fuente);

        int multiplicador_unidades, multiplicador_decenas;

        multiplicador_unidades = numAleatorio_dos % 10;
        multiplicador_decenas = numAleatorio_dos / 10;

        int product1 = numAleatorio_uno * multiplicador_unidades;
        int product2 = numAleatorio_uno * multiplicador_decenas;


        String[] texto_medidas = {"Unidades", "Decenas", "Centenas", "Unidades de millar", "Decenas de millar", "Centenas de millar",
                "Unidades de millón", "Decenas de millón", "Centenas de millón"};

        int avance_medidas_array = 0;

        txt1.setText("Respuesta Incorrecta");
        txt2.setText("\nPrimer producto parcial  -> " + product1 +
                "\nSegundo producto parcial -> " + product2 + "*");
        txt3.setText("\nResultado correcto: " + resultado);
        txt4.setText("Resultado ingresado: " + respuesta_jugador);


        Button btn_ok = vista.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vidas == 0) {
                    dialogo_game_over();
                } else {
                    dialog.dismiss();
                }
            }
        });
    }

    //Abajo del dialogo de error
    public void dialogo_game_over() {
        mp.stop();//con esto detenemos la cancion del nivel
        mp.release();//con esto destruimos el audio y liberamos memoria

        //Sonido Game over
        game_over.play(sonido_over, 1, 1, 1, 0, 0);

        AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity_Nivel3.this);
        LayoutInflater inflater = getLayoutInflater();
        View vista = inflater.inflate(R.layout.dialogo_game_over, null);
        builder.setView(vista);
        builder.setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.show();

        final TextView txt_game_1 = vista.findViewById(R.id.txt_game_over_1);
        final TextView txt_game_2 = vista.findViewById(R.id.txt_game_over_2);
        final TextView txt_game_3 = vista.findViewById(R.id.txt_game_over_3);
        final TextView txt_game_4 = vista.findViewById(R.id.txt_game_over_4);
        final TextView txt_game_5 = vista.findViewById(R.id.txt_game_over_5);

        Typeface fuente = Typeface.createFromAsset(getAssets(), "fuentes/patito.ttf");
        txt_game_1.setTypeface(fuente);
        txt_game_2.setTypeface(fuente);
        txt_game_3.setTypeface(fuente);
        txt_game_4.setTypeface(fuente);
        txt_game_5.setTypeface(fuente);

        int suma = score + contador_puntos_extra;
        txt_game_1.setText(" ¡Juego Terminado!");
        txt_game_2.setText(" Puntaje Obtenido: " + score);
        txt_game_3.setText(" Puntaje Extra Obtenido: " + contador_puntos_extra);
        txt_game_4.setText(" Puntaje Total Almacenado: " + suma);
        txt_game_5.setText(" ¡Mejor Suerte la Próxima!");
        //Iconos
        txt_game_1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.a_over2, 0, 0, 0);
        txt_game_2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.a_over5, 0, 0, 0);
        txt_game_3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.a_over3, 0, 0, 0);
        txt_game_4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.a_over4, 0, 0, 0);
        txt_game_5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.a_suerte2, 0, 0, 0);

        Button btn_acepto_over = vista.findViewById(R.id.btn_aceptar_over);
        btn_acepto_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vidas == 0) {
                    BaseDeDatos();//con esto cada vez que el usuario falle llamamos a la base de datos
                    AciertosFallos();
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                    finish();//con esto finalizamos el nivel2 y regresamos al inicio
                }
            }
        });
        dialog.setCancelable(false);
    }

    ///Metodos para el menu de dibujar
    public void estado_color(int c) {
        estado_pincel = c;
        //importar el Path
        drawPath = new Path();
    }

    public void Confirmacion_borrado() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View Viewlayout = inflater.inflate(R.layout.confirmacion_borrado, null);
        final TextView txt_borrado = (TextView) Viewlayout.findViewById(R.id.txt_confir_borrado); // txtItem1
        final Button btn_confir_borra = (Button) Viewlayout.findViewById(R.id.btn_confir_borra);
        final Button btn_cancel_borra = (Button) Viewlayout.findViewById(R.id.btn_cancel_borra);

        Typeface fuente = Typeface.createFromAsset(getAssets(), "fuentes/patito.ttf");
        txt_borrado.setTypeface(fuente);
        btn_confir_borra.setTypeface(fuente);
        btn_cancel_borra.setTypeface(fuente);

        builder.setView(Viewlayout);

        final AlertDialog dialog = builder.create();
        dialog.show();

        txt_borrado.setText("¿Seguro que quieres borrar las anotaciones en pantalla?");

        btn_confir_borra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lienzo.setBorrado(false);
                //Con lo de abajo llamamos al metodo para que borre el panel
                panel_dibujo.borrar_Completo();
                goma = 0;
                pintar = 0;
                iconoDesactivar.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.a_des_pin));
                dialog.dismiss();
            }
        });

        btn_cancel_borra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
    }

    public void goma_grosor() {
        goma_por_defecto = false;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View Viewlayout = inflater.inflate(R.layout.seek_bar_grosor, null);
        final TextView item1 = (TextView) Viewlayout.findViewById(R.id.text_seek_grosor); // txtItem1
        final Button btn_seek_aceptar = (Button) Viewlayout.findViewById(R.id.btn_seek_aceptar);
        final Button btn_seek_cancelar = (Button) Viewlayout.findViewById(R.id.btn_seek_cancelar);

        Typeface fuente_seek = Typeface.createFromAsset(getAssets(), "fuentes/patito.ttf");
        item1.setTypeface(fuente_seek);
        btn_seek_aceptar.setTypeface(fuente_seek);
        btn_seek_cancelar.setTypeface(fuente_seek);

        builder.setIcon(android.R.drawable.star_big_on);
        builder.setTitle("Seleccione el grosor de la goma");
        builder.setView(Viewlayout);

        final AlertDialog dialog = builder.create();
        dialog.show();

        SeekBar seek1 = (SeekBar) Viewlayout.findViewById(R.id.seekBar_grosor);
        seek1.setMax(40);
        seek1.setProgress(4);

        seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Abajo convertimos a string el valor de progress para que lo muestre
                item1.setText(String.valueOf(progress));
                grosor_goma = progress;
                goma_por_defecto = true;
            }

            public void onStartTrackingTouch(SeekBar arg0) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        btn_seek_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lienzo.Grosor_Goma(grosor_goma);
                goma = 1;
                iconoDesactivar.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.a_des_pin2));
                pintar = 0;
                Lienzo.setBorrado(true);
                dialog.dismiss();
            }
        });

        btn_seek_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);

    }

    void openDialog(boolean supportsAlpha) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, defaultcolor, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                goma = 0;
                Lienzo.setBorrado(false);
                pintar = 1;
                iconoDesactivar.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.a_des_pin2));
                Lienzo.Grosor_Lapiz(4);
                defaultcolor = color;
                drawPaint.setColor(defaultcolor);
                estado_color(drawPaint.getColor());
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }
        });
        dialog.show();
    }

    public void Lapiz_Grosor() {
        lapiz_por_defecto = false;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View Viewlayout = inflater.inflate(R.layout.seek_bar_grosor, null);
        final TextView item1 = (TextView) Viewlayout.findViewById(R.id.text_seek_grosor); // txtItem1
        final Button btn_seek_aceptar = (Button) Viewlayout.findViewById(R.id.btn_seek_aceptar);
        final Button btn_seek_cancelar = (Button) Viewlayout.findViewById(R.id.btn_seek_cancelar);

        Typeface fuente_seek = Typeface.createFromAsset(getAssets(), "fuentes/patito.ttf");
        item1.setTypeface(fuente_seek);
        btn_seek_aceptar.setTypeface(fuente_seek);
        btn_seek_cancelar.setTypeface(fuente_seek);

        builder.setIcon(android.R.drawable.star_big_on);
        builder.setTitle("Seleccione el grosor del pincel");
        builder.setView(Viewlayout);

        final AlertDialog dialog = builder.create();
        dialog.show();

        SeekBar seek1 = (SeekBar) Viewlayout.findViewById(R.id.seekBar_grosor);
        seek1.setMax(40);
        seek1.setProgress(4);

        seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Abajo convertimos a string el valor de progress para que lo muestre
                item1.setText(String.valueOf(progress));
                grosor_lapiz = progress;
                lapiz_por_defecto = true;
            }

            public void onStartTrackingTouch(SeekBar arg0) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        btn_seek_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lienzo.setBorrado(false);
                Lienzo.Grosor_Lapiz(grosor_lapiz);
                pintar = 1;
                iconoDesactivar.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.a_des_pin2));
                goma = 0;
                dialog.dismiss();
            }
        });

        btn_seek_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);

    }

    @Override
    public void onBackPressed() {//con esto controlamos que usuario no se mueva del nivel actual y no pueda regresar

    }
}
