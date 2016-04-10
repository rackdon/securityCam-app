package app.rackdon.com.securitycam.notification;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import app.rackdon.com.securitycam.socket.IOSocket;
import io.socket.client.IO;
import io.socket.client.Socket;

public class NotificationService extends IntentService {
    private String SERVERIP;
    private String SERVERPORT;
    private IOSocket ioSocket;

    public NotificationService() {
        super("NotificationService");
        ioSocket = new IOSocket(new Notification(this));
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SERVERIP = this.getSharedPreferences("SecurityCam", Context.MODE_PRIVATE)
                .getString("Url", "");
        SERVERPORT = this.getSharedPreferences("SecurityCam", Context.MODE_PRIVATE)
                .getString("ServerPort", "8082");

        IO.Options options = ioSocket.addOptions();
        Socket socket = ioSocket.createSocket(SERVERIP, SERVERPORT, options);
        ioSocket.connect(socket);
    }
}
