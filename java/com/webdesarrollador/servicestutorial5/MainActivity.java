package com.webdesarrollador.servicestutorial5;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // Messenger para enviar mensajes al servicio
    Messenger mService = null;
    //Determina si estamos enlazados o no
    boolean mBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Enlazamos al servicio
        bindService(new Intent(this, Service5.class), mConnection,this.BIND_AUTO_CREATE);
        Log.d("estado","inicio");
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Desenlazamos del servicio
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }
    public void saludar(View v) {
        if (!mBound) return;

        //Creamos y enviamos un mensaje al servicio, asignandole como dato el nombre del EditText
        EditText nombre = (EditText)findViewById(R.id.nombre);
        String val = nombre.getText().toString();
        Message msg = Message.obtain(null, Service5.MSG_HOLA, 0, 0);
        Bundle b = new Bundle();
        b.putString("data", val);
        msg.setData(b);
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //Creamos una interface ServiceConection para conectarnos con el servicio
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {

            mService = new Messenger(service);
            mBound = true;
            Log.d("estado","conectado");
        }

        public void onServiceDisconnected(ComponentName className) {
            //Cuando termina la conexión con el servicio de forma inesperada. no cuando el cliente se desenlaza
            mService = null;
            mBound = false;
        }
    };
}

