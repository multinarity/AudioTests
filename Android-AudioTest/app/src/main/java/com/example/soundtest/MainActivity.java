package com.example.soundtest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    boolean isWhite = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.sounds);
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
    }
}