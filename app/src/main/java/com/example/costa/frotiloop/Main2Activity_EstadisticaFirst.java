package com.example.costa.frotiloop;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class Main2Activity_EstadisticaFirst extends AppCompatActivity {

    int valor1, valor2, valor3, valor4;

    int suma;

    String temp_aciertos1, temp_aciertos2, temp_aciertos3, temp_aciertos4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Esto es para colocar el activity solo en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main2__estadistica_first);

        //icono para el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        PieChart pieChart = findViewById(R.id.pieChart);
        ArrayList<PieEntry> visitors = new ArrayList<>();

        AdminSQLiteOpenHelper admin1 = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase db = admin1.getReadableDatabase();

        Cursor Aciertos_Fallos = db.rawQuery("SELECT SUM(aciertos1), SUM(aciertos2), SUM(aciertos3), SUM(aciertos4) FROM AciertosFallos", null);

        if (Aciertos_Fallos.moveToFirst()) {//con esto preguntamos si hay datos en la base si los hay los muestra
            temp_aciertos1 = Aciertos_Fallos.getString(0);
            temp_aciertos2 = Aciertos_Fallos.getString(1);
            temp_aciertos3 = Aciertos_Fallos.getString(2);
            temp_aciertos4 = Aciertos_Fallos.getString(3);

            valor1 = Integer.parseInt(temp_aciertos1);
            valor2 = Integer.parseInt(temp_aciertos2);
            valor3 = Integer.parseInt(temp_aciertos3);
            valor4 = Integer.parseInt(temp_aciertos4);

            db.close();//cerramos la base

        } else {
            db.close();
        }

        visitors.add(new PieEntry(valor1, "Nivel 1"));
        visitors.add(new PieEntry(valor2, "Nivel 2"));
        visitors.add(new PieEntry(valor3, "Nivel 3"));
        visitors.add(new PieEntry(valor4, "Nivel 4"));

        suma = valor1 + valor2 + valor3 + valor4;

        PieDataSet pieDataSet = new PieDataSet(visitors, "");

        pieDataSet.setColors(ColorTemplate.rgb("#EE0941"), ColorTemplate.rgb("#09CBEE"), ColorTemplate.rgb("#6AEE09"),
                ColorTemplate.rgb("#FF7400"));
        //colores para el nivel 5 y 6 del Color Template
        //ColorTemplate.rgb("#8700FF"), ColorTemplate.rgb("#808B96")
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(40f);
        pieDataSet.setFormSize(30f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterTextSize(30f);
        pieChart.setEntryLabelTextSize(20f);
        pieChart.setCenterText("Aciertos Totales: " + suma);
        pieChart.animateY(3000);

        //leyendaaaaaaa
        Legend leyenda = pieChart.getLegend();
        leyenda.setTextSize(50f);
        leyenda.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        leyenda.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        leyenda.setOrientation(Legend.LegendOrientation.VERTICAL);

    }

    //mostrar el menu

    //metodo para mostrar el menu calculadora durante el juego

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow_reportes, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_fallos) {
            Intent fallos = new Intent(this, Main2Activity_EstadisticaTwo.class);
            startActivity(fallos);
        } else if (id == R.id.item_meses) {
            Intent meses = new Intent(this, Main2Activity_Estadistica_2023.class);
            startActivity(meses);
        } else if (id == R.id.item_salir) {
            onBackPressed();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    //Ahora vamos a crear un metodo para que el usuario no pueda regresar para atras
    //porque una vez iniciado el juego no se debe volver hacia atras
    @Override
    public void onBackPressed() {

    }
}
