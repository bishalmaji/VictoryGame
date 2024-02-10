package com.victory.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.victory.game.R;

import java.util.Objects;

public class Feedback extends AppCompatActivity {
    ImageView addFeedBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ImageView back=findViewById(R.id.fBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addFeedBack=findViewById(R.id.f_add_feedback);
        addFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  openFeedbackDialog();
            }
        });
    }
    private void openFeedbackDialog() {
        final Dialog dialog = new Dialog(Feedback.this);
        dialog.setContentView(R.layout.add_complain_suggest);
        Objects.requireNonNull(dialog.getWindow()).setWindowAnimations(R.style.DialogAnimation);


        final EditText typeEditText = dialog.findViewById(R.id.d_type);
        final EditText outidEditText = dialog.findViewById(R.id.outidEditText);
        final EditText whatsappEditText = dialog.findViewById(R.id.dwhatsapp);
        final EditText descriptionEditText = dialog.findViewById(R.id.dDesc);
        Button sendButton = dialog.findViewById(R.id.dsend);
        Button cancelButton = dialog.findViewById(R.id.dcancle);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here you can handle the send button click
                String type = typeEditText.getText().toString();
                String outid = outidEditText.getText().toString();
                String whatsapp = whatsappEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                // Check if any of the fields are empty
                if (type.isEmpty() || outid.isEmpty() || whatsapp.isEmpty() || description.isEmpty()) {
                    // Display an error message or toast indicating that all fields are required
                    Toast.makeText(Feedback.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    sendFeedBackRequestApi();
                    dialog.dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here you can handle the cancel button click
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void sendFeedBackRequestApi() {
    }
}