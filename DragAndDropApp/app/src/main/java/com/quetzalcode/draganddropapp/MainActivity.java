package com.quetzalcode.draganddropapp;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.*;
import android.view.*;

public class MainActivity extends Activity {
    Arrastra a;

    public void onCreate(Bundle b) {
        super.onCreate(b);
        a = new Arrastra(this);
        setContentView(a);
    }

    class Arrastra extends View {
        Paint paint1 = new Paint();
        Paint paint2 = new Paint();
        Paint outlinePaint = new Paint();
        Paint linePaint = new Paint();
        Paint tempLinePaint = new Paint();

        float x1 = 250, y1 = 300, r1 = 10;
        float x2 = 500, y2 = 300, r2 = 10;
        float outlineRadius = 70;
        float tempX = -1, tempY = -1;
        boolean drawingLine1 = false;
        boolean drawingLine2 = false;
        boolean permanentLine = false;

        public Arrastra(Context c) {
            super(c);

            // Configurar pintura para círculos rojos
            paint1.setColor(Color.RED);
            paint2.setColor(Color.RED);
            paint1.setAntiAlias(true);
            paint2.setAntiAlias(true);

            // Configurar pintura para circunferencias verdes
            outlinePaint.setColor(Color.GREEN);
            outlinePaint.setStyle(Paint.Style.STROKE);
            outlinePaint.setStrokeWidth(5);
            outlinePaint.setAntiAlias(true);

            // Configurar pintura para la línea permanente
            linePaint.setColor(Color.WHITE);
            linePaint.setStrokeWidth(5);
            linePaint.setAntiAlias(true);

            // Configurar pintura para la línea temporal
            tempLinePaint.setColor(Color.argb(150, 255, 255, 255)); // Semi-transparente
            tempLinePaint.setStrokeWidth(5);
            tempLinePaint.setAntiAlias(true);
        }

        protected void onDraw(Canvas can) {
            can.drawColor(Color.BLACK);

            // Dibujar circunferencias verdes
            can.drawCircle(x1, y1, outlineRadius, outlinePaint);
            can.drawCircle(x2, y2, outlineRadius, outlinePaint);

            // Dibujar círculos rojos
            can.drawCircle(x1, y1, r1, paint1);
            can.drawCircle(x2, y2, r2, paint2);

            // Dibujar línea permanente si existe
            if (permanentLine) {
                can.drawLine(x1, y1, x2, y2, linePaint);
            }

            // Dibujar línea temporal mientras se arrastra
            if (drawingLine1 && tempX != -1 && tempY != -1) {
                can.drawLine(x1, y1, tempX, tempY, tempLinePaint);
            }if (drawingLine2 && tempX != -1 && tempY != -1) {
                can.drawLine(x2, y2, tempX, tempY, tempLinePaint);
            }
        }

        public boolean onTouchEvent(MotionEvent me) {
            float X = me.getX();
            float Y = me.getY();

            switch (me.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Verificar si se tocó el primer círculo
                    if (isInsideCircle(X, Y, x1, y1, outlineRadius)) {
                        drawingLine1 = true;
                        tempX = X;
                        tempY = Y;
                    }if ( isInsideCircle(X, Y, x2, y2, outlineRadius)) {
                        drawingLine2 = true;
                        tempX = X;
                        tempY = Y;
                    }
                    permanentLine = false;
                    break;

                case MotionEvent.ACTION_MOVE:
                    // Actualizar posición temporal de la línea
                    if (drawingLine1) {
                        tempX = X;
                        tempY = Y;

                        // Verificar si llegamos al segundo círculo
                        if (isInsideCircle(X, Y, x2, y2, outlineRadius)) {
                            permanentLine = true;
                            drawingLine1 = false;
                        }

                        invalidate();
                    }if (drawingLine2) {
                        tempX = X;
                        tempY = Y;

                        // Verificar si llegamos al segundo círculo
                        if (isInsideCircle(X, Y, x1, y1, outlineRadius)) {
                            permanentLine = true;
                            drawingLine2 = false;
                        }

                        invalidate();
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    // Finalizar el dibujo de la línea temporal
                    if (drawingLine1 || drawingLine2) {
                        drawingLine1 = false;
                        drawingLine2 = false;
                        tempX = -1;
                        tempY = -1;
                        invalidate();
                    }

                    break;
            }

            return true;
        }

        private boolean isInsideCircle(float x, float y, float circleX, float circleY, float radius) {
            return Math.pow(x - circleX, 2) + Math.pow(y - circleY, 2) <= Math.pow(radius, 2);
        }
    }
}