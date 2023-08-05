package com.example.costa.frotiloop;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by costa on 22/01/2023.
 */

public class Lienzo extends View {
    public static int pintar = 0;
    public static int goma = 0;
    public static Path drawPath = new Path();
    public static Paint drawPaint = new Paint();
    public static int estado_pincel = Color.RED;
    public Paint canvasPaint;
    public Canvas drawCanvas;
    public Bitmap canvasBitmap;
    public static boolean borrar = false;//iniciamos la variable de borrar en false

    //para los grosores que funcionen bien independientemente
    public static boolean lapiz_por_defecto = false;
    public static boolean goma_por_defecto = false;

    public Lienzo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing() {
        drawPaint.setColor(Color.RED);//color por defecto
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(4);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        //Lo de abajo permite dibujar con difuminado
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    //tamaño asignado al bitmap por defecto para dibujar
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);//Color del Bitmap por defecto
        drawCanvas = new Canvas(canvasBitmap);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
        //ROTAR BITMAAAAP

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (pintar == 1 || goma == 1) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN://cuando pulsamos
                    drawPath.moveTo(x, y);
                    break;
                case MotionEvent.ACTION_MOVE://cuando movemos el dedo
                    if (borrar) {
                        drawPath.lineTo(x, y);
                        drawCanvas.drawPath(drawPath, drawPaint);
                        drawPath.reset();
                        drawPath.moveTo(x, y);
                    } else {
                        drawPath.lineTo(x, y);
                    }
                    break;
                case MotionEvent.ACTION_UP://cuando levantamos el dedo
                    drawPath.lineTo(x, y);
                    drawCanvas.drawPath(drawPath, drawPaint);
                    drawPath.reset();
                    break;//quizas lo podemos quitar nose
                default:
                    return false;
            }
        }
        invalidate();
        return true;
    }

    //STROKE PARA EL GROSOR LAPIZ
    public static void Grosor_Lapiz(int nuevo_grosor_lapiz) {
        if (lapiz_por_defecto == false) {
            drawPaint.setStrokeWidth(4);
        } else {
            drawPaint.setStrokeWidth(nuevo_grosor_lapiz);
        }
    }

    //STROKE PARA EL GROSOR GOMAAA
    public static void Grosor_Goma(int nuevo_grosor_goma) {
        if (goma_por_defecto == false) {
            drawPaint.setStrokeWidth(4);
        } else {
            drawPaint.setStrokeWidth(nuevo_grosor_goma);
        }
    }

    public static void setBorrado(Boolean estaborrado) {
        borrar = estaborrado;
        if (borrar) {//si se activa la goma con esto vamos borrando
            drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            //VEEEEER TUTORIALES DE COMO USAR LA GOMA TRANSPARENTE
        } else {//si no se activa le pasamos un null
            drawPaint.setXfermode(null);
        }
    }

    public void borrar_Completo() {
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }


}
