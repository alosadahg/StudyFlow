package com.example.studyflow;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Pomodoro extends AppCompatActivity {
    private TextView txtPomodoroTimer;
    private CountDownTimer timer;
    private boolean isTimerRunning = false;
    private static final long POMODORO_TIME = 25 * 60 * 1000; // 25 minutes in milliseconds
    private static final long LONG_BREAK_TIME = 15 * 60 * 1000; // 15 minutes in milliseconds
    private static final long SHORT_BREAK_TIME = 5 * 60 * 1000; // 5 minutes in milliseconds
    private Button btnFocus, btnShortBreak, btnLongBreak;
    private MaterialButtonToggleGroup toggleButton;
    private Button btnPomodoroStart;
    private int selectedButtonId;
    private CircularProgressIndicator circularProgressIndicator;
    private long timerDuration;
    private long timeElapsed;
    int timerID = -1;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent main = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        ImageView catTheme = findViewById(R.id.catTheme);
        ImageView ic_close = findViewById(R.id.ic_close);

        btnFocus = findViewById(R.id.btnPomodoroFocus);
        btnShortBreak = findViewById(R.id.btnPomodoroShortBreak);
        btnLongBreak = findViewById(R.id.btnPomodoroLongBreak);
        btnPomodoroStart = findViewById(R.id.btnPomodoroStart);
        toggleButton = findViewById(R.id.toggleButton);

        selectedButtonId = -1;
        toggleButton.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                // Check if the button is checked
                if (isChecked) {
                    // Handle the button selection
                    selectedButtonId = checkedId;
                    }
            }
        });

        btnPomodoroStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTimerRunning) {
                    stopTimer();
                }

                if (selectedButtonId != -1) {
                    switch (selectedButtonId) {
                        case R.id.btnPomodoroFocus:
                            startPomodoroTimer();
                            break;
                        case R.id.btnPomodoroLongBreak:
                            startLongBreakTimer();
                            break;
                        case R.id.btnPomodoroShortBreak:
                            startShortBreakTimer();
                            break;
                    }
                }
            }
        });

        txtPomodoroTimer = findViewById(R.id.txtPomodoroTimer);

        circularProgressIndicator = findViewById(R.id.circularProgressIndicator2);

        circularProgressIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning) {
                    pauseTimer();
                }
            }
        });

        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO) {
            // Night mode is not active
            catTheme.setBackgroundResource(R.drawable.pomodoro_cat);
        } else {
            // Night mode is active
            catTheme.setBackgroundResource(R.drawable.pomodoro_cat_night);
        }

        ic_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
            }
        });
    }

    private void startPomodoroTimer() {
        timeElapsed = 0;
        timerDuration = POMODORO_TIME;
        circularProgressIndicator.setProgress(0);
        timer = new CountDownTimer(timerDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long remainingTime = timerDuration - millisUntilFinished;
                updateTimerText(millisUntilFinished);
                updateProgress(remainingTime);
            }

            @Override
            public void onFinish() {
                // Pomodoro timer finished
                // Perform any necessary actions here
                stopTimer();
                updateTimerText(POMODORO_TIME);
            }
        };
        timerID = 1;
        timer.start();
        isTimerRunning = true;
    }

    private void startLongBreakTimer() {
        timeElapsed = 0;
        timerDuration = LONG_BREAK_TIME;
        circularProgressIndicator.setProgress(0);

        timer = new CountDownTimer(timerDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long remainingTime = timerDuration - millisUntilFinished;
                updateTimerText(millisUntilFinished);
                updateProgress(remainingTime);
            }

            @Override
            public void onFinish() {
                // Long break timer finished
                // Perform any necessary actions here
                stopTimer();
                updateTimerText(LONG_BREAK_TIME);
            }
        };
        timerID = 2;
        timer.start();
        isTimerRunning = true;
    }

    private void startShortBreakTimer() {
        timeElapsed = 0;
        timerDuration = SHORT_BREAK_TIME;
        circularProgressIndicator.setProgress(0);
        timer = new CountDownTimer(timerDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long remainingTime = timerDuration - millisUntilFinished;
                updateTimerText(millisUntilFinished);
                updateProgress(remainingTime);
            }

            @Override
            public void onFinish() {
                // Short break timer finished
                // Perform any necessary actions here
                stopTimer();
                updateTimerText(SHORT_BREAK_TIME);
            }
        };
        timerID = 3;
        timer.start();
        isTimerRunning = true;
    }

    @SuppressLint("NonConstantResourceId")
    private void stopTimer() {
        timerID = -1;
        timer.cancel();
        isTimerRunning = false;
    }

    private void updateTimerText(long millisUntilFinished) {
        int minutes = (int) (millisUntilFinished / 1000) / 60;
        int seconds = (int) (millisUntilFinished / 1000) % 60;
        int progress = (int) ((timerDuration - millisUntilFinished) / 1000);
        circularProgressIndicator.setProgress(progress);
        String timeText = String.format("%02d:%02d", minutes, seconds);
        txtPomodoroTimer.setText(timeText);
    }

    private void updateProgress(long remainingTime) {
        int progress = (int) (remainingTime / 1000);
        int maxProgress = (int) (timerDuration / 1000);
        circularProgressIndicator.setProgressCompat(progress, true);
        circularProgressIndicator.setMax(maxProgress);
    }

    private void pauseTimer() {
        timer.cancel();
        isTimerRunning = false;
    }


}