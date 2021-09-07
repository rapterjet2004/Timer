# Timer
A small Timer app with a clean simple UI

# Paths
Path to Java files is app>>src>>main>>java/com/example/timer

Path to layout resource files is app>>src>>main>>res>>layout

# Short gist of how it works
The app has two activitys, the launcher activity which collects user input, and the second activity which displays the timer. 
These activitys are connected to their layouts via the setContentView() method near the top of the onCreate() method in every
activity. 

The launcher activity gathers input using 3 editText widgets corresponding to the hour, minute, and time the user inputs. 

The second activity is just a textView widget which gets it's text reset every 1000 milliseconds via the android CountDownTimer class, using
the setText() method. 

There is a button in the launcher activity that starts the timer(Sends data from launcher activity to next activity, and starts SecondActivity.java)

There are buttons in the second activity that stop the timer, and reset the timer(returns user to the launcher activity)

# How to run it
The app isn't on the play store, so you'll have to download android studio to run it. It should be pretty light weight. 






