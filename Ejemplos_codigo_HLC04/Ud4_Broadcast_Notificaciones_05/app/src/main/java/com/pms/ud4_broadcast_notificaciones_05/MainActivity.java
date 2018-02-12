package com.pms.ud4_broadcast_notificaciones_05;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //objeto intentFilter que se usará para filtrar el intent emitido por un BroadcastReceiver
    IntentFilter intentFilter;

    //llamado al crearse la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Se ejecuta una vez que la Activity ha terminado de cargarse en el dispositivo
    //y el usuario empieza a interactuar con la aplicación.
    @Override
    public void onResume(){
        super.onResume();
        //---IntentFilter para filtrar intent de archivo descargado ---
        intentFilter = new IntentFilter();
        intentFilter.addAction("FILE_DOWNLOADED_ACTION");

        //---registrar el receptor ---
        registerReceiver(intentReceiver, intentFilter);

    }
    //Se ejecuta cuando el sistema arranca una nueva Activity
    //que necesitará los recursos del sistema centrados en ella,
    // por lo que esta Activity quedará pausada
    @Override
    public void onPause(){
        super.onPause();
        //--- anular el registro del recpetor ---
        unregisterReceiver(intentReceiver);

    }
    //onClick() botón 'Arrancar Servicio'
    public void arrancarServicio(View view) {
        startService(new Intent(getBaseContext(), MyIntentService.class));
    }

    //Cuando se recibe el intent, invoca una instancia de la clase BroadcastReceiver que hemos definido
    //En este caso, se muestra "Fichero descargado", pero se pueden pasar datos del servicio
    // a la actividad mediante el intent
    private BroadcastReceiver intentReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive (Context context, Intent intent){
            Toast.makeText(getBaseContext(), "Fichero descargado", Toast.LENGTH_LONG).show();

            Integer bytes=intent.getIntExtra("BYTES", 0);
            // Creamos Notificación

            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent intencionPendiente = PendingIntent.getActivity(context, 0, new Intent(context, MyIntentService.class), 0);
            Notification noti = new NotificationCompat.Builder(context)
                    .setContentIntent(intencionPendiente)
                    .setTicker("DESCARGA")
                    .setContentTitle("Fichero descargado: "+ bytes.toString()+ " bytes")
                    .setSmallIcon(R.drawable.ic_launcher)
                    .addAction(R.drawable.ic_launcher, "DESCARGA", intencionPendiente)
                    .setVibrate(new long[] {100, 250, 100, 500})
                    .build();

            nm.notify(1, noti);
        }
    };
}
