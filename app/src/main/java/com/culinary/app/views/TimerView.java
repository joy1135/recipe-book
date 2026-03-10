package com.culinary.app.views;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.culinary.app.R;

public class TimerView extends LinearLayout {

    private TextView tvTimerLabel;
    private TextView tvTimerDisplay;
    private Button btnStart;
    private Button btnPause;
    private Button btnReset;
    private EditText etCustomTime;
    private Button btnSetTime;

    private CountDownTimer countDownTimer;
    private long totalTimeMillis;
    private long remainingTimeMillis;
    private boolean isRunning = false;
    private boolean isFinished = false;
    private int soundResId;
    private boolean editable;

    public TimerView(Context context, String label, int minutes, int seconds,
                     int soundResId, boolean editable) {
        super(context);
        this.totalTimeMillis = (minutes * 60L + seconds) * 1000L;
        this.remainingTimeMillis = totalTimeMillis;
        this.soundResId = soundResId;
        this.editable = editable;

        LayoutInflater.from(context).inflate(R.layout.view_timer, this, true);

        tvTimerLabel = findViewById(R.id.tv_timer_label);
        tvTimerDisplay = findViewById(R.id.tv_timer_display);
        btnStart = findViewById(R.id.btn_timer_start);
        btnPause = findViewById(R.id.btn_timer_pause);
        btnReset = findViewById(R.id.btn_timer_reset);
        etCustomTime = findViewById(R.id.et_custom_time);
        btnSetTime = findViewById(R.id.btn_set_time);

        tvTimerLabel.setText(label);
        updateDisplay(remainingTimeMillis);

        // Показать поле ввода только если editable
        if (editable) {
            etCustomTime.setVisibility(View.VISIBLE);
            btnSetTime.setVisibility(View.VISIBLE);
        } else {
            etCustomTime.setVisibility(View.GONE);
            btnSetTime.setVisibility(View.GONE);
        }

        btnStart.setOnClickListener(v -> startTimer());
        btnPause.setOnClickListener(v -> pauseTimer());
        btnReset.setOnClickListener(v -> resetTimer());

        btnSetTime.setOnClickListener(v -> {
            String input = etCustomTime.getText().toString().trim();
            if (!input.isEmpty()) {
                try {
                    int mins = Integer.parseInt(input);
                    totalTimeMillis = mins * 60 * 1000L;
                    remainingTimeMillis = totalTimeMillis;
                    updateDisplay(remainingTimeMillis);
                    resetTimer();
                } catch (NumberFormatException e) {
                    etCustomTime.setError(getContext().getString(R.string.invalid_input));
                }
            }
        });

        // Начальное состояние кнопок
        btnPause.setEnabled(false);
    }

    private void startTimer() {
        // Нельзя запустить дважды без сброса
        if (isRunning || isFinished) return;

        isRunning = true;
        btnStart.setEnabled(false);
        btnPause.setEnabled(true);

        countDownTimer = new CountDownTimer(remainingTimeMillis, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTimeMillis = millisUntilFinished;
                updateDisplay(millisUntilFinished);

                // Красный цвет при < 1 минуты
                if (millisUntilFinished < 60000) {
                    tvTimerDisplay.setTextColor(
                            getResources().getColor(R.color.timer_warning));
                } else {
                    tvTimerDisplay.setTextColor(
                            getResources().getColor(R.color.text_primary));
                }
            }

            @Override
            public void onFinish() {
                isRunning = false;
                isFinished = true;
                remainingTimeMillis = 0;
                updateDisplay(0);
                tvTimerDisplay.setTextColor(
                        getResources().getColor(R.color.timer_warning));
                btnStart.setEnabled(false);
                btnPause.setEnabled(false);
                playSound();
            }
        }.start();
    }

    private void pauseTimer() {
        if (!isRunning) return;
        countDownTimer.cancel();
        isRunning = false;
        btnStart.setEnabled(true);
        btnPause.setEnabled(false);
    }

    private void resetTimer() {
        if (countDownTimer != null) countDownTimer.cancel();
        isRunning = false;
        isFinished = false;
        remainingTimeMillis = totalTimeMillis;
        updateDisplay(remainingTimeMillis);
        tvTimerDisplay.setTextColor(getResources().getColor(R.color.text_primary));
        btnStart.setEnabled(true);
        btnPause.setEnabled(false);
    }

    private void updateDisplay(long millis) {
        long minutes = millis / 1000 / 60;
        long seconds = (millis / 1000) % 60;
        tvTimerDisplay.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void playSound() {
        MediaPlayer mp = MediaPlayer.create(getContext(), soundResId);
        if (mp != null) {
            mp.start();
            mp.setOnCompletionListener(MediaPlayer::release);
        }
    }

    public void release() {
        if (countDownTimer != null) countDownTimer.cancel();
    }
}