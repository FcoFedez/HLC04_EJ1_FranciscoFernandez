package com.pms.ud3contadortask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ContadorTask extends AppCompatActivity {

    private int mSleep, mMax;
    private TextView textView,textodoble,textotriple,tvdoble,tvtriple;
    private CounterTask counterTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador_task);
        // cadencia de la cuenta
        mSleep = 500;
        // tope del contador
        mMax = 15;
        // marcador
        textView = (TextView) findViewById(R.id.textView1);
        textodoble = (TextView) findViewById(R.id.textoDoble);
        textodoble.setVisibility(View.INVISIBLE);
        textotriple = (TextView) findViewById(R.id.textoTriple);
        textotriple.setVisibility(View.INVISIBLE);
        tvdoble = (TextView) findViewById(R.id.twDoble);
        tvdoble.setVisibility(View.INVISIBLE);
        tvtriple = (TextView) findViewById(R.id.twTriple);
        tvtriple.setVisibility(View.INVISIBLE);
    }

    /**
     * método onClick del botón Para cuenta
     * @param v
     */
    public void stopCounter(View v) {
        if(counterTask == null) {
            Toast.makeText(this,"No hay cuenta iniciada, imposible parar",Toast.LENGTH_LONG).show();
        }else if (counterTask != null && !counterTask.isCancelled())
            // la cancela
            counterTask.cancel(true);
    }

    public void onBackPressed(){
        super.onBackPressed();

        // si cuenta no ha sido iniciada o cancelada
        if (counterTask != null && !counterTask.isCancelled()){
            // la cancela
            counterTask.cancel(true);

        }

    }



    /**
     * método onClick del botón Comenzar cuenta
     * @param v
     */
    public void startCounter(View v) {
        // si cuenta no iniciada nunca, o ha sido cancelada
        if (counterTask == null || counterTask.isCancelled()) {
            // la arranca con los parámetros indicados en execute()
            counterTask = new CounterTask();
            counterTask.execute(mSleep, mMax);
        }
    }

    /**
     * clase AsyncTask  para realizar la tarea en segundo plano
     * indicando el tipo de parámetros requeridos
     */
    private class CounterTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textodoble.setVisibility(View.VISIBLE);
            textotriple.setVisibility(View.VISIBLE);
            tvdoble.setVisibility(View.VISIBLE);
            tvtriple.setVisibility(View.VISIBLE);
        }
        /**
         * Método que inicia la tarea en segundo plano con los parámetros pasados a su
         * método execute(), y se devuelve el resultado a onPostExecute()
         * @param intParam  parámetros de ejecución de la tarea
         * @return resultado de la tarea
         */
        @Override
        protected Integer doInBackground(Integer... intParam) {
            int sleep = intParam[0];
            int max = intParam[1];
            // inicio del progreso del contador
            int progreso = 0;
            // por debajo del tope
            while (progreso < max) {
                // si cuenta cancelada
                if (isCancelled())
                    // sale sin finalizar
                    return progreso;
                    // si cuenta en marcha
                else {
                    // envía el nuevo valor calculado
                    publishProgress(progreso = progreso + 1);
                    // simula la cadencia durmiendo el hilo secundario
                    try {
                        Thread.sleep(mSleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }//fin while
            // cuenta finalizada y devuelve el valor
            return progreso;
        }

        /**
         * Método se recibe los valores de progreso enviados por publishProgress(), y
         * se actualiza la interfaz con ellos
         * @param progress recibe los valores del progreso enviados por publishProgress()
         */
        @Override
        protected void onProgressUpdate(Integer... progress) {
            // recoge el único valor enviado
            int contador = progress[0];
            // actualiza la interfaz
            textView.setText("" + contador);
            tvdoble.setText(""+contador*2);
            tvtriple.setText(""+contador*3);
        }

        /**
         * Método que recibe el valor devuelto por doInBackground()
         * y se ejecuta en Interfaz de usuario al finalizar la tarea
         * @param result valores que provienen de finalizar doInBackgound()
         */
        @Override
        protected void onPostExecute(Integer result) {

            //código que se ejecuta al finalizar la tarea en segundo plano
            Toast.makeText(getApplicationContext(), "Tarea completada con "+result+" numeros",
                    Toast.LENGTH_SHORT).show();
        }

        /**
         * Método que se ejecuta al cancelar la tarea
         */
        @Override
        protected void onCancelled(Integer result) {
            Toast.makeText(getApplicationContext(), "Tarea cancelada con la impresion de "+result+" numeros",
                    Toast.LENGTH_SHORT).show();
            //tarea no iniciada
            counterTask = null;
        }
    }


}
