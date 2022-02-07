package com.example.timer;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class StopWatchActivity extends AppCompatActivity {
    // create global boolean variable for pausing and lapping
    private boolean isPaused;
    private boolean isLapped;
    // TextView
    TextView stopWatchView;
    Button stopBtn, resetBtn, startBtn;

    // global Thread variable
    private volatile Thread stopWatch;
    //navigation fragment
    NavigationFragment fragment = new NavigationFragment();
    // create global long timeElapsed variable
    private long timeElapsed = fragment.getStopWatchTimeElapsed();

    private long timePassed;

    private final String TAG = "StopWatchActivity.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.navigation, NavigationFragment.class, null)
                    .commit();
            getSupportFragmentManager().executePendingTransactions();
        }

        stopWatchView = findViewById(R.id.stopwatch);

        resetBtn = findViewById(R.id.resetButton);
        resetBtn.setOnClickListener(view -> {
            resetStopWatch();
        });

        stopBtn = findViewById(R.id.stopButton);
        stopBtn.setOnClickListener(view -> {
            ifPaused();
        });

        startBtn = findViewById(R.id.startButton);
        startBtn.setOnClickListener(view -> {
            startStopWatch(timeElapsed);
        });

        // create a Thread with a new runnable parameter
        // inside run() copy algorithm from github
        // run code only runs if thread is not null
        // code sets the textView to the updated text

        //simple right, how does pausing work?
        //well I don't want the timer to restart, so I'll have to save the time elapsed
        //then I could restart the timer and set the starting time to the time elapsed

        //How does resetting work?
        // Thread set to null
        //Timer's start time is set to 0 or timeElapsed set to 0
        //button goes back to original settings

        //How does lapping work
        //layout inflater creates custom layout, the parent layout would be a scroll view
        //layout is then added to a scroll view
        //layout text is the exact time when button is clicked
        //text is updated from the runnable thread using an if statement which changes a global boolean after it's done

        //pause simply change the global variables that the thread constantly references with the while loop
        //because stop() was depreciated, instead my reset just sets the thread to null
        //start starts a new thread

    }

    private void startStopWatch(long startTime)
    {
        stopWatch = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread thisThread = Thread.currentThread();
                while(stopWatch == thisThread)
                {
                    timePassed = System.currentTimeMillis() - SystemClock.elapsedRealtime();

                    DecimalFormat df = new DecimalFormat("00");

                    int hours = (int)(timePassed / (3600 * 1000));
                    int remaining = (int)(timePassed % (3600 * 1000));

                    int minutes = (int)(remaining / (60 * 1000));
                    remaining = (int)(remaining % (60 * 1000));

                    int seconds = (int)(remaining / 1000);
                    remaining = (int)(remaining % (1000));

                    int milliseconds = (int)(((int)timePassed % 1000) / 100);

                    String text = "";

                    if (hours > 0) {
                        text += df.format(hours) + ":";
                    }

                    text += df.format(minutes) + ":";
                    text += df.format(seconds) + ":";
                    text += Integer.toString(milliseconds);

                    //stopWatchView.setText(text);
                    Log.i(TAG, text);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }

            }
        });
        stopWatch.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(stopWatch != null) {
            stopWatch = null;
        }

        startStopWatch(timeElapsed);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(stopWatch != null) {
            stopWatch = null;
            startStopWatch(timeElapsed);
        }
        //startStopWatch(timeElapsed);
    }


    private void resetStopWatch()
    {
        stopWatch = null;
        fragment.setStopWatchTimeElapsed(0);
        //set stopWatchView to 0
    }



    private void ifPaused() // called on pause button click
    {
        isPaused = !isPaused;
        if(isPaused)
        {
            stopWatch = null;
            //change button color
        }
        else
        {
            //change button color
            startStopWatch(timeElapsed); //timeElapsed is kept updated by the run() and stored in fragment
        }
    }

    private void setLapped()
    {
        isLapped = !isLapped;
    }
}
