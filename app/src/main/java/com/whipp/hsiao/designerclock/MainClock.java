package com.whipp.hsiao.designerclock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainClock extends Activity {

    private TextView clockText;
    private TextView dateText;
    private WatchViewStub stub;
    private SharedPreferences prefs;
    private Context context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_clock);
        stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        context = this;
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            public void onLayoutInflated(WatchViewStub stub) {
                clockText = (TextView) stub.findViewById(R.id.clock);
                dateText = (TextView) stub.findViewById(R.id.date);

            }
        });
        stub.inflate();

        new UpdateClock().execute(this);
        stub.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent myIntent = new Intent(context, Settings.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(myIntent);
                new endActivity().execute(context);
            }
        });
    }


    /*
     *
     */

    private class UpdateClock extends AsyncTask<Context, Integer, Boolean> {

        protected Boolean doInBackground(Context... context) {
            new Timer().scheduleAtFixedRate(new TimerTask(){
                public void run() {
                    runOnUiThread(new Runnable(){
                        public void run() {
                            Calendar time;
                            time = Calendar.getInstance();
                            int hour;
                            String mode = prefs.getString("mode","12");
                            if(mode.equals("12")){
                                hour = time.get(Calendar.HOUR);
                            }else{
                                hour = time.get(Calendar.HOUR_OF_DAY);

                            }
                            int minute = time.get(Calendar.MINUTE);
                            int sec = time.get(Calendar.SECOND);
                            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                            Date d = new Date();
                            String day = sdf.format(d);
                            sdf = new SimpleDateFormat("MM/dd/yyyy");
                            String date = sdf.format(d);
                            dateText.setText(day + ", " + date);
                            clockText.setText(hour + ":" + String.format("%02d", minute) + ":" + String.format("%02d", sec));
                            if(mode.equals("12"))
                                if(time.get(Calendar.HOUR_OF_DAY) < 12){
                                    clockText.append(" AM");
                                }else{
                                    clockText.append(" PM");
                                }
                        }});
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
