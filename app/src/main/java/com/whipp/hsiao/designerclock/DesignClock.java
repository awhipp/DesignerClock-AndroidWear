package com.whipp.hsiao.designerclock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class DesignClock extends Activity {

    ClockView clockView;
    Context context;
    Handler uiHandler = new Handler();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_clock);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        context = this;


        Calendar time;
        time = Calendar.getInstance();
        int hour = time.get(Calendar.HOUR_OF_DAY);
        int minute = time.get(Calendar.MINUTE);
        int sec = time.get(Calendar.SECOND);

        clockView = (com.whipp.hsiao.designerclock.ClockView) findViewById(R.id.draw);
        clockView.updateView(hour, minute, sec, hour < 12);


        layout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent myIntent = new Intent(context, Settings.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(myIntent);
                new endActivity().execute(context);
            }
        });


        new UpdateClock().execute(this);


    }


    private class UpdateClock extends AsyncTask<Context, Integer, Boolean> {

        protected Boolean doInBackground(Context... context) {
            new Timer().scheduleAtFixedRate(new TimerTask(){
                public void run() {
                    final Calendar time = Calendar.getInstance();
                    final int hour = time.get(Calendar.HOUR_OF_DAY);
                    final int minute = time.get(Calendar.MINUTE);
                    final int sec = time.get(Calendar.SECOND);
                    uiHandler.post(new Runnable(){
                       public void run(){
                           clockView.updateView(hour, minute, sec, hour < 12);
                        }
                    });

                }
            }, 0, 500);

            return true;
        }
    }

    private class endActivity extends AsyncTask<Context, Integer, Boolean> {

        protected Boolean doInBackground(Context... context) {
            new Timer().schedule(new TimerTask(){
                public void run() {
                    runOnUiThread(new Runnable(){
                        public void run() {
                            finish();
                        }});
                }
            }, 500);

            return true;
        }
    }
}
