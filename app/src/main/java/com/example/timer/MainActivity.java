package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author Julius Linus
 *
 * MainActivity.java is the launcher activity for Timer. It's responsible for
 * the retrieving and handling of user input from activity_main.xml, and sending the
 * data to SecondActivity.java.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Private global int variable to represent the hour, minute, and seconds
     * the user inputs
     */
    private int hour, minute, second;

    /**
     * Private global EditText variables, meant to connect to and retrieve data from
     * the EditText's in activity_main.xml
     */
    private EditText hourInput, minuteInput, secondInput;

    /**
     * Private global Button variable, meant to send user input data to SecondActivity.java
     */
    private Button setTimer;

    /**
     * Intent variable, to handle sending data to SecondActivity.java
     */
    Intent intent;

    /**
     * Initializes the activity, Button onClickListeners, and sets Content View to
     * activity_main.xml
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
          setTimer is initialized and given an onClickListener. Listener calls
          gatherInput() to get user input from EditText widget setTimerBtn
          at activity_main.xml, and sendInput() to send user input into
          SecondActivity.java
         */
        setTimer = findViewById(R.id.setTimerBtn);
        setTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gatherInput();
                sendInput();
            }
        });

    }

    /**
     * initializes the global EditText variables before parsing and storing their input
     * in the global int variables hour, minute, and second.
     */
    private void gatherInput()
    {
        hourInput = findViewById(R.id.hours);
        minuteInput = findViewById(R.id.minutes);
        secondInput = findViewById(R.id.seconds);

        /*
          Data from EditTexts are not string by default and need to be converted
          before being parsed into int
         */
        hour = Integer.parseInt(hourInput.getText().toString());
        minute = Integer.parseInt(minuteInput.getText().toString());
        second = Integer.parseInt(secondInput.getText().toString());
    }

    /**
     * Initializes the global intent variable and stores the user data along with it
     * using the putExtra() method, before starting the SecondActivity.java using the
     * startActivity() method.
     */
    private void sendInput()
    {
        intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("HourInput", hour);
        intent.putExtra("MinuteInput", minute);
        intent.putExtra("SecondInput", second);
        startActivity(intent);
    }
}