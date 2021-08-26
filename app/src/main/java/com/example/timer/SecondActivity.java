package com.example.timer;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SecondActivity extends AppCompatActivity {
    private final String TAG = "SecondActivity.java";
    private int hours, minutes, seconds;
    private boolean isPaused, isPressed;
    private long timeRemaining; // I have the time remaining when the countdowntimer was canceled.
    private CountDownTimer mTimer;
    private TextView timer;
    private Button resetBtn, stopBtn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        start();
    }

    private void gatherDataFromIntent()
    {
        intent = getIntent();
        hours = intent.getIntExtra("HourInput", 0);
        minutes = intent.getIntExtra("MinuteInput", 0);
        seconds = intent.getIntExtra("SecondInput", 0);

    }

    private void countDown(long milli)
    {
        mTimer = new CountDownTimer(milli, 1000)
        {
            public void onTick(long millisUntilFinished) //while loop messes with onTick
            {
                timeRemaining = millisUntilFinished; //updates every tick
                formatTimer();
                onTickUpdater();
            }

            public void onFinish()
            {
                timer.setText("00:00:00");
            }
        }.start();
    }

    private void formatTimer()
    {
        timer.setText(formatTimerHelper(hours) + ":" + formatTimerHelper(minutes) + ":" + formatTimerHelper(seconds));
    }

    private String formatTimerHelper(int time)
    {
        String formatted = "";
        if(time >= 10)
        {
            formatted += time;
        }
        else
        {
            formatted += "0" + time;
        }

        return formatted;
    }

    private int userInputInMilli(int h, int m, int s)
    {
        int result = 0;
        result += s * 1000;
        result += m * 60000;
        result += h * 3600000;
        return result;
    }


    private void onTickUpdater()
    {
        if(seconds == 0 && minutes > 0)
        {
            minutes--;
            seconds = 59;
        }
        else
        {
            seconds--;
        }

        if(minutes == 0 && hours > 0)
        {
            hours--;
            minutes = 59;
            seconds = 59;
        }

    }

    public void start()
    {
        initializeTextViewTimer();
        gatherDataFromIntent();
        countDown(userInputInMilli(hours, minutes, seconds));
        initializeResetButton(resetBtn);
        initializeStopButton(stopBtn);

    }

    public void initializeTextViewTimer()
    {
        timer = findViewById(R.id.timer);
    }

    public void initializeResetButton(Button resetBtn)
    {
        resetBtn = findViewById(R.id.resetButton);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initializeStopButton(Button stopBtn)
    {
        stopBtn = findViewById(R.id.stopButton);
        Button finalStopBtn = stopBtn;
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifPressed(finalStopBtn);
                ifPaused(mTimer);
            }
        });
    }

    public void ifPressed(Button b)
    {
        if(!isPressed)
        {
            b.setBackgroundColor(ContextCompat.getColor(SecondActivity.this, R.color.lightergray));
            isPressed = !isPressed;
        }
        else
        {
            b.setBackgroundColor(ContextCompat.getColor(SecondActivity.this, R.color.orange));
            isPressed = !isPressed;
        }
    }

    public void ifPaused(CountDownTimer mTimer)
    {
        if(!isPaused)
        {
            mTimer.cancel();
            isPaused = !isPaused;
        }
        else
        {
            countDown(timeRemaining);
            isPaused = !isPaused;
        }
    }

}
