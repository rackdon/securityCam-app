package app.rackdon.com.securitycam.socket;

import android.util.Log;

import java.net.URISyntaxException;

import app.rackdon.com.securitycam.notification.Notification;
import io.socket.client.IO;
import io.socket.emitter.Emitter;

/**
 * Created by Rackdon on 3/4/16.
 */
public class IOSocket {
    private Notification notification;

    public IOSocket() {
    }
    public IOSocket(Notification notification) {
        this.notification = notification;
    }

    public io.socket.client.Socket createSocket(String IP, String PORT, IO.Options opts) {
        io.socket.client.Socket socket = null;
        try {
            socket = IO.socket(IP + ":" + PORT, opts);
            Log.wtf("IOSocket", "IOSocket created on " + IP + ":" + PORT);
        } catch (URISyntaxException e) {
            Log.wtf("IOSocket creation error", e);
        }
        return socket;
    }

    public IO.Options addOptions () {
        IO.Options opts = new IO.Options();
        opts.forceNew = false;
        opts.reconnection = false;
        return opts;
    }

    public void connect (io.socket.client.Socket socket) {
        socket.connect();
        socket.on("connect", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.wtf("IOSocket Connect", "Connected to the socket");
            }
        });

        socket.on("connect_error", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.wtf("IOSocket Connect Error", "Connection with the socket can not be stablished");
            }
        });

        socket.on("Motion detected", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.wtf("Motion detected", "Motion has been detected");
                notification.makeNotify();
            }
        });

        socket.on("disconnect", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.wtf("IOSocket Disconnect", "Disconnected from the socket");
            }
        });
    }
}
