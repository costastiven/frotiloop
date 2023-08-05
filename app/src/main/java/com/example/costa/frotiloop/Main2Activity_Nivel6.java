package com.example.costa.frotiloop;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity_Nivel6 extends AppCompatActivity {

    private TextView tv_nombre, tv_rol, tv_score;
    private ImageView iv_Auno, iv_Ados, iv_vidas, iv_signo;
    private EditText et_respuesta;
    private MediaPlayer mp, mp_great, mp_bad;

    int score, numAleatorio_uno, numAleatorio_dos, resultado, vidas = 3;
    String nombre_jugador, rol_jugador, string_score, string_vidas;

    //este sera un vector unidimensional el cual almacenara los carateres respectivos de cada imagen
    String numero[] = {"cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Esto es para colocar el activity solo en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main2__nivel6);

        for (int i = 0; i < 2; i++) {
            Toast.makeText(this, "Nivel 6 - Sumas, Restas y Multiplicaciones", Toast.LENGTH_LONG).show();
        }

        tv_nombre = (TextView) findViewById(R.id.textView_nombre);
        tv_rol = (TextView) findViewById(R.id.tv_Puntos_Extra);
        tv_score = (TextView) findViewById(R.id.textView_score);
        iv_vidas = (ImageView) findViewById(R.id.imageView_vidas);
        iv_Auno = (ImageView) findViewById(R.id.imageView_NumUno);
        iv_Ados = (ImageView) findViewById(R.id.imageView_NumDos);
        iv_signo = (ImageView) findViewById(R.id.imageView_signo);
        et_respuesta = (EditText) findViewById(R.id.editText_resultado);

        //colocar el nombre del jugador en el nivel previamene recuperado del activity principal
        nombre_jugador = getIntent().getStringExtra("jugador");
        rol_jugador = getIntent().getStringExtra("rol");
        tv_nombre.setText("Jugador: " + nombre_jugador);
        tv_rol.setText(rol_jugador);


        //con esto recuperamos el score del jugador para seguir jugando en el nivel2
        string_score = getIntent().getStringExtra("score");
        score = Integer.parseInt(string_score);
        tv_score.setText("Score: " + score);

        //con esto recuperamos las vidas del jugador para seguir jugando en el nivel2
        string_vidas = getIntent().getStringExtra("vidas");
        vidas = Integer.parseInt(string_vidas);
        if (vidas == 3) {
            iv_vidas.setImageResource(R.drawable.tresvidas);
        }
        if (vidas == 2) {
            iv_vidas.setImageResource(R.drawable.dosvidas);
        }
        if (vidas == 1) {
            iv_vidas.setImageResource(R.drawable.unavida);
        }

        //colocar el icono del juego en el actionBar del nivel
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //colocar cancion en el nivel
        mp = MediaPlayer.create(this, R.raw.levels_a);
        mp.start();
        mp.setLooping(true);

        //colocar los audios representativos a las respuestas correctas e incorrectas
        //este es para las respuestas correctas
        mp_great = MediaPlayer.create(this, R.raw.correcto_a);
        //para las respuestas incorrectas este
        mp_bad = MediaPlayer.create(this, R.raw.incorrecto_a);

        NumAleatorio();//con esto hacemos que al arrancar el nivel 1 ya se ejecuten las imagenes aleatorias
    }

    public void Comparar(View view) {//con este metodo vamos a comparar los resultados del usuario si son correctos o incorrectos
        String respuesta = et_respuesta.getText().toString();
        //ahora vamos a validar si la respuesta del usuario es correcta o incorrecta

        if (!respuesta.equals("")) {//con esto decimos que si el campo no esta vacio entra por aca

            int respuesta_jugador = Integer.parseInt(respuesta);//con esto convertimos la respuesta de String a int
            if (resultado == respuesta_jugador)//si el resultado del ususario es correcto entra por aca
            {
                mp_great.start();//este audio indica que la respuesta es correcta
                score++;//con esto incrementamos el score en uno por cada respuesta correcta
                tv_score.setText("Score: " + score);
                et_respuesta.setText("");//con esto limpiamos el campo respuesta

            } else//si el resultado del ususario es Incorrecto entra por aca
            {
                mp_bad.start();//este audio indica que la respuesta es Incorrecta
                vidas--;//con esto decimos que las vida se reduzca en uno por cada respuesta incorrecta

                switch (vidas)//con esto vamos a definir los distintos casos que se pueden presentar para las vidas
                {
                    case 3://al empezar tendra tres vidas
                        iv_vidas.setImageResource(R.drawable.tresvidas);
                        break;
                    case 2://si falla se muestra un mensaje y se le descuenta una vida
                        Toast.makeText(this, "Te quedan dos oportunidades", Toast.LENGTH_LONG).show();
                        iv_vidas.setImageResource(R.drawable.dosvidas);
                        break;
                    case 1://si falla se muestra un mensaje de que solo le queda una vida
                        Toast.makeText(this, "Te queda una oportunidad", Toast.LENGTH_LONG).show();
                        iv_vidas.setImageResource(R.drawable.unavida);
                        break;
                    case 0://si ya no le quedan oportunidades entra en este caso
                        BaseDeDatos();//con esto cada vez que el usuario falle llamamos a la base de datos
                        Toast.makeText(this, "Has perdido todas tus oportunidades", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        //con eso lo redireccionamos al activity incial para que comienze el juego nuevamente
                        startActivity(intent);//con esto regresamos al inicio
                        finish();//con esto finalizamos el nivel2 y regresamos al inicio
                        mp.stop();//con esto detenemos la cancion del nivel
                        mp.release();//con esto destruimos el audio y liberamos memoria
                        break;
                }
                et_respuesta.setText("");//con esto hacemos que se limpie el campo si el usuario escribe una respuesta incorrecta
            }

            NumAleatorio();//con esto llamamos al metodo para que se acutalizen las imagenes por cada respuesta

        } else {//si el campo esta vacio entra por aca
            Toast.makeText(this, "Escribe tu respuesta", Toast.LENGTH_SHORT).show();
        }

    }

    public void NumAleatorio() {
        if (score <= 10000) {//esto es para que se mantenga dentro del nivel 1 ya que el nivel 1 corresponde a un score maximo de 10
            numAleatorio_uno = (int) (Math.random() * 10);
            numAleatorio_dos = (int) (Math.random() * 10);

            //ahora vamos a alternar operaciones es decir que salgan sumas y tambien restas

            if (numAleatorio_uno >= 0 && numAleatorio_uno <= 3) {//si se cumple esta condicion hacemos una suma
                resultado = numAleatorio_uno + numAleatorio_dos;
                iv_signo.setImageResource(R.drawable.suma);
            } else if (numAleatorio_uno >= 4 && numAleatorio_uno <= 7)// si no se cumple la condicion anterior se hara una resta
            {
                resultado = numAleatorio_uno - numAleatorio_dos;
                iv_signo.setImageResource(R.drawable.resta);
            } else//si no se cumple ninguna de las condiciones anteriores entra por aca y se hace una multiplicacion
            {
                resultado = numAleatorio_uno * numAleatorio_dos;
                iv_signo.setImageResource(R.drawable.multiplicacion);
            }

            //ahora vamos a controlar que no existan resultados negativos con esta condicion
            if (resultado >= 0) {//si las imagenes que salen no son negativas viene por aca

                for (int i = 0; i < numero.length; i++)//con esto vamos a asignarle un valor a cada imagen que aparece aleatoriamente
                {
                    int id = getResources().getIdentifier(numero[i], "drawable", getPackageName());
                    //ahora con esto vamos a identificar que imagen pertenece a cada numero
                    if (numAleatorio_uno == i) {
                        iv_Auno.setImageResource(id);//con esto colocamos la imagen al numero aleatorio que corresponda
                    }
                    if (numAleatorio_dos == i) {
                        iv_Ados.setImageResource(id);
                    }
                }

            } else {//si las imagenes que salen son negativas viene por aca
                NumAleatorio();//esto es recursividad lo usamos para ejecutar otra vez el codigo numAleatorio si salen imagenes negativas
            }


        } else {
            Intent gameover = new Intent(this, MainActivity.class);

            Toast.makeText(this, "!Felicitaciones juego terminado!", Toast.LENGTH_LONG).show();

            startActivity(gameover);//con esto iniciamos el nivel2
            finish();//finalizamos el nivel1
            mp.stop();//detenemos la musica del nivel1
            mp.release();//con esto destrumos la cancion y liberamos memoria
        }
    }

    //con esto vamos a almacenar los datos de cada jugador
    public void BaseDeDatos() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre_jugador);
        registro.put("score", score);
        db.insert("puntaje", null, registro);
        db.close();
    }

    @Override
    public void onBackPressed() {//con esto controlamos que usuario no se mueva del nivel actual y no pueda regresar

    }
}
