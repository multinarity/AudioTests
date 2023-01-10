package com.example.soundtest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;


import java.io.File;

public class MainActivity extends AppCompatActivity {


    private static final String GPIO_DIR = "/sys/class/gpio/gpio1124";
    private static final String UDPPLAY_PORT = "11514";
    private static final String SOUND_FILE_PATH = "/data/local/tmp/1kHz_48000Hz_16bit_5sec_stereo.wav";

    boolean isWhite = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayer mediaPlayer = new MediaPlayer();
        Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sin);
        try {
            mediaPlayer.setDataSource(this, soundUri);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isWhite=!isWhite;
                if(isWhite)
                {
                    btn.setBackgroundColor(Color.WHITE);
                    mediaPlayer.start();
                }
                else
                    btn.setBackgroundColor(Color.BLACK);

            }
        });


        Button button = findViewById(R.id.buttonGPIO);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Check if the GPIO directory exists
                    File gpioDir = new File(GPIO_DIR);
                    if (!gpioDir.exists()) {
                        // Export the GPIO pin to userspace
                        BufferedWriter exportWriter = new BufferedWriter(new FileWriter("/sys/class/gpio/export"));
                        exportWriter.write("1124");
                        exportWriter.close();
                        // Set the direction of the pin to output
                        BufferedWriter directionWriter = new BufferedWriter(new FileWriter(GPIO_DIR + "/direction"));
                        directionWriter.write("out");
                        directionWriter.close();
                    }
                    // Set the value of the pin to 1
                    BufferedWriter valueWriter = new BufferedWriter(new FileWriter(GPIO_DIR + "/value"));
                    valueWriter.write("1");
                    valueWriter.close();
                    // Play the sound file using Android's native audio player
                    mediaPlayer.start();
                    // Set the value of the pin back to 0 when the sound has finished playing
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            try {
                                BufferedWriter valueWriter = new BufferedWriter(new FileWriter(GPIO_DIR + "/value"));
                                valueWriter.write("0");
                                valueWriter.close();
                            } catch (IOException e) {
                                Toast.makeText(getApplicationContext(), "Error setting pin value: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Error playing sound: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}