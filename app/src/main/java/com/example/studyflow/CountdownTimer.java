package com.example.studyflow;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CountdownTimer extends AppCompatActivity {


    private TextView tvTimer;
    private EditText etHours, etMinutes, etSeconds;
    private Button btnStart, btnStop;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_timer);

        tvTimer = findViewById(R.id.tvTimer);
        etHours = findViewById(R.id.etHours);
        etMinutes = findViewById(R.id.etMinutes);
        etSeconds = findViewById(R.id.etSeconds);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });
    }

    private void startTimer() {
        if (timerRunning) {
            return;
        }
        try {

        int hours = Integer.parseInt(etHours.getText().toString());
        int minutes = Integer.parseInt(etMinutes.getText().toString());
        int seconds = Integer.parseInt(etSeconds.getText().toString());

        long totalMillis = (hours * 60 * 60 * 1000) + (minutes * 60 * 1000) + (seconds * 1000);
        timeLeftInMillis = totalMillis;

        countDownTimer = new CountDownTimer(totalMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
            }
        }.start();

        timerRunning = true;
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
        } catch (NumberFormatException e) {
            Toast.makeText(CountdownTimer.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopTimer() {
        if (timerRunning) {
            countDownTimer.cancel();
            timerRunning = false;
        }

        btnStart.setEnabled(true);
        btnStop.setEnabled(false);

        etHours.getText().clear();
        etMinutes.getText().clear();
        etSeconds.getText().clear();

        tvTimer.setText("00:00:00");
    }

    private void updateTimer() {
        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        tvTimer.setText(timeLeftFormatted);
    }
}