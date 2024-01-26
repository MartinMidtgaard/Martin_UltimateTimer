package com.example.ultimatetimer;

import android.widget.TextView;

import java.util.Locale;

public class TimerThread implements Runnable {

    private MainActivity activity;
    private TextView txt_countdown;
    private boolean isRunning;
    private long remainingSeconds;
    private long initialSeconds;

    public TimerThread(MainActivity activity, TextView txt_countdown, long remainingSeconds) {
        this.activity = activity;
        this.txt_countdown = txt_countdown;
        this.remainingSeconds = remainingSeconds;
        this.initialSeconds = remainingSeconds;
        updateTimer();
    }

    @Override
    public void run() {
        while (remainingSeconds > 0 && isRunning) {
            updateTimer();
            try {
                Thread.sleep(1000);
                remainingSeconds--;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateTimer() {
        int hours = (int) remainingSeconds / 3600;
        int minutes = (int) (remainingSeconds % 3600) / 60;
        int seconds = (int) (remainingSeconds % 60);
        String time = String.format(Locale.ENGLISH, "%02d:%02d:%02d", hours, minutes, seconds);

        activity.runOnUiThread(() -> txt_countdown.setText(time));
    }

    public void stopTimer() {
        isRunning = false;
    }

    public void startTimer() {
        isRunning = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void resetTimer() {
        isRunning = false;
        remainingSeconds = initialSeconds;
        updateTimer();
    }

    public boolean getRunning() {
        return isRunning;
    }
}
