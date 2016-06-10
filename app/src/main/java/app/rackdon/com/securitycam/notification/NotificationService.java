package app.rackdon.com.securitycam.notification;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import app.rackdon.com.securitycam.socket.IOSocket;
import io.socket.client.IO;
import io.socket.client.Socket;

public class NotificationService extends Service {
    private String SERVERIP;
    private String SERVERPORT;
    private IOSocket ioSocket;
    private Socket socket;

    public NotificationService() {
        ioSocket = new IOSocket(new Notification(this));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SERVERIP = this.getSharedPreferences("SecurityCam", Context.MODE_PRIVATE)
                .getString("Url", "");
        SERVERPORT = this.getSharedPreferences("SecurityCam", Context.MODE_PRIVATE)
                .getString("ServerPort", "8082");

        IO.Options options = ioSocket.addOptions();
        socket = ioSocket.createSocket(SERVERIP, SERVERPORT, options);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ioSocket.connect(socket);
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }

}
