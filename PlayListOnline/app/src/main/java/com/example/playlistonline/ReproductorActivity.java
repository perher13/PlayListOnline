package com.example.playlistonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class ReproductorActivity extends AppCompatActivity {

    protected TextView text1;
    protected TextView text2;
    protected Button boton1;
    protected Button boton2;
    protected Button boton3;
    protected Button boton4;

    private MediaPlayer mp;
    private int posicionPausada = 0;

    private Bundle extras;
    private String paquete1 = "";
    private String paquete2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);

        text1 = (TextView) findViewById(R.id.text2_Repro);
        text2 = (TextView) findViewById(R.id.text3_Repro);

        boton1 = (Button) findViewById((R.id.button2));
        boton2 = (Button) findViewById((R.id.button3));
        boton3 = (Button) findViewById((R.id.button4));
        boton4 = (Button) findViewById((R.id.boton1_Repro));

        mp = new MediaPlayer();

        extras = getIntent().getExtras();
        paquete1 = extras.getString("NOMBRE");
        paquete2 = extras.getString("URL");

        text1.setText("Titulo: " + paquete1);
        text2.setText(paquete2);

        boton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                Intent pasarPantalla = new Intent(ReproductorActivity.this, StartActivity.class);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boton1.setEnabled(true);
                boton2.setEnabled(false);
                boton3.setEnabled(true);

                if(posicionPausada > 0 ){
                    //Pausa
                    mp.seekTo(posicionPausada);
                    mp.start();
                } else if (posicionPausada < 0) {
                    //Stop
                    mp.prepareAsync();
                    mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mp.start();
                        }
                    });
                }else {

                    try {

                        Uri url = Uri.parse(text2.getText().toString());
                        mp.setDataSource(ReproductorActivity.this, url);
                        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mp.prepareAsync();

                        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                mp.start();
                            }
                        });

                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                mp.stop();
                            }
                        });

                    } catch (IOException e) {
                        Toast.makeText(ReproductorActivity.this, getString(R.string.label1_Repro), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posicionPausada = mp.getCurrentPosition();
                mp.pause();
                boton2.setEnabled(true);
                boton1.setEnabled(false);
                boton3.setEnabled(true);
            }
        });

        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posicionPausada = -1;
                mp.stop();
                boton2.setEnabled(true);
                boton1.setEnabled(false);
                boton3.setEnabled(false);
            }
        });

    }
}