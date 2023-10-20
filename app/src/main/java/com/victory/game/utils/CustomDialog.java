package com.victory.game.utils;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.victory.game.R;
import com.victory.game.fragments.Win;

import java.util.Locale;

public class CustomDialog {

    private final Dialog dialog;
    private int value=1;
    private int money=10;

    public CustomDialog(Context context, String message, Win winFragment) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_money_layout);

        TextView messageTextView = dialog.findViewById(R.id.dialog_title);
        messageTextView.setText(message);
        TextView ten,hundred,thousand,ten_thousand,plus,minus,value_plus_minus,total_money;
        ten=dialog.findViewById(R.id.m_10);
        hundred=dialog.findViewById(R.id.m_100);
        thousand=dialog.findViewById(R.id.m_1000);
        ten_thousand=dialog.findViewById(R.id.m_10000);
        plus=dialog.findViewById(R.id.plus_tv);
        minus=dialog.findViewById(R.id.minus_tv);
        value_plus_minus=dialog.findViewById(R.id.value_tv);
        total_money=dialog.findViewById(R.id.total_money_tv);
        total_money.setText("Total Amount= "+money*value);
        ten.setBackgroundColor(Color.GREEN);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value++;
                value_plus_minus.setText(String.valueOf(value));
                total_money.setText("Total Amount= "+money*value);
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             value--;
                value_plus_minus.setText(String.valueOf(value));

                total_money.setText("Total Amount= "+money*value);

            }
        });
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money=10;
                total_money.setText("Total Amount="+money*value);
                ten.setBackgroundColor(Color.GREEN);
                thousand.setBackgroundColor(Color.WHITE);
                hundred.setBackgroundColor(Color.WHITE);
                ten_thousand.setBackgroundColor(Color.WHITE);

            }
        });
        hundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money=100;
                total_money.setText("Total Amount="+money*value);
                hundred.setBackgroundColor(Color.GREEN);
                ten.setBackgroundColor(Color.WHITE);
                thousand.setBackgroundColor(Color.WHITE);
                ten_thousand.setBackgroundColor(Color.WHITE);
            }
        });
        thousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money=1000;
                total_money.setText("Total Amount="+money*value);
                thousand.setBackgroundColor(Color.GREEN);
                ten.setBackgroundColor(Color.WHITE);
                hundred.setBackgroundColor(Color.WHITE);
                ten_thousand.setBackgroundColor(Color.WHITE);


            }
        });
        ten_thousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money=10000;
                total_money.setText("Total Amount="+money*value);
                ten_thousand.setBackgroundColor(Color.GREEN);
                ten.setBackgroundColor(Color.WHITE);
                hundred.setBackgroundColor(Color.WHITE);
                thousand.setBackgroundColor(Color.WHITE);

            }
        });


        Button okButton = dialog.findViewById(R.id.dialog_confirm_btn);
        Button cancelButton = dialog.findViewById(R.id.dialog_cancel);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // For this case update color but i will do different operation and call this
                // from other fragment
                    if (winFragment != null) {
                        winFragment.updateColorValue(message,money*value); // Replace with the desired color and value
                    }
                    dialog.cancel();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Cancel button click
                dialog.dismiss();
            }
        });
    }

    public void show() {
        // Set zoom-in animation
        Animation zoomInAnimation = AnimationUtils.loadAnimation(dialog.getContext(), R.anim.zoom_in);
        dialog.getWindow().getDecorView().startAnimation(zoomInAnimation);

        dialog.show();
    }
}
