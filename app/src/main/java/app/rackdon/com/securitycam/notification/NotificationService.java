package app.rackdon.com.securitycam.notification;


import android.app.IntentService;
import android.content.Intent;

import app.rackdon.com.securitycam.socket.IOSocket;
import io.socket.client.IO;
import io.socket.client.Socket;

public class NotificationService extends IntentService {
    private String SERVERIP;
    private final String SERVERPORT = "8082";
    private IOSocket ioSocket;
    private Notification notification;

    public NotificationService() {
        super("NotificationService");
        ioSocket = new IOSocket(new Notification(this));
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SERVERIP = (String) intent.getExtras().get("url");
        IO.Options options = ioSocket.addOptions();
        Socket socket = ioSocket.createSocket(SERVERIP, SERVERPORT, options);
        ioSocket.connect(socket);
    }
}
