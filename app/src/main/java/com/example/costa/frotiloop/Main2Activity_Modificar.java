package com.example.costa.frotiloop;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity_Modificar extends AppCompatActivity {

    private ListView lv1;
    private EditText et_id, et_nuevNom, et_nuevPunt;
    private TextView tv_modificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Esto es para colocar el activity solo en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main2__modificar);

        //colocar el icono del juego en el actionBar del nivel
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        lv1 = (ListView) findViewById(R.id.lv1);
        et_id = (EditText) findViewById(R.id.editText_id);
        et_nuevNom = (EditText) findViewById(R.id.editText_nuevoNom);
        et_nuevPunt = (EditText) findViewById(R.id.editText_nuevoPun);
        tv_modificar=(TextView)findViewById(R.id.textView);

        Typeface fuente = Typeface.createFromAsset(getAssets(), "fuentes/patito.ttf");
        tv_modificar.setTypeface(fuente);
        tv_modificar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ad_custom, 0, 0, 0);

        cargarListView();
    }

    private void cargarListView() {
        ArrayList<String> lista = new ArrayList<String>();
        AdminSQLiteOpenHelper admin1 = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase db = admin1.getReadableDatabase();
        Cursor agregar = db.rawQuery("SELECT codigo, nombre, score FROM puntaje ORDER BY score DESC", null);
        while (agregar.moveToNext()) {
            lista.add(agregar.getString(0) + " - " + agregar.getString(1) + " - " + agregar.getString(2));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_admin, lista);
        lv1.setAdapter(adapter);
        db.close();
        admin1.close();
    }

    public void Modificar(View view) {
        String id=et_id.getText().toString();
        String nom=et_nuevNom.getText().toString();
        String punt= et_nuevPunt.getText().toString();

        //para validar el ingreso de solo numeros y letras en el nombre
        String input = et_nuevNom.getText().toString().trim();

        if(id.length()==0){
            toastIncorrecto("Ingresar identificador", 4000);
            return;
        }
        if(nom.length()==0){
            toastIncorrecto("Ingresar el nuevo nombre", 4000);
            return;
        }
        if(punt.length()==0){
            toastIncorrecto("Ingresar el nuevo puntaje", 4000);
            return;
        }
        if(!input.matches("^[a-zA-Z0-9]+$")){
            toastIncorrecto("Solo se permiten letras y numeros en el nuevo nombre", 4000);
            return;
        }
        //Si contiene solo letras y numeros puede ingresar
        else{
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BD", null, 1);
            SQLiteDatabase db = admin.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("nombre", et_nuevNom.getText().toString());
            registro.put("score", et_nuevPunt.getText().toString());

            int cantidad = db.update("puntaje", registro, "codigo=" + et_id.getText().toString(), null);
            if (cantidad == 0)
            {
                toastIncorrecto("No existe el codigo de jugador " + et_id.getText().toString(), 4000);
            }
            else
            {
                toastCorrecto("Se modificaron los datos", 4000);
                et_id.setText("");
                et_nuevNom.setText("");
                et_nuevPunt.setText("");
                cargarListView();
            }
            db.close();
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

    public void Salir(View view) {
        finish();
    }


    //Ahora vamos a crear un metodo para que el usuario no pueda regresar para atras
    //porque una vez iniciado el juego no se debe volver hacia atras
    @Override
    public void onBackPressed() {

    }

}
