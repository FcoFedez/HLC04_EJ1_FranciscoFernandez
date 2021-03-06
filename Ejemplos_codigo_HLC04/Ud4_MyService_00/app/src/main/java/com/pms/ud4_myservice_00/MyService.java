package com.pms.ud4_myservice_00;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

// clase que hereda de Service para implementar un servicio
public class MyService extends Service {
    public MyService() {
    }

    /**
     * Invocado por cualquier tipo de servicio al iniciarse
     */
    @Override
    public void onCreate() {
        //Inicializaciones y configuración del servicio, previas a la llamda de
        // StartCommand() o onBind()).
        // Si el servicio ya se está ejecutando, no se llama a este método.
    }

    /**
     * Método invocado por un servicio iniciado mediante startService() -- Servicio Ejecutado.
     * el servicio se ejecuta hasta que se detiene explícitamente con stopService() o stopSelf()
     *
     * @param intent: objeto intent que se indicó en la llamada (startService(Intent))
     * @param flags: indica cómo se hace la solicitud: 0- primera llamada, otro valor para un reinicio del servicio
     * @param startId: identificador de la solicitud de inicio (
     * @return valor entero: valor que describe la forma en que
     * el sistema debe continuar el servicio en caso de que el sistema lo destruya:
     * START_NOT_STICKY: no vuelve a crear el servicio
     * START_STICKY: vuelve a  crear el servicio
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Aquí vendría el código con la Tarea que debe realizar el servicio
        Toast.makeText(this, "Servicio Arrancado", Toast.LENGTH_LONG).show();
        // para re-crear servicio iniciado cuando haya memoria suficiente
        return START_STICKY;
    }


    /**
     * Lo invoca un servicio iniciado mediante bindService() -- Servicio Enlazado
     * permite la comunicación entre cliente y servicio
     *
     * @return objeto IBinder que indica la interfaz de comunicación del cliente con el servicio
     * Puede ser null en el caso de un servicio ejecutado
     */
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    /**
     * Se invoca al finalizar o parar el servicio
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        // tareas antes de destruir servicio. Liberar recursos
        Toast.makeText(this, "Servicio Parado", Toast.LENGTH_LONG).show();
    }
}
