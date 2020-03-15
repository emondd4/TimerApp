package com.example.timerapp;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView countTextView;
    private FloatingActionButton startButton;
    private FloatingActionButton pauseButton;
    private FloatingActionButton stopButton;
    private ProgressBar progressBar;
    private NumberPicker numberPicker;
    private CountDownTimer countDownTimer;

    private boolean TimerRunning;
    private long START_TIME_IN_MILLIS;

    private long TimeLeftMillis;
    private int i = 0;
    private long imma = 10000;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.ic_timer);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setTitle("   TimerApp");

        countTextView = findViewById(R.id.textViewCountDown);
        startButton = findViewById(R.id.floatingActionButton_play);
        pauseButton = findViewById(R.id.floatingActionButton_pause);
        stopButton = findViewById(R.id.floatingActionButton_stop);
        progressBar = findViewById(R.id.progress_bar);
        numberPicker = findViewById(R.id.number_picker);
        numberPicker.setMaxValue(100);
        numberPicker.setMinValue(1);

        stopButton.setEnabled(false);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                stopButton.setEnabled(true);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimerRunning){
                    pauseTimer();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });


    }
    private void startTimer(){
        if (flag == 0){
            imma = imma * numberPicker.getValue();
            //Toast.makeText(this,"Imma is: "+ imma,Toast.LENGTH_SHORT).show();
            START_TIME_IN_MILLIS = imma;
            TimeLeftMillis = imma;
            flag = 1;
        }
        progressBar.setProgress(i);
        countDownTimer = new CountDownTimer(TimeLeftMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TimeLeftMillis = millisUntilFinished;
                updateCountDownText();
                i++;
                progressBar.setProgress((int)i*100/((int)imma/1000));
            }
            @Override
            public void onFinish() {
                i++;
                progressBar.setProgress(100);
                TimerRunning = false;
            }
        }.start();
        TimerRunning = true;
        startButton.hide();
        numberPicker.setEnabled(false);
    }

    private void pauseTimer(){
        countDownTimer.cancel();
        TimerRunning = false;
        startButton.show();
        i--;
    }

    private void resetTimer(){
        TimeLeftMillis = START_TIME_IN_MILLIS;
        countDownTimer.cancel();
        progressBar.setProgress(0);
        i = 0;
        imma = 10000;
        updateCountDownText();
        startButton.show();
        numberPicker.setEnabled(true);
        countTextView.setText("00:00");
        flag = 0;
    }

    private void updateCountDownText(){
        int minutes = (int) (TimeLeftMillis / 1000) / 60;
        int seconds = (int) (TimeLeftMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        countTextView.setText(timeLeftFormatted);
    }
}
