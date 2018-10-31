package com.example.osori.hopelinev1.Presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.osori.hopelinev1.Activities.CommonActivities.UserChatActivity;

public class GridPresenter extends GridLayout{

    private Button[][] buttons;
    private String TAG = "HERE";
    public GridPresenter(Context context, int rows, int columns) {
        super(context);

        Log.d(TAG,"Grid Start");
        buttons = new Button[rows][columns];

        //For testing, we are creating buttons manually

        setColumnCount(2);
        setRowCount(2);

        buttons[0][0] = new Button(context);
        buttons[0][0].setText("Talk to mentor");
        buttons[0][0].setTextSize(10);
        buttons[0][0].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UserChatActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        addView(buttons[0][0], 100, 100);


        buttons[1][1] = new Button(context);
        buttons[1][1].setText("Map");
        buttons[1][1].setTextSize(10);
        addView(buttons[1][1], 100, 100);


//        setColumnCount(columns);
//        setRowCount(rows);
//
//        for(int row = 0; row < rows; row++){
//            for(int column = 0; column < columns; column++){
//                buttons[row][column] = new Button(context);
//                buttons[row][column].setText("Test");
//                buttons[row][column].setTextSize(10);
//                addView(buttons[row][column], 100, 100);

//            }
//        }


    }
}
