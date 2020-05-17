package com.example.socketioclientexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSocket.on("to_mobile", onNewMessage);
        mSocket.connect();
    }

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.100.38:3000");
        } catch (URISyntaxException e) {
            System.out.println(e.toString());
        }
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            /*
            * Listener
            * args[0].toString() merupakan data yang diperoleh */
            System.out.println(args[0].toString());
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
    }

    /*
    * Contoh untuk mengirim data ke raspi dari mobile*/
    public void sendCommand(View view) {
        String message = "Dari Mobile";

        mSocket.emit("from_mobile", message);
    }
}
