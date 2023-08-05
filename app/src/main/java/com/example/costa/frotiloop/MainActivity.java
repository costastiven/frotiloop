package com.example.costa.frotiloop;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Clock;

public class MainActivity extends AppCompatActivity {

    private EditText et_nombre;
    private ImageView iv_personaje;
    private MediaPlayer mp;
    private Spinner spinner1;
    private Button btn_jugar, btn_guia;

    int numeroAleatorio = (int) (Math.random() * 10);
    int meses1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Esto es para colocar el activity solo en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);

        et_nombre = (EditText) findViewById(R.id.txt_nombre);
        iv_personaje = (ImageView) findViewById(R.id.imageView_Personaje);
        spinner1 = (Spinner) findViewById(R.id.spinner);
        btn_jugar = (Button) findViewById(R.id.button);
        btn_guia = (Button) findViewById(R.id.btn_guia);


        Typeface fuentejugar = Typeface.createFromAsset(getAssets(), "fuentes/boxi_bold.otf");
        btn_jugar.setTypeface(fuentejugar);

        Typeface fuenteET = Typeface.createFromAsset(getAssets(), "fuentes/patito.ttf");
        et_nombre.setTypeface(fuenteET);
        btn_guia.setTypeface(fuenteET);


        //Opciones para el spinner
        String[] opcioSp1 = {"Jugador Simple", "Jugador Experto"};

        //Array para mostrar los datos en el spinner
        ArrayAdapter<String> sp1 = new ArrayAdapter<String>(this, R.layout.spinner_item_rol, opcioSp1);
        spinner1.setAdapter(sp1);

        //icono para el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //imagenes aleatorias al iniciar
        int id;
        if (numeroAleatorio == 0 || numeroAleatorio == 10) {
            id = getResources().getIdentifier("inicial1", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        } else if (numeroAleatorio == 1 || numeroAleatorio == 9) {
            id = getResources().getIdentifier("inicial2", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        } else if (numeroAleatorio == 2 || numeroAleatorio == 8) {
            id = getResources().getIdentifier("inicial4", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        } else if (numeroAleatorio == 3 || numeroAleatorio == 7) {
            id = getResources().getIdentifier("inicial5", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        } else if (numeroAleatorio == 4 || numeroAleatorio == 5 || numeroAleatorio == 6) {
            id = getResources().getIdentifier("inicial7", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        }

        //cancion para el activity de bienvenida
        mp = MediaPlayer.create(this, R.raw.menu_music);
        mp.start();
        //con este metodo setLooping hacemos que se cicle la cancion varias veces
        mp.setLooping(true);

    }

    //ahora vamos a crear un metodo para mostrar y ocultar el menu de opciones
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    //metodo para asignar las funciones correspondientes a cada item del menu
    public boolean onOptionsItemSelected(MenuItem item) {
        //variable que se crea para saber que item se presiono
        int id = item.getItemId();
        String nombre = et_nombre.getText().toString();
        //variable para el spinner
        String seleccion = spinner1.getSelectedItem().toString();

        if (id == R.id.item1) {
            Intent rank = new Intent(this, Main2Activity_Ranking.class);
            startActivity(rank);
        } else if (id == R.id.item2) {
            LogueoAdmin();
        } else if (id == R.id.item3) {
            Intent aciertos = new Intent(this, Main2Activity_EstadisticaFirst.class);
            startActivity(aciertos);
        } else if (id == R.id.item_sonido) {
            Intent sonidos = new Intent(this, Main2Activity_ModificarSonidos.class);
            startActivity(sonidos);
        } else if (id == R.id.item_salir) {
            mp.stop();
            mp.release();
            finish();
            //System.exit(0);

        }
        //Con esto retornamos el valor del item ya
        //que es un metodo booleano y hay que retornar un valor
        return super.onOptionsItemSelected(item);
    }

    public void guia_apren(View view) {
        mp.stop();
        mp.release();
        Intent guia = new Intent(this, Main2Activity_Guia0.class);
        startActivity(guia);
    }


    //metodo para controlar el boton jugar y iniciar activitys
    public void jugar(View view) {
        String nombre = et_nombre.getText().toString();
        //variable para el spinner
        String seleccion = spinner1.getSelectedItem().toString();

        //Validacion solo caracteres y numeros
        String input = et_nombre.getText().toString().trim();

        if (seleccion.equals("Jugador Experto")) {
            if(!input.matches("^[a-zA-Z0-9]+$")){
                    toastIncorrecto("Solo se permiten letras y numeros", 4000);
                    return;
            }
            //Si contiene solo letras y numeros puede ingresar
            else{
                mp.stop();
                mp.release();
                meses1++;//sumamos la variable meses
                PartidasMeses();//llamamos al metodo para que guarde el juego de partida
                Intent experto = new Intent(this, Main2Activity_Nivel3.class);
                experto.putExtra("jugador", et_nombre.getText().toString());//con esto enviamos el nombre del jugador hacia el otro activity
                experto.putExtra("rol", seleccion);
                startActivity(experto);
                finish();
            }
        } else if (!nombre.equals("")) {//con esto decimos que si el nombre existe entre por aca
            if(!input.matches("^[a-zA-Z0-9]+$")){
                toastIncorrecto("Solo se permiten letras y numeros", 4000);
                return;
            }
            //Si contiene solo letras y numeros puede ingresar
            else{
                mp.stop();
                mp.release();//con este metodo destruimos al objeto mp para liberar recursos
                meses1++;//sumamos la variable meses
                PartidasMeses();//llamamos al metodo para que guarde el juego de partida
                Intent pasar = new Intent(this, Main2Activity_Nivel1.class);//Con esto pasamos al activity del nivel1
                pasar.putExtra("jugador", et_nombre.getText().toString());//con esto enviamos el nombre del jugador hacia el otro activity
                pasar.putExtra("rol", seleccion);
                startActivity(pasar);//con esto inciamos el activity
                finish();//con esto finalizamos el activity actual
            }
        } else {//con esto vamos a validar si el usuario no cargo su nombre entra por aca
            toastIncorrecto("Primero debes escribir tu nombre", 4000);
            //ahora vamos a abrir el teclado para que el usuario pueda escribir su nombre
            et_nombre.requestFocus();//con esto decimos que el teclado apunte al editText del nombre
            InputMethodManager teclado = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);//con esto vamos a mostrar el teclado en pantalla
            teclado.showSoftInput(et_nombre, InputMethodManager.SHOW_IMPLICIT);//con esto ya mostramos el teclado
        }
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


    //Metodo para guardar la cantidad de paridas en meses
    public void PartidasMeses() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("meses1", meses1);
        db.insert("meses", null, registro);
        db.close();
    }

    //Metodo para logeuar al admin
    public void LogueoAdmin() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View viewLayout = inflater.inflate(R.layout.logeo_admin, null);

        builder.setIcon(R.drawable.a_admin2);
        builder.setTitle("Panel - Administrador");
        builder.setView(viewLayout);

        final AlertDialog dialog = builder.create();
        dialog.show();

        //Variables
        final EditText et_user_admin = (EditText) viewLayout.findViewById(R.id.et_user_admin);
        final EditText et_admin_pass = (EditText) viewLayout.findViewById(R.id.et_admin_pass);
        final Button btn_aceptar = (Button) viewLayout.findViewById(R.id.btn_log_aceptar);
        final Button btn_cancelar = (Button) viewLayout.findViewById(R.id.btn_log_cancelar);

        Typeface fuente_edits_log = Typeface.createFromAsset(getAssets(), "fuentes/patito.ttf");
        Typeface fuente_botones = Typeface.createFromAsset(getAssets(), "fuentes/patito.ttf");
        //fuente edits
        et_user_admin.setTypeface(fuente_edits_log);
        et_admin_pass.setTypeface(fuente_edits_log);
        //Fuente botones
        btn_aceptar.setTypeface(fuente_botones);
        btn_cancelar.setTypeface(fuente_botones);


        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, password;

                user = et_user_admin.getText().toString();
                password = et_admin_pass.getText().toString();
                if (user.equals("") || password.equals("")) {
                    toastIncorrecto("Debe completar los campos", 4000);
                    return;
                } else if (user.equals("admin") && password.equals("admin")) {
                    dialog.dismiss();
                    mp.stop();
                    mp.release();
                    Intent admin = new Intent(getBaseContext(), Main2Activity_Admin.class);
                    startActivity(admin);
                    finish();
                } else {
                    toastIncorrecto("Datos incorrectos", 4000);
                }
            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);


    }

    //Ahora vamos a crear un metodo para que el usuario no pueda regresar para atras
    //porque una vez iniciado el juego no se debe volver hacia atras
    @Override
    public void onBackPressed() {

    }

}
