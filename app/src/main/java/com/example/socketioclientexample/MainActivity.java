package com.example.socketioclientexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonSendCommand = findViewById(R.id.button2);

        buttonSendCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand();
            }
        });

        mSocket.on("to_mobile", onNewMessage);
        mSocket.connect();
    }

    private void sendCommand() {
        String message = "Dari Mobile";

        mSocket.emit("from_mobile", message);
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
            System.out.println(args[0].toString());
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
    }
}
