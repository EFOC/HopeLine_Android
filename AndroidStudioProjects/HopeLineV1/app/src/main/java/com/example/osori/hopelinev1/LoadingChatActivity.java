package com.example.osori.hopelinev1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osori.hopelinev1.Activities.CommonActivities.UserChatActivity;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LoadingChatActivity extends AppCompatActivity {

    private String connectionString;
    private HubConnection hubConnection = HubConnectionBuilder.create("https://hopelineapi.azurewebsites.net/v2/chathub").build();
    private TextView tvLoadingChat;
    private Button btn_cancel;
    private boolean connected;
    private String room;
    private String guestName;
    int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_chat);

        Random random = new Random();
        guestName = "Guest" + random.nextInt(999999) + 1000;
        Log.d("LoadingChat", "guest name " + guestName);
        tvLoadingChat = (TextView)findViewById(R.id.tv_loadingChat);
        time = 0;
        btn_cancel = (Button)findViewById(R.id.btn_cancelChat);
        Log.d("LoadingChat", "Started");

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoadingChatActivity.this, "Canceling...", Toast.LENGTH_SHORT).show();
                try{
                    TimeUnit.SECONDS.sleep(3);
                    LoadingChatActivity.this.finish();
                }catch (Exception e){
                    Log.d("LoadingChat", "Error: " + e);
                }

            }
        });

        hubConnection.on("Room", (room)->{

            Log.d("LoadingChat", "room " + room);
            if(!room.isEmpty()){
                LoadingChatActivity.this.room = room;
                LoadingChatActivity.this.connected = true;
            }
        }, String.class);

        hubConnection.on("NotifyUser", (connected)->{
            Log.d("LoadingChat", "1 Connecting " + connected);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Log.d("LoadingChat", "Connecting " + connected);
                    if(connected.contains("Connected")){
                        Log.d("LoadingChat", "Connected " + connected);
                        LoadingChatActivity.this.connected = true;
                        Log.d("LoadingChat", "is connected " + LoadingChatActivity.this.connected);
                    }
                }
            });
        }, String.class);
        new HubConnectionTask().execute(hubConnection);
    }




    class HubConnectionTask extends AsyncTask<HubConnection, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("LoadingChat", "onPost");
            if(connected){
                Log.d("LoadingChat", "Connecting");
                Toast.makeText(LoadingChatActivity.this, "Found mentor... connecting...", Toast.LENGTH_SHORT).show();
                try{
                    TimeUnit.SECONDS.sleep(3);
                    Intent intent = new Intent(LoadingChatActivity.this, UserChatActivity.class);
                    intent.putExtra("room", room);
                    intent.putExtra("guestName", guestName);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    Log.d("LoadingChat", "Error: " + e);
                }
            }else if(time == 10){
                Log.d("LoadingChat", "time is over 10");
                Toast.makeText(LoadingChatActivity.this, "Could not find mentor...", Toast.LENGTH_SHORT).show();
                try{
                    TimeUnit.SECONDS.sleep(3);
                }catch (Exception e){
                    Log.d("LoadingChat", "Error: " + e);
                }
                finish();
            }
            Log.d("LoadingChat", "after if statements, connected: " + connected + " time: " + time);
        }

        @Override
        protected Void doInBackground(HubConnection... hubConnections) {
            HubConnection hubConnection = hubConnections[0];
            hubConnection.start().blockingAwait();
            connected = false;
            time = 0;
            Log.d("LoadingChat", "before loop");
            while(!connected && time != 10){

                try{
                    TimeUnit.SECONDS.sleep(5);
                }catch(Exception e){
                    Log.d("LoadingChat", "Error");
                }
                hubConnection.invoke(String.class, "RequestToTalk", guestName);
                time++;
            }


            return null;
        }
    }
}
