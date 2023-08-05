package com.example.costa.frotiloop;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Main2Activity_Estadistica_2023 extends AppCompatActivity {

    int valor1;

    String temp_meses1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Esto es para colocar el activity solo en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main2__estadistica_2023);

        //icono para el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        BarChart barChart = findViewById(R.id.barChart_2023);

        AdminSQLiteOpenHelper admin1 = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase db = admin1.getReadableDatabase();

        Cursor reporte_meses = db.rawQuery("SELECT SUM(meses1) FROM meses", null);

        if (reporte_meses.moveToFirst()) {//con esto preguntamos si hay datos en la base si los hay los muestra
            temp_meses1 = reporte_meses.getString(0);

            valor1 = Integer.parseInt(temp_meses1);

            db.close();//cerramos la base

        } else {
            db.close();
        }

        ArrayList<BarEntry> visitantes = new ArrayList<>();
        //ajustar valores para obtener concordancia
        visitantes.add(new BarEntry(1, 45));
        visitantes.add(new BarEntry(2, valor1));

        BarDataSet barDataSet = new BarDataSet(visitantes, "Cantidad de juegos por mes");
        barDataSet.setColors(
                ColorTemplate.rgb("#09CBEE"), ColorTemplate.rgb("#FF7400"));
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(30f);
        barDataSet.setFormSize(20f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
        barChart.animateY(2000);

        //Texto que dice juegos por mes
        Legend leyenda = barChart.getLegend();
        leyenda.setTextSize(30f);

        //Numeros de los meses eje x
        XAxis xAxis = barChart.getXAxis(); //aqui se obtiene el eje x
        xAxis.setTextSize(20f); // aquí se establece el tamaño de letra deseado en unidades de dp
        xAxis.setYOffset(-5f);//ver este tambien
        xAxis.setPosition(XAxis.XAxisPosition.TOP);//ver el top inside

        //numeros de los meses eje y
        YAxis yAxisLeft = barChart.getAxisLeft(); // aquí se obtiene el eje Y izquierdo
        YAxis yAxisRight = barChart.getAxisRight(); // aquí se obtiene el eje Y izquierdo
        yAxisLeft.setTextSize(18f); // aquí se establece el tamaño de letra deseado en unidades de dp
        yAxisRight.setTextSize(18f); // aquí se establece el tamaño de letra deseado en unidades de dp
        yAxisLeft.setYOffset(10f);
        yAxisRight.setYOffset(10f);
    }

    //metodo para mostrar el menu calculadora durante el juego
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow_anios, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_anios) {
            //Toast.makeText(this, "No existen registros de años Anteriores y Siguientes al actual.", Toast.LENGTH_LONG).show();
            Intent anio_2022 = new Intent(this, Main2Activity_EstadisticaTree.class);
            startActivity(anio_2022);
        }
        return super.onOptionsItemSelected(item);
    }


    //Ahora vamos a crear un metodo para que el usuario no pueda regresar para atras
    //porque una vez iniciado el juego no se debe volver hacia atras
    @Override
    public void onBackPressed() {
        finish();
    }

}
