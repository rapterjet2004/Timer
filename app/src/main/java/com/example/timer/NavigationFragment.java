package com.example.timer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class NavigationFragment extends Fragment implements View.OnClickListener {
    final String TAG = "NavigationFragment";
    View view;
    Button button1, button2;
    static int hours, minutes, seconds;
    boolean timerEnded = true;
    private static long testy = 0;
    private static long stopWatchTimeElapsed = 0;

    public NavigationFragment() {
        super(R.layout.fragment_navigation);

    }
    public void setTesty(long param)
    {
        testy = param;
    }

    public long getTesty()
    {
        return testy;
    }

    public void setStopWatchTimeElapsed(long param)
    {
        stopWatchTimeElapsed = param;
    }

    public long getStopWatchTimeElapsed()
    {
        return stopWatchTimeElapsed;
    }

    public void setTime(int h, int m, int s)
    {
        hours = h;
        minutes = m;
        seconds = s;
    }

    public int getHours(){
        return hours;
    }

    public int getMinutes(){
        return minutes;
    }

    public int getSeconds(){
        return seconds;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setRetainInstance(true);
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);
        button1 = (Button) view.findViewById(R.id.button1);
        button2 = (Button) view.findViewById(R.id.button2);
        //button3 = (Button) view.findViewById(R.id.button3);
        //button4 = (Button) view.findViewById(R.id.button4);

        Button[] buttonArray = {button1, button2};
        buttonHelper(buttonArray);

        return view;
    }

    private void buttonHelper(Button[] buttons)
    {

        for (Button button : buttons)
        {
            button.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View button) {
        Log.i(TAG, "NavigationFragment Timer Ended?: " + timerEnded);
        switch(button.getId())
        {
            case R.id.button1:
                if(timerEnded) {
                    Log.i(TAG, "Direct to MainActivity.java");
                    Intent intent = new Intent(getActivity(), SecondActivity.class);
                    getActivity().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));//, 0);
                }
                else
                {
                    Log.i(TAG, "Direct to SecondActivity.java");
                    Intent intent1 = new Intent(getActivity(), SecondActivity.class);
                    getActivity().startActivityIfNeeded(intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION), 0);

                }

                break;
            case R.id.button2:
                Log.i(TAG, "Direct to StopWatchActivity.java");
                Intent intent1 = new Intent(getActivity(), StopWatchActivity.class);
                getActivity().startActivityIfNeeded(intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION), 0);

                break;
            /*
            case R.id.button3:
                Log.i(TAG, "button3");
                break;
            case R.id.button4:
                Log.i(TAG, "button4");
                break;

             */
        }

    }

    public void setTimerEnded(boolean msg)
    {
        //Log.i(TAG, "setTimerEnded was called");
        timerEnded = msg;
    }

    private void updateActivity()
    {
        // Fragments are hosted by activities,
        // If getActivity() is how the intent tells which activity is hosting the fragment
        // then I can make a method that can account for this when switching through the
        // navigation bar

        Intent intent = new Intent(getActivity(), SecondActivity.class);
        getActivity().startActivityIfNeeded(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION), 0);


    }
}
