package com.example.timer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorInt;
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
        timer = findViewById(R.id.timer);


        gatherDataFromIntent();
        countDown(userInputInMilli(hours, minutes, seconds));



        resetBtn = findViewById(R.id.resetButton);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    intent = new Intent(SecondActivity.this, MainActivity.class);
                    startActivity(intent);
            }
        });


        stopBtn = findViewById(R.id.stopButton);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPressed)
                {
                    stopBtn.setBackgroundColor(ContextCompat.getColor(SecondActivity.this, R.color.lightergray));
                    isPressed = !isPressed;

                }
                else
                {
                    stopBtn.setBackgroundColor(ContextCompat.getColor(SecondActivity.this, R.color.orange));
                    isPressed = !isPressed;
                }

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
        });

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

}
