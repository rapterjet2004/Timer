package com.example.timer;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.lang.Math;

/**
 * @author Julius Linus
 *
 * SecondActivity.java is responsible for the handling the logic for
 * the timer displayed in activity_second.xml. Recieves user data from MainActivity.java
 * using Intent.
 */
public class SecondActivity extends AppCompatActivity {
    /**
     * Used for tagging Logs
     */
    private final String TAG = "SecondActivity.java";

    /**
     * int variables that store the hours, minutes, and seconds of specified from user input
     */
    private int hours, minutes, seconds;

    /**
     * boolean variables used to tell if the app is paused, or if a button is pressed.
     */
    private boolean isPaused, isPressed;

    /**
     * Gives the time remaining on the counter at any given time. Updates every tick(1000 milliseconds) in
     * onTick() method in countdown()
     */
    private long timeRemaining;

    /**
     * Creates an instance of the CountDownTimer class, from android.
     */
    private CountDownTimer mTimer;

    /**
     * Creates a TextView object that represents the timer displayed in activity_second.xml
     */
    private TextView timer;

    /**
     * Creates two Button objects that represent the reset button and the stop Button.
     * They appear grey, but are initialized in initializeResetButton() and initializeStopButton()
     */
    private Button resetBtn, stopBtn;

    /**
     * Intent used to gather data from MainActivity.java
     */
    Intent intent;


    /**
     * Initializes the activity, sets the content view to activity_second.xml, calls start()
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        start();

    }

    /**
     * Gathers data from MainActivity.java, stores it in the int variables hours, minutes, and seconds
     */
    private void gatherDataFromIntent()
    {
        intent = getIntent();
        hours = intent.getIntExtra("HourInput", 0);
        minutes = intent.getIntExtra("MinuteInput", 0);
        seconds = intent.getIntExtra("SecondInput", 0);

    }

    /**
     * Initializes the CountDownTimer mTimer and starts it.
     * Method input is used to specify how the long the CountDownTimer lasts.
     * Ticks every 1000 milliseconds(1 second), updates timeRemaining, and calls
     * formatTimer() and onTickUpdater() to send updates to the timer TextView.
     *
     * Note: onTick will never call before the previous onTick is called.
     *
     * @param milli
     */
    private void countDown(long milli)
    {
        mTimer = new CountDownTimer(milli, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                timeRemaining = millisUntilFinished;
                formatTimer();
                updateIterationFromSeconds((int)timeRemaining/1000); //drops decimal place 9.856 >>> 9
            }

            public void onFinish()
            {
                timer.setText("00:00:00");
            }
        }.start();
    }

    /**
     * uses formatTimerHelper to help format the hours, minutes, and seconds variables to be ready
     * to be displayed in the timer textView.
     */
    private void formatTimer()
    {
        timer.setText(formatTimerHelper(hours) + ":" + formatTimerHelper(minutes) + ":" + formatTimerHelper(seconds));
    }

    /**
     * Takes any int input, and formats it into a style that is ready to be displayed
     * in the timer. Adds a 0 in front of the int if it's below 10. Returns the formatted int
     * as a String.
     *
     * @param time
     * @return String
     */
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

    /**
     * Calculates user input in milliseconds, returns this result as an int. Used to restore time after
     * pause event.
     *
     * @param h
     * @param m
     * @param s
     * @return int
     */
    private int userInputInMilli(int h, int m, int s)
    {
        int result = 0;
        result += s * 1000;
        result += m * 60000;
        result += h * 3600000;
        return result;
    }

    /**
     * Updates the hours, minutes, and seconds variables from the given seconds input.
     *
     * @param seconds
     */

    private void updateIterationFromSeconds(int seconds)
    {
            //Log.i(TAG, "Seconds: " + String.valueOf(seconds));
            hours = seconds / 3600;
            double totalMinutes = ((seconds / 3600.0 - hours) * 3600) / 60.0;
            minutes = (int)totalMinutes;
            this.seconds = (int)Math.round(((totalMinutes - minutes) * 60));
            //11 iterations

    }


    /**
     * Initializes the timer, the reset button, and the stop button.
     * Gathers the data from Intent, to be stored in hours, minutes, and seconds.
     * Starts the countDown after parsing user input into milliseconds.
     */
    private void start()
    {
        initializeTextViewTimer();
        gatherDataFromIntent();
        countDown(userInputInMilli(hours, minutes, seconds));
        initializeResetButton(resetBtn);
        initializeStopButton(stopBtn);

    }

    /**
     * Initializes the CountDownTimer
     */
    private void initializeTextViewTimer()
    {
        timer = findViewById(R.id.timer);
    }

    /**
     * Initializes and sets up the reset button. When clicked, sends user back to MainActivity.java
     *
     * @param resetBtn
     */
    private void initializeResetButton(Button resetBtn)
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

    /**
     * Initializes and sets up the stop button. Timer is stopped and resumed, when clicked.
     *
     * @param stopBtn
     */
    private void initializeStopButton(Button stopBtn)
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

    /**
     * isPressed is false by default. Checks to see if the button hsa been pressed, and
     * changes graphics accordingly.
     *
     * @param b
     */
    private void ifPressed(Button b)
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

    /**
     * isPaused is false by default. Checks to see if the timer is paused, and cancels it if true.
     * Upon unPause, the timer is continues off where it left using timeRemaining as the parameter for
     * countDown()
     *
     * @param mTimer
     */
    private void ifPaused(CountDownTimer mTimer)
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
