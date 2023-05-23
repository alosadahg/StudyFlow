package com.example.studyflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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

        ImageView imgTheme = findViewById(R.id.imgTheme);
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
                    if(isTimerRunning) {
                        stopTimer();
                        circularProgressIndicator.setProgress(0);
                    }
                    switch (selectedButtonId) {
                        case R.id.btnPomodoroFocus:
                            // Perform action for the "Focus" button
                            updateTimerText(POMODORO_TIME);
                            break;
                        case R.id.btnPomodoroLongBreak:
                            // Perform action for the "Long" button
                            updateTimerText(LONG_BREAK_TIME);
                            break;
                        case R.id.btnPomodoroShortBreak:
                            updateTimerText(SHORT_BREAK_TIME);
                            // Perform action for the "Short" button
                            break;
                    }
                }
            }
        });
        imgTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });

        btnPomodoroStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedButtonId != -1) {
                    if (!isTimerRunning) {
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
            }
        });

        txtPomodoroTimer = findViewById(R.id.txtPomodoroTimer);
        txtPomodoroTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTimerRunning) {
                    startPomodoroTimer();
                } else {
                    stopTimer();
                }
            }
        });

        circularProgressIndicator = findViewById(R.id.circularProgressIndicator2);
        circularProgressIndicator.setMax((int) POMODORO_TIME / 1000);

        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO) {
            // Night mode is not active
            imgTheme.setBackgroundResource(R.drawable.theme_light);
            catTheme.setBackgroundResource(R.drawable.pomodoro_cat);
        } else {
            // Night mode is active
            imgTheme.setBackgroundResource(R.drawable.theme);
            catTheme.setBackgroundResource(R.drawable.pomodoro_cat_night);
        }

        ic_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
            }
        });
        imgTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO) {
                    // Night mode is not active
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    // Night mode is active
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });


    }

    private void startPomodoroTimer() {
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
                updateProgress(timerDuration); // Set progress to maximum
                startLongBreakTimer();
            }
        };

        timer.start();
        isTimerRunning = true;
    }

    private void startLongBreakTimer() {
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
                updateProgress(timerDuration); // Set progress to maximum
                startLongBreakTimer();
            }
        };

        timer.start();
        isTimerRunning = true;
    }

    private void startShortBreakTimer() {
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
                updateProgress(timerDuration); // Set progress to maximum
                startLongBreakTimer();
            }
        };

        timer.start();
        isTimerRunning = true;
    }

    @SuppressLint("NonConstantResourceId")
    private void stopTimer() {
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
}