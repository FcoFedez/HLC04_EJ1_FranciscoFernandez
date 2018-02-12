package com.example.asus410.hlc04_ej1_franciscofernandez;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void arrancar(View v){

        startService(new Intent(getBaseContext(), ServicioMusica.class));
    }

    public void parar (View v ){

        stopService(new Intent(getBaseContext(), ServicioMusica.class));
    }
}
