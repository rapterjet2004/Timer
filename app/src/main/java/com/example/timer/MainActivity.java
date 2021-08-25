package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private int hour, minute, second;
    private EditText hourInput, minuteInput, secondInput;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button setTimer = findViewById(R.id.setTimerBtn);
        setTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gatherInput();
                sendInput();
            }
        });

    }

    private void gatherInput()
    {
        hourInput = findViewById(R.id.hours);
        minuteInput = findViewById(R.id.minutes);
        secondInput = findViewById(R.id.seconds);

        hour = Integer.parseInt(hourInput.getText().toString());
        minute = Integer.parseInt(minuteInput.getText().toString());
        second = Integer.parseInt(secondInput.getText().toString());
    }

    private void sendInput()
    {
        intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("HourInput", hour);
        intent.putExtra("MinuteInput", minute);
        intent.putExtra("SecondInput", second);
        startActivity(intent);
    }
}