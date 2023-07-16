package com.example.media;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Button button;
    SeekBar seekBar;
    TextView duration;
    private MediaPlayer mediaPlayer;
    private int click = 0;
    private boolean ready = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        //pause = findViewById(R.id.pause);
        seekBar = findViewById(R.id.seekBar);
        duration = findViewById(R.id.duration);

        //Media Player using local source...
       // mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.bones);

        //Media Player using remote source...
        mediaPlayer = new MediaPlayer();
        try {
//            mediaPlayer.setDataSource("https://djmaza.live/files/download/id/8231");  //UCL anthem...
            mediaPlayer.setDataSource("https://djmaza.live/files/download/id/5730");  //On my way...
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Toast.makeText(MainActivity.this, "Music is ready", Toast.LENGTH_SHORT).show();
                ready = true;

                seekBar.setMax(mediaPlayer.getDuration());

                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                },0,900);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        duration.setVisibility(View.VISIBLE);
                        duration.setText(progress/1000 + "/193");
                        if(fromUser) mediaPlayer.seekTo(progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
            }
        });
        mediaPlayer.prepareAsync();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ready) {
                    if (click % 2 == 0) {
                        mediaPlayer.start();
                        button.setText("Pause");
                        click++;
                    } else {
                        mediaPlayer.pause();
                        button.setText("Play");
                        click++;
                    }
                }
                else Toast.makeText(MainActivity.this, "Please Wait...", Toast.LENGTH_SHORT).show();
            }
        });
//        pause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mediaPlayer.pause();
//            }
//        });
    }
}