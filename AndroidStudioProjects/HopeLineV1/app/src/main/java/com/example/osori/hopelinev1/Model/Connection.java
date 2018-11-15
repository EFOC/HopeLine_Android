package com.example.osori.hopelinev1.Model;

import android.content.Context;
import android.os.AsyncTask;

import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnection;

public class Connection extends AsyncTask<HubConnection, Context, Void> {
    private HubConnection hubConnection = HubConnectionBuilder.create("https://hopelineapi.azurewebsites.net/v2/chathub").build();
    private boolean connected;
    private int time;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
//        Toast.makeText(, "", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(HubConnection... hubConnections) {
        HubConnection hubConnection = hubConnections[0];
        hubConnection.start().blockingAwait();

//        connected = false;
//        time = 0;
//        Log.d("LoadingChat", "before loop");
//        while(!connected && time != 10){
//
//            try{
//                TimeUnit.SECONDS.sleep(5);
//            }catch(Exception e){
//                Log.d("LoadingChat", "Error");
//            }
//            hubConnection.invoke(String.class, "RequestToTalk", guestName);
//            time++;
//        }
        return null;
    }
}
