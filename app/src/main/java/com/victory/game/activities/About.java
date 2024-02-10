package com.victory.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.victory.game.R;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ImageView back=findViewById(R.id.aboutBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();

        // Get the string extra data from the intent
        String title = intent.getStringExtra("Atitle");
        String desc = intent.getStringExtra("Adesc");
        TextView titleTv=findViewById(R.id.a_title);
        TextView descTv=findViewById(R.id.a_desc);
        titleTv.setText(title);
        descTv.setText(desc);
    }
}