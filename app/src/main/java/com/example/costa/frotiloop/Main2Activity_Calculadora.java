package com.example.costa.frotiloop;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity_Calculadora extends AppCompatActivity {

    Button btnCero, btnUno, btnDos, btnTres, btnCuatro, btnCinco, btnSeis, btnSiete, btnOcho,
            btnNueve, btnPunto, btnIgual, btnSuma, btnResta, btnMulti, btnDiv, btnLimpiar, btnSalir;
    EditText etProceso, etconcatenar;
    double numero1, numero2, resultado;
    String operador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Esto es para colocar el activity solo en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main2__calculadora);

        //colocar el icono del juego en el actionBar del nivel
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        btnLimpiar = (Button) findViewById(R.id.btnLimpiar);
        btnCero = (Button) findViewById(R.id.btnCero);
        btnUno = (Button) findViewById(R.id.btnUno);
        btnDos = (Button) findViewById(R.id.btnDos);
        btnTres = (Button) findViewById(R.id.btnTres);
        btnCuatro = (Button) findViewById(R.id.btnCuatro);
        btnCinco = (Button) findViewById(R.id.btnCinco);
        btnSeis = (Button) findViewById(R.id.btnSeis);
        btnSiete = (Button) findViewById(R.id.btnSiete);
        btnOcho = (Button) findViewById(R.id.btnOcho);
        btnNueve = (Button) findViewById(R.id.btnNueve);
        btnPunto = (Button) findViewById(R.id.btnPunto);
        btnIgual = (Button) findViewById(R.id.btnIgual);
        btnSuma = (Button) findViewById(R.id.btnSuma);
        btnResta = (Button) findViewById(R.id.btnResta);
        btnMulti = (Button) findViewById(R.id.btnMulti);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnSalir = (Button) findViewById(R.id.btnSalir);
        etProceso = (EditText) findViewById(R.id.etProceso);

        btnSuma.setEnabled(false);
        btnResta.setEnabled(false);
        btnDiv.setEnabled(false);
        btnMulti.setEnabled(false);
        btnIgual.setEnabled(false);
        btnPunto.setEnabled(false);

        Typeface fuente = Typeface.createFromAsset(getAssets(), "fuentes/boxi.otf");
        btnCero.setTypeface(fuente);
        btnUno.setTypeface(fuente);
        btnDos.setTypeface(fuente);
        btnTres.setTypeface(fuente);
        btnCuatro.setTypeface(fuente);
        btnCinco.setTypeface(fuente);
        btnSeis.setTypeface(fuente);
        btnSiete.setTypeface(fuente);
        btnOcho.setTypeface(fuente);
        btnNueve.setTypeface(fuente);
        etProceso.setTypeface(fuente);

        Typeface operaciones = Typeface.createFromAsset(getAssets(), "fuentes/bubble.ttf");
        btnSuma.setTypeface(operaciones);
        btnResta.setTypeface(operaciones);
        btnMulti.setTypeface(operaciones);
        btnDiv.setTypeface(operaciones);
        btnIgual.setTypeface(operaciones);
        btnPunto.setTypeface(operaciones);

        Typeface texto = Typeface.createFromAsset(getAssets(), "fuentes/boxi_bold.otf");
        btnSalir.setTypeface(texto);
        btnLimpiar.setTypeface(texto);

        btnCero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etconcatenar = (EditText) findViewById(R.id.etProceso);
                etProceso.setText(etconcatenar.getText().toString() + "0");
                btnSuma.setEnabled(true);
                btnResta.setEnabled(true);
                btnDiv.setEnabled(true);
                btnMulti.setEnabled(true);
                btnIgual.setEnabled(true);
                btnPunto.setEnabled(true);
            }
        });
        btnUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etconcatenar = (EditText) findViewById(R.id.etProceso);
                etProceso.setText(etconcatenar.getText().toString() + "1");
                btnSuma.setEnabled(true);
                btnResta.setEnabled(true);
                btnDiv.setEnabled(true);
                btnMulti.setEnabled(true);
                btnIgual.setEnabled(true);
                btnPunto.setEnabled(true);

            }
        });
        btnDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etconcatenar = (EditText) findViewById(R.id.etProceso);
                etProceso.setText(etconcatenar.getText().toString() + "2");
                btnSuma.setEnabled(true);
                btnResta.setEnabled(true);
                btnDiv.setEnabled(true);
                btnMulti.setEnabled(true);
                btnIgual.setEnabled(true);
                btnPunto.setEnabled(true);
            }
        });
        btnTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etconcatenar = (EditText) findViewById(R.id.etProceso);
                etProceso.setText(etconcatenar.getText().toString() + "3");
                btnSuma.setEnabled(true);
                btnResta.setEnabled(true);
                btnDiv.setEnabled(true);
                btnMulti.setEnabled(true);
                btnIgual.setEnabled(true);
                btnPunto.setEnabled(true);
            }
        });
        btnCuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etconcatenar = (EditText) findViewById(R.id.etProceso);
                etProceso.setText(etconcatenar.getText().toString() + "4");
                btnSuma.setEnabled(true);
                btnResta.setEnabled(true);
                btnDiv.setEnabled(true);
                btnMulti.setEnabled(true);
                btnIgual.setEnabled(true);
                btnPunto.setEnabled(true);
            }
        });
        btnCinco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etconcatenar = (EditText) findViewById(R.id.etProceso);
                etProceso.setText(etconcatenar.getText().toString() + "5");
                btnSuma.setEnabled(true);
                btnResta.setEnabled(true);
                btnDiv.setEnabled(true);
                btnMulti.setEnabled(true);
                btnIgual.setEnabled(true);
                btnPunto.setEnabled(true);
            }
        });
        btnSeis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etconcatenar = (EditText) findViewById(R.id.etProceso);
                etProceso.setText(etconcatenar.getText().toString() + "6");
                btnSuma.setEnabled(true);
                btnResta.setEnabled(true);
                btnDiv.setEnabled(true);
                btnMulti.setEnabled(true);
                btnIgual.setEnabled(true);
                btnPunto.setEnabled(true);
            }
        });
        btnSiete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etconcatenar = (EditText) findViewById(R.id.etProceso);
                etProceso.setText(etconcatenar.getText().toString() + "7");
                btnSuma.setEnabled(true);
                btnResta.setEnabled(true);
                btnDiv.setEnabled(true);
                btnMulti.setEnabled(true);
                btnIgual.setEnabled(true);
                btnPunto.setEnabled(true);
            }
        });
        btnOcho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etconcatenar = (EditText) findViewById(R.id.etProceso);
                etProceso.setText(etconcatenar.getText().toString() + "8");
                btnSuma.setEnabled(true);
                btnResta.setEnabled(true);
                btnDiv.setEnabled(true);
                btnMulti.setEnabled(true);
                btnIgual.setEnabled(true);
                btnPunto.setEnabled(true);
            }
        });
        btnNueve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etconcatenar = (EditText) findViewById(R.id.etProceso);
                etProceso.setText(etconcatenar.getText().toString() + "9");
                btnSuma.setEnabled(true);
                btnResta.setEnabled(true);
                btnDiv.setEnabled(true);
                btnMulti.setEnabled(true);
                btnIgual.setEnabled(true);
                btnPunto.setEnabled(true);
            }
        });
        btnPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etconcatenar = (EditText) findViewById(R.id.etProceso);
                etProceso.setText(etconcatenar.getText().toString() + ".");
            }
        });
        btnSuma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operador = "+";
                etconcatenar = (EditText) findViewById(R.id.etProceso);
                numero1 = Double.parseDouble(etconcatenar.getText().toString());
                etProceso.setText("");
                btnSuma.setEnabled(false);
                btnResta.setEnabled(false);
                btnDiv.setEnabled(false);
                btnMulti.setEnabled(false);
                btnIgual.setEnabled(false);
                btnPunto.setEnabled(false);
            }
        });
        btnResta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operador = "-";
                etconcatenar = (EditText) findViewById(R.id.etProceso);
                numero1 = Double.parseDouble(etconcatenar.getText().toString());
                etProceso.setText("");
                btnSuma.setEnabled(false);
                btnResta.setEnabled(false);
                btnDiv.setEnabled(false);
                btnMulti.setEnabled(false);
                btnIgual.setEnabled(false);
                btnPunto.setEnabled(false);
            }
        });
        btnMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operador = "*";
                etconcatenar = (EditText) findViewById(R.id.etProceso);
                numero1 = Double.parseDouble(etconcatenar.getText().toString());
                etProceso.setText("");
                btnSuma.setEnabled(false);
                btnResta.setEnabled(false);
                btnDiv.setEnabled(false);
                btnMulti.setEnabled(false);
                btnIgual.setEnabled(false);
                btnPunto.setEnabled(false);
            }
        });
        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operador = "/";
                etconcatenar = (EditText) findViewById(R.id.etProceso);
                numero1 = Double.parseDouble(etconcatenar.getText().toString());
                etProceso.setText("");
                btnSuma.setEnabled(false);
                btnResta.setEnabled(false);
                btnDiv.setEnabled(false);
                btnMulti.setEnabled(false);
                btnIgual.setEnabled(false);
                btnPunto.setEnabled(false);
            }
        });
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numero1 = 0;
                numero2 = 0;
                etProceso.setText("");
                btnSuma.setEnabled(false);
                btnResta.setEnabled(false);
                btnDiv.setEnabled(false);
                btnMulti.setEnabled(false);
                btnIgual.setEnabled(false);
                btnPunto.setEnabled(false);
            }
        });

    }

    public void metodoIgual(View view) {
        etconcatenar = (EditText) findViewById(R.id.etProceso);
        numero2 = Double.parseDouble(etconcatenar.getText().toString());

        if (operador == null) {
            //Toast.makeText(this, "Debe ingresar una operacion", Toast.LENGTH_LONG).show();
            toastIncorrecto("Debe ingresar una operacion", 4000);

            return;
        }

        btnSuma.setEnabled(true);
        btnResta.setEnabled(true);
        btnDiv.setEnabled(true);
        btnMulti.setEnabled(true);
        btnIgual.setEnabled(false);
        btnPunto.setEnabled(false);

        if (operador.equals("+")) {
            etProceso.setText("");
            resultado = numero1 + numero2;
        }
        if (operador.equals("-")) {
            etProceso.setText("");
            resultado = numero1 - numero2;
        }
        if (operador.equals("*")) {
            etProceso.setText("");
            resultado = numero1 * numero2;
        }
        if (operador.equals("/")) {
            etProceso.setText("");
            if (numero2 != 0) {
                resultado = numero1 / numero2;
            } else {
                etProceso.setText("Infinito");
            }
        }
        etProceso.setText(String.valueOf(resultado));
        operador = null;
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
        onBackPressed();
        finish();
    }

    //Ahora vamos a crear un metodo para que el usuario no pueda regresar para atras
    //porque una vez iniciado el juego no se debe volver hacia atras
    @Override
    public void onBackPressed() {

    }

}
