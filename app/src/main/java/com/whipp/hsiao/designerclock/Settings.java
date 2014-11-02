package com.whipp.hsiao.designerclock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.Timer;
import java.util.TimerTask;

public class Settings extends Activity {

    Button back;
    CheckBox standard;
    CheckBox military;
    CheckBox basic;
    CheckBox fancy;

    SharedPreferences prefs;
    SharedPreferences.Editor edit;

    private Context context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context = this;

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            public void onLayoutInflated(WatchViewStub stub) {
                back = (Button) (stub.findViewById(R.id.back_button));
                standard = (CheckBox) (stub.findViewById(R.id.radio12));
                military = (CheckBox) (stub.findViewById(R.id.radio24));
                basic = (CheckBox) (stub.findViewById(R.id.basic_radio));
                fancy = (CheckBox) (stub.findViewById(R.id.fancy_radio));
            }
        });
        stub.inflate();

        prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        edit = prefs.edit();

        new checkButtons().execute(this);

    }

    private class checkButtons extends AsyncTask<Context, Integer, Boolean> {

        protected Boolean doInBackground(Context... context) {
            new Timer().schedule(new TimerTask(){
                public void run() {
                    runOnUiThread(new Runnable(){
                        public void run() {
                            Log.d("Clock", prefs.getString("clock","basic"));
                            if(prefs.getString("mode","12").equals("12")) {
                                standard.setChecked(true);
                                military.setChecked(false);
                                Log.d("check",String.valueOf(basic.isChecked()));
                            }else{
                                military.setChecked(true);
                                standard.setChecked(false);
                            }

                            Log.d("Mode", prefs.getString("mode", "12"));
                            if(prefs.getString("clock","basic").equals("basic")){
                                basic.setChecked(true);
                                fancy.setChecked(false);
                            }else{
                                basic.setChecked(false);
                                fancy.setChecked(true);
                            }
                        }});
                }
            }, 0);

            return true;
        }
    }

    public void backClick(View view) {
        Log.d("Click","Back");
        if(prefs.getString("clock", "basic").equals("basic")){
            Intent myIntent = new Intent(context, MainClock.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(myIntent);
            new endActivity().execute(context);
        }else{
            Intent myIntent = new Intent(context, DesignClock.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(myIntent);
            new endActivity().execute(context);
        }
    }

    public void basicClick(View view){
        basic.setChecked(true);
        edit.putString("clock","basic").apply();
        fancy.setChecked(false);
    }

    public void fancyClick(View view){
        basic.setChecked(false);
        edit.putString("clock","fancy").apply();
        fancy.setChecked(true);
    }

    public void standardClick(View view){
        standard.setChecked(true);
        edit.putString("mode", "12").apply();
        military.setChecked(false);
    }

    public void militaryClick(View view){
        standard.setChecked(false);
        edit.putString("mode", "24").apply();
        military.setChecked(true);
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
