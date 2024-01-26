package com.example.ultimatetimer;

import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText editTextHours;
    private EditText editTextMinutes;
    private EditText editTextSeconds;
    private TextView textViewCountDown;
    private Button btnSet;
    private Button btnStartStop;
    private Button btnReset;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long startTimerInMillis;
    private long timeLeftInMillis;
    private long endTimer;

    private LinearLayout timerContainer;
    //private long initialTimerSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerContainer = findViewById(R.id.timer_container);

        editTextHours = findViewById(R.id.edit_txt_hours);
        editTextMinutes = findViewById(R.id.edit_txt_minutes);
        editTextSeconds = findViewById(R.id.edit_txt_seconds);
        Button btnAddTimer = findViewById(R.id.btn_add_timer);

        btnAddTimer.setOnClickListener(view -> addNewTimer());
    }

    /*public long getInitialTimerSeconds() {
        return initialTimerSeconds;
    }*/

    public void addNewTimer() {
        int hours = Integer.parseInt(editTextHours.getText().toString()) * 3600;
        int minutes = Integer.parseInt(editTextMinutes.getText().toString()) * 60;
        int seconds = Integer.parseInt(editTextSeconds.getText().toString()) + minutes + hours;

        View newTimerLayout = LayoutInflater.from(this).inflate(R.layout.layout_timer, timerContainer, false);

        TextView txtTimer = newTimerLayout.findViewById(R.id.txt_countdown);
        Button btnStartStop = newTimerLayout.findViewById(R.id.btn_start_stop);
        Button btnReset = newTimerLayout.findViewById(R.id.btn_reset);
        Button btnRemove = newTimerLayout.findViewById(R.id.btn_remove_timer);

        timerContainer.addView(newTimerLayout);
        TimerThread timerThread = new TimerThread(this, txtTimer, seconds);
        btnStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timerThread.getRunning()) {
                    timerThread.stopTimer();
                } else {
                    timerThread.startTimer();
                }
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerThread.resetTimer();
            }
        });
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerThread.stopTimer();
                timerContainer.removeView(newTimerLayout);
            }
        });
    }

}

