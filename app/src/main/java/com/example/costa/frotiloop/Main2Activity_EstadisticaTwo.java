package com.example.costa.frotiloop;


import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Main2Activity_EstadisticaTwo extends AppCompatActivity {

    int valor1, valor2, valor3, valor4;

    int suma;

    String temp_fallos1, temp_fallos2, temp_fallos3, temp_fallos4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Esto es para colocar el activity solo en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main2__estadistica_two);

        //icono para el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        PieChart pieChart = findViewById(R.id.pieChart);
        ArrayList<PieEntry> visitors = new ArrayList<>();

        AdminSQLiteOpenHelper admin1 = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase db = admin1.getReadableDatabase();

        Cursor Aciertos_Fallos = db.rawQuery("SELECT SUM(fallos1), SUM(fallos2), SUM(fallos3), SUM(fallos4) FROM AciertosFallos", null);

        if (Aciertos_Fallos.moveToFirst()) {
            temp_fallos1 = Aciertos_Fallos.getString(0);
            temp_fallos2 = Aciertos_Fallos.getString(1);
            temp_fallos3 = Aciertos_Fallos.getString(2);
            temp_fallos4 = Aciertos_Fallos.getString(3);

            valor1 = Integer.parseInt(temp_fallos1);
            valor2 = Integer.parseInt(temp_fallos2);
            valor3 = Integer.parseInt(temp_fallos3);
            valor4 = Integer.parseInt(temp_fallos4);

            db.close();
        } else {
            db.close();
        }

        visitors.add(new PieEntry(valor1, "Nivel 1"));
        visitors.add(new PieEntry(valor2, "Nivel 2"));
        visitors.add(new PieEntry(valor3, "Nivel 3"));
        visitors.add(new PieEntry(valor4, "Nivel 4"));

        suma = valor1 + valor2 + valor3 + valor4;

        PieDataSet pieDataSet = new PieDataSet(visitors, "");
        pieDataSet.setColors(ColorTemplate.rgb("#808B96"), ColorTemplate.rgb("#E74C3C"), ColorTemplate.rgb("#6AEE09"),
                ColorTemplate.rgb("#FF7400"));
        //colores para el nivel 5 y 6
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(40f);
        pieDataSet.setFormSize(20f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterTextSize(30f);
        pieChart.setEntryLabelTextSize(20f);
        pieChart.setCenterText("Fallos Totales: " + suma);
        pieChart.animateY(3000);

        //leyendaaaaaaa
        Legend leyenda = pieChart.getLegend();
        leyenda.setTextSize(50f);
        leyenda.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        leyenda.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        leyenda.setOrientation(Legend.LegendOrientation.HORIZONTAL);


    }

    //Ahora vamos a crear un metodo para que el usuario no pueda regresar para atras
    //porque una vez iniciado el juego no se debe volver hacia atras
    @Override
    public void onBackPressed() {
        finish();
    }
}
