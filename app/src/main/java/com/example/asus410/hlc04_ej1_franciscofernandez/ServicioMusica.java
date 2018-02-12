package com.example.asus410.hlc04_ej1_franciscofernandez;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServicioMusica extends Service {

    MediaPlayer reproductor;
    private NotificationManager nm;
    private static final int ID_NOTIFICACION = 1;
    private Notification notificacion;

    public ServicioMusica() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    public void onCreate(){
        Toast.makeText(this, "Servicio creado", Toast.LENGTH_LONG).show();
        reproductor = MediaPlayer.create(this,R.raw.audio);
        nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    //iniciar servicio
    @Override
    public int onStartCommand(Intent intencion, int flags, int idArranque){
        Toast.makeText(this, "Servicio arrancado " + idArranque,Toast.LENGTH_SHORT).show();
        reproductor.start();

        servicio();

        //el sistema trata de crear de nuevo el servicio cuando disponga de memoria suficiente
        return START_STICKY;
    }

    //parar servicio
    @Override
    public void onDestroy(){
        nm.cancel(ID_NOTIFICACION);
        Toast.makeText(this, "Servicio detenido",Toast.LENGTH_SHORT).show();
        reproductor.stop();

    }

    public int compruebaApi(){
      return Build.VERSION.SDK_INT;
    }

    public void servicio(){


        Intent intent = new Intent(this,MainActivity.class);

        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Mi notificacion")
                .setContentText("servicio de musica");


        PendingIntent contIntent =
                PendingIntent.getActivity(
                        this, 0, intent, 0);
        builder.setContentIntent(contIntent);
        notificacion = builder.getNotification();
        nm.notify(ID_NOTIFICACION, notificacion);
    }

    public void servicio2(){

        Intent intent = new Intent(this,MainActivity.class);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this,"")
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setLargeIcon((((BitmapDrawable)getResources()
                                .getDrawable(R.drawable.icon)).getBitmap()))
                        .setContentTitle("Mensaje de Alerta")
                        .setContentText("Ejemplo de notificación.")
                        .setContentInfo("4")
                        .setTicker("Alerta!");
        PendingIntent contIntent =
                PendingIntent.getActivity(
                        this, 0, intent, 0);

        mBuilder.setContentIntent(contIntent);
        nm.notify(ID_NOTIFICACION, mBuilder.build());
    }

    public void servicio3(){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, "miCanal")
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("Mi notificacicon")
                        .setContentText("Servicio de musica");

        Intent intent = new Intent(this,MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        nm.notify(ID_NOTIFICACION, mBuilder.build());
    }

}
