package com.pms.ud4_serviciomusica_01;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class ServicioMusica extends Service {
    public ServicioMusica() {
    }

    //objeto MediaPlayer para la reproducción de música
    MediaPlayer reproductor;

    @Override
    public void onCreate(){
        Toast.makeText(this, "Servicio creado",Toast.LENGTH_SHORT).show();
        //obtiene el recurso de audio de res/raw/audio.mid
        reproductor = MediaPlayer.create(this, R.raw.audio);

    }

    //iniciar servicio
    @Override
    public int onStartCommand(Intent intencion, int flags, int idArranque){
        Toast.makeText(this, "Servicio arrancado " + idArranque,Toast.LENGTH_SHORT).show();
        reproductor.start();
        //el sistema trata de crear de nuevo el servicio cuando disponga de memoria suficiente
        return START_STICKY;
    }

    //parar servicio
    @Override
    public void onDestroy(){
        Toast.makeText(this, "Servicio detenido",Toast.LENGTH_SHORT).show();
        reproductor.stop();

    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
}
