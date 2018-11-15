package com.example.osori.hopelinev1.Activities.CommonActivities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osori.hopelinev1.R;
//import com.microsoft.aspnet.signalr.HubConnection;
//import com.microsoft.aspnet.signalr.HubConnectionBuilder;
//import com.microsoft.aspnet.signalr.HubConnection;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import org.webrtc.PeerConnection;
import org.webrtc.VideoCapturer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.osori.hopelinev1.Activities.CommonActivities.LoadingChatActivity.apiConnection;

public class UserChatActivity extends AppCompatActivity{

    private Button btnSend;
    private EditText inputBar;
    private LinearLayout chatBox;
    private ScrollView chatBoxScroll;
    private ImageView btnReturn;
    private HubConnection hubConnection;
    private TextView mentorName;
    private String room;
    private String guestName;
    private ArrayList<String> topicsChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        guestName = getIntent().getStringExtra("guestName");
        room = getIntent().getStringExtra("room");
        btnSend = (Button)findViewById(R.id.btn_send);
        inputBar = (EditText)findViewById(R.id.edt_chat_input);
        chatBoxScroll = (ScrollView)findViewById(R.id.txt_chat_box_scroll);
        chatBox = (LinearLayout)findViewById(R.id.txt_chat_box);
        btnReturn = (ImageView)findViewById(R.id.btn_chat_return);
        mentorName = (TextView)findViewById(R.id.txt_incoming_user_name);

        Log.d("ChatHub", "Context is: " + this.getApplicationContext());

        hubConnection = apiConnection.getHubConnection();

        hubConnection.on("ReceiveMessage", (user, message)->{
            Log.d("ChatHub", "Receiving message: " + message);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!user.equals(guestName)){
                        TextView textView = new TextView(getApplicationContext());
                        textView.setText(message);
                        chatBox.addView(textView);
                        chatBoxScroll.fullScroll(chatBoxScroll.FOCUS_DOWN);
                    }

                }
            });
        }, String.class, String.class);

        

        boolean isTopicsEmpty = getIntent().getBooleanExtra("emptyTopics", false);
        if(isTopicsEmpty){
            topicsChosen = getIntent().getStringArrayListExtra("topics");
        }


//        TODO: add the mentor's name to the top of the screen
//        mentorName.setText("");

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hubConnection.invoke(String.class,"RemoveUser", guestName, room, true);
                finish();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = inputBar.getText().toString();
                try{
                    apiConnection.sendMessage(userInput);
                    Log.d("ChatHub", "room " + room + " guestname: " + guestName);
                    TextView textView = new TextView(v.getContext());
                    textView.setText("\n" + userInput);
                    textView.setGravity(Gravity.RIGHT);
                    textView.setTextColor(Color.BLUE);
                    chatBox.addView(textView);
                    chatBoxScroll.fullScroll(chatBoxScroll.FOCUS_DOWN);
                    inputBar.setText("");
                }catch (Exception e){
                    Log.d("Connection Error: ", e.toString());
                    Toast.makeText(UserChatActivity.this, "Can't send message, not connected to server...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

