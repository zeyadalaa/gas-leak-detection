package com.example.gasleakdetection;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.macroyau.thingspeakandroid.model.ChannelFeed;


//import com.macroyau.thingspeakandroid.ThingSpeakChannel;
import com.macroyau.thingspeakandroid.model.ChannelFeed;
public class MainActivity2 extends AppCompatActivity {

    ThingSpeakChannel tsChannel ;
    Button botton;
    Button botton2;
    ProgressBar PB;
    TextView dataReceived;
    TextView state;
    ProgressBar PB2;
    TextView dataReceived2;
    HorizontalScrollView HSView;
    LinearLayout LinLayHScroll;
    long ServerCHANNEL_ID= 1277610;
    String CHANNEL_ID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        getSupportActionBar().hide();
        dataReceived = (TextView) findViewById(R.id.dataReceived);
        PB = (ProgressBar) findViewById(R.id.progressBar);
        PB.setProgress(0);
        dataReceived2 = (TextView) findViewById(R.id.dataReceived4);
        PB2 = (ProgressBar) findViewById(R.id.progressBar4);
        PB2.setProgress(0);
        dataReceived2.setText("Closed");
        tsChannel = new ThingSpeakChannel(ServerCHANNEL_ID);
        botton= (Button) findViewById(R.id.button);
        HSView = findViewById(R.id.Hscroll);
        LinLayHScroll = findViewById(R.id.LinHscroll);
        tsChannel.loadChannelFeed();
        state = (TextView) findViewById(R.id.state);

        botton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String per = tsChannel.loadLastEntryInChannelFeed();
                if(per!=null) {
                    Log.v("ss",per+"");
                    String[] data = per.split(" ");
                    int num = Integer.parseInt(data[0]);
                    dataReceived.setText(data[0]);
                    PB.setProgress(num);
                    num = Integer.parseInt(data[1]);
                    dataReceived2.setText(data[1]);
                    PB2.setProgress(num);
                    addGasReading(data);
                }
            }
        });

        botton2= (Button) findViewById(R.id.valve);

        botton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(botton2.getText().equals("Open"))
                {
                    state.setText("Opened");
                    botton2.setText("Close");
                    PB2.setProgress(0);
                    dataReceived2.setText("Opened");
                }
                else
                {
                    state.setText("Closed");
                    botton2.setText("Open");
                    PB2.setProgress(1);
                    dataReceived2.setText("Closed");
                }
            }
        });

    }
    private  void  addGasReading(String[] data){
        TextView tv = new TextView(getApplicationContext());
        tv.setPadding(20,4,20,4);
        tv.setTextSize(54);
        tv.setText(data[0] + "\n" + data[2]);
        LinLayHScroll.addView(tv);
    }
}


