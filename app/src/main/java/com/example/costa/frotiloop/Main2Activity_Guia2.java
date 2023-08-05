package com.example.costa.frotiloop;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

import yuku.ambilwarna.AmbilWarnaDialog;

import static com.example.costa.frotiloop.Lienzo.drawPaint;
import static com.example.costa.frotiloop.Lienzo.drawPath;
import static com.example.costa.frotiloop.Lienzo.estado_pincel;
import static com.example.costa.frotiloop.Lienzo.goma_por_defecto;
import static com.example.costa.frotiloop.Lienzo.lapiz_por_defecto;
import static com.example.costa.frotiloop.Lienzo.pintar;
import static com.example.costa.frotiloop.Lienzo.goma;

public class Main2Activity_Guia2 extends AppCompatActivity {

    private EditText et_respuesta;
    int resultado;
    //array de backgrounds
    int backId[] = {R.drawable.guia_000, R.drawable.guia_017, R.drawable.guia_018, R.drawable.guia_019,
            R.drawable.guia_020, R.drawable.guia_021, R.drawable.guia_022, R.drawable.guia_023};

    int i = 0;
    int total;

    //int resultado1 = 479, resultado2 = 625, resultado3 = 74538, resultado4 = 51424, resultado5 = 1875,
    //        resultado6 = 1784644, resultado7 = 92, resultado8 = 274;

    RelativeLayout layout;

    //Dibujar
    int defaultcolor;
    Lienzo panel_dibujo;
    public static float grosor_pincel = 0;
    public MenuItem iconoDesactivar;

    public Button btn_instru;

    int grosor_lapiz = 4;
    int grosor_goma = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2__guia2);
        //Esto es para colocar el activity solo en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        et_respuesta = (EditText) findViewById(R.id.editText_resultado);
        layout = (RelativeLayout) findViewById(R.id.relative_laya);
        //Objeto lienzo para poder hacer dibujos
        panel_dibujo = (Lienzo) findViewById(R.id.lienzo_dibujo_act);
        defaultcolor = drawPaint.getColor();

        btn_instru = (Button) findViewById(R.id.btn_instru);

        Typeface fuente = Typeface.createFromAsset(getAssets(), "fuentes/patito.ttf");
        btn_instru.setTypeface(fuente);


        //icono para el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        total = backId.length;

        numGuia();
    }

    //metodo para mostrar el menu del juego con lapiz incluido
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.guia_actividades, menu);
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
        } else if (id == R.id.item_salir) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void Comparar(View view) {
        int id = view.getId();
        String respuesta = et_respuesta.getText().toString();
        //ahora vamos a validar si la respuesta del usuario es correcta o incorrecta

        if (id == R.id.button3) {
            if (!respuesta.equals("")) {
                int respuesta_jugador = Integer.parseInt(respuesta);//con esto convertimos la respuesta de String a int
                if (resultado == respuesta_jugador)//si el resultado del ususario es correcto entra por aca
                {
                    panel_dibujo.borrar_Completo();//con esto borramos el dibujo y empezamos uno nuevo
                    et_respuesta.setText("");
                    i++;

                } else//si el resultado del ususario es Incorrecto entra por aca
                {
                    toastIncorrecto("Resultado Incorrecto", 4000);
                    et_respuesta.setText("");
                    return;
                }

                numGuia();

            } else {
                toastIncorrecto("Escribe tu respuesta", 4000);
                return;
            }
            if (i == total) {//Aqui finalizan las imagenes y volvemos a la guia principal
                finish();
                return;
            }
        }
        layout.setBackgroundResource(backId[i]);
    }

    public void numGuia() {
        if (i == 0) {
            resultado = 479;
        }
        if (i == 1) {
            resultado = 625;
        }
        if (i == 2) {
            resultado = 74538;
        }
        if (i == 3) {
            resultado = 51424;
        }
        if (i == 4) {
            resultado = 1875;
        }
        if (i == 5) {
            resultado = 1784644;
        }
        if (i == 6) {
            resultado = 92;
        }
        if (i == 7) {
            resultado = 274;
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

    public void Pasos(View view) {
        pintar = 0;
        goma = 0;
        iconoDesactivar.setIcon(ContextCompat.getDrawable(this, R.drawable.a_des_pin));
        //comenzar a poner los textos
        //y solucionar los edit text con el focus
        Dialogo_pasos();
    }

    public void Dialogo_pasos() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View Viewlayout = inflater.inflate(R.layout.dialogo_pasos, null);
        final TextView txt_pasos = (TextView) Viewlayout.findViewById(R.id.txt_pasos_operacion); // txtItem1
        final Button btn_pasos_aceptar = (Button) Viewlayout.findViewById(R.id.btn_aceptar_pasos);

        Typeface fuente = Typeface.createFromAsset(getAssets(), "fuentes/patito.ttf");
        txt_pasos.setTypeface(fuente);
        btn_pasos_aceptar.setTypeface(fuente);

        builder.setView(Viewlayout);

        final AlertDialog dialog = builder.create();
        dialog.show();

        if (i == 0) {
            txt_pasos.setText("Para resolver sumas debemos sumar los valores de derecha a izquierda, " +
                    "es decir, comenzado por las unidades, decenas y así sucesivamente." +
                    "\n*Paso 1: Sumar Unidades 9+6+4 = 19\n" +
                    "*Paso 2: Llevamos 1 a la Decena\n" +
                    "*Paso 3: Sumar Decenas 1+5+8+3 = 17\n" +
                    "*Paso 4: Llevamos 1 a la Centena\n" +
                    "*Paso 5: Sumar Centenas 1+1+2 = 4");
        }
        if (i == 1) {
            txt_pasos.setText("Para resolver restas debemos restar los valores de derecha a izquierda, " +
                    "es decir, comenzado por las unidades, decenas y así sucesivamente." +
                    "\nEn este caso para restar 3 filas debemos restar primero la fila 1 con la fila 2.\n" +
                    "Luego a ese resultado le restamos la fila 3.\n" +
                    "*Paso 1: Restar Unidades 9-3 = 6\n" +
                    "*Paso 2: Restar Decenas 8-4 = 4\n" +
                    "*Paso 3: Restar Centenas 7-1 = 6\n" +
                    "*Paso 4: Restar Unidades nuevamente 6-1 = 5\n" +
                    "*Paso 5: Restar Decenas nuevamente 4-2 = 2\n" +
                    "*Paso 6: Bajar el 6, no posee sustraendo.");
        }
        if (i == 2) {
            txt_pasos.setText("*Paso 1: Sumar Unidades 3+2+3 = 8\n" +
                    "*Paso 2: Sumar Decenas 4+4+5 = 13\n" +
                    "*Paso 3: Llevamos 1 a la Centena\n" +
                    "*Paso 4: Sumar Centenas 1+7+1+6 = 15\n" +
                    "*Paso 5: Llevamos 1 a la Unidad de millar \n" +
                    "*Paso 6: Sumar Unidades de Millar 1+8+5 = 14\n" +
                    "*Paso 7: Llevamos 1 a la Decena de millar\n" +
                    "*Paso 8: Sumamos las Decenas de millar 1+6 = 7");
        }
        if (i == 3) {
            txt_pasos.setText("Cuando no podemos restar dos números, debemos pedir prestado al número " +
                    "situado a la izquierda del número que solicita el préstamo, de esta forma el número " +
                    "obtendrá un valor de 10 combinado con el valor base, " +
                    "para poder realizar la resta y al número que nos prestó le debemos reducir uno." +
                    "\n*Paso 1: Restar Unidades 13-7 = 6" +
                    "\n*Paso 2: Restar Decenas 4-1 = 3" +
                    "\n*Paso 3: Restar Centenas 12-4 = 8" +
                    "\n*Paso 4: Restar Unidades de millar 3-2 = 1" +
                    "\n*Paso 5: Bajar el 5, no posee sustraendo" +
                    "\n*Paso 6: Restar Unidades nuevamente 6-2 = 4" +
                    "\n*Paso 7: Restar Decenas nuevamente 3-1 = 2" +
                    "\n*Paso 8: Restar Centenas nuevamente 8-4 = 4");
        }
        if (i == 4) {
            txt_pasos.setText("Para realizar multiplicaciones debemos multiplicar cada dígito del segundo factor por cada " +
                    "dígito del primer factor y escribir el producto debajo de la línea." +
                    "\n*Paso 1: Multiplicar el 3 por la Unidad 3x5= 15\n" +
                    "*Paso 2: Llevamos 1 a la Decena\n" +
                    "*Paso 3: Multiplicar  la Decena más el 1 = 3x2+1 = 7 \n" +
                    "*Paso 4: Multiplicamos la Centena 3x6 = 18");
        }
        if (i == 5) {
            txt_pasos.setText("Cuando multiplicamos por ejemplo 5 cifras por 2 cifras, debemos multiplicar " +
                    "primero el factor de la segunda fila situado a la derecha por todos los elementos de la primera fila, " +
                    "es decir, de derecha a izquierda, teniendo en cuenta la cantidad de factores de la segunda fila. " +
                    "\n*Paso 1: 74361 x 4 U." +
                    "\n*Paso 2: 74361 x 2 D." +
                    "\nDebemos tener en cuenta que al acomodar los productos parciales, estos deben quedar situados de tal manera " +
                    "que el último elemento del primer producto parcial este desplazado una posición hacia la izquierda, para " +
                    "luego finalizar efectuando la suma entre ambos productos parciales.");
        }
        if (i == 6) {
            txt_pasos.setText("Para resolver una división debemos dividir el primer dígito del dividendo entre el divisor. " +
                    "Este será el primer dígito del cociente." +
                    "\nDebemos tener en cuenta que si el divisor es más grande que el dividendo, se debe tomar la cantidad " +
                    "correspondiente en el dividendo para poder efectuar la división." +
                    "\n*Paso 1: Tomamos el 36 debido a que el divisor 4 es mayor que 3.\n" +
                    "*Paso 2: Buscamos un número que al multiplicarlo por el divisor, nos dé un número " +
                    "inferior o igual al 36, en este caso el 9. \n" +
                    "*Paso 3: Efectuamos la multiplicación entre el cociente 9 y el divisor 4." +
                    "\n*Paso 4: Colocamos el resultado obtenido menos el dividendo = 0." +
                    "\n*Paso 5: buscamos un número que al multiplicarlo por él 4 sea inferior " +
                    "o igual a 8, en este caso el 2." +
                    "\nPaso 6: Multiplicamos el 2 por el 4." +
                    "\nPaso 7: Colocamos el resultado obtenido menos el dividendo = 0." +
                    "\nCómo resto nos queda 0 y no es posible seguir dividiendo.");
        }
        if (i == 7) {
            txt_pasos.setText("Para esta división debemos tomar 3 números en el divisor, es decir, el 208. " +
                    "\n*Paso 1: Buscamos un número que al multiplicarlo por 76, nos dé un " +
                    "número inferior o igual a 208, en este caso el 2." +
                    "\n*Paso 2: Efectuamos la multiplicación entre el 2 por el 76." +
                    "\n*Paso 3: Colocamos el resultado obtenido menos el dividendo = 56." +
                    "\n*Paso 4: Bajamos el 3 y buscamos un número que al multiplicarlo por 76 nos dé 563 o menos, " +
                    "en este caso el 7." +
                    "\n*Paso 5: Colocamos el resultado obtenido menos el dividendo = 31." +
                    "\n*Paso 6: Bajamos el 2 y buscamos un que al multiplicarlo por el divisor nos dé 312 o menos, " +
                    "en este caso el 4. " +
                    "\nPaso 7: Colocamos el resultado obtenido menos el dividendo = 8." +
                    "\nCómo resto nos queda 8 y no es posible seguir dividiendo.");
        }

        btn_pasos_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //Metodos para el menu de dibujar

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
        //Hacer el dialgo no cancelable


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

    //Ahora vamos a crear un metodo para que el usuario no pueda regresar para atras
    //porque una vez iniciado el juego no se debe volver hacia atras
    @Override
    public void onBackPressed() {

    }

}
