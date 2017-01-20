package com.webdesarrollador.servicestutorial5;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;


public class Service5 extends Service {
    //Constante que nos identifica el tipo de mensaje
    static final int MSG_HOLA = 1;

    //Handler que recibira los mensajes y procesara las solicitudes
    class MiHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_HOLA:
                    String data = msg.getData().getString("data");
                    Toast.makeText(getApplicationContext(), "Hola "+data, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    //Messenger que será devuelto al cliente en el método onBind
    final Messenger mMessenger = new Messenger(new MiHandler());

    //Retornamos el messenger convertido en IBinder para poder realizar la comunicación
    @Override
    public IBinder onBind(Intent intent) {

        return mMessenger.getBinder();
    }
}