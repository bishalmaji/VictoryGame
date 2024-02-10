package com.victory.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.victory.game.R;

import java.util.Objects;

public class Address extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ImageView back=findViewById(R.id.addr_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView addAddress=findViewById(R.id.addr_add);
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             openAddressDialog();
            }
        });
    }
    private void openAddressDialog() {
        final Dialog dialog = new Dialog(Address.this);
        dialog.setContentView(R.layout.add_address_dialog);
        Objects.requireNonNull(dialog.getWindow()).setWindowAnimations(R.style.DialogAnimation);

        // Set dialog window attributes to make it full screen
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(dialog.getWindow().getAttributes());
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);

        final EditText fullname = dialog.findViewById(R.id.a_fnmae);
        final EditText pincode = dialog.findViewById(R.id.a_pin);
        final EditText state = dialog.findViewById(R.id.a_state);
        final EditText town = dialog.findViewById(R.id.a_town);
        final EditText detailaddress = dialog.findViewById(R.id.a_adr_dtail);

        Button sendButton = dialog.findViewById(R.id.asend);
        Button cancelButton = dialog.findViewById(R.id.acancle);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here you can handle the send button click
                String type = fullname.getText().toString();
                String outid = pincode.getText().toString();
                String whatsapp = state.getText().toString();
                String description = town.getText().toString();
                String detail = detailaddress.getText().toString();
                // Check if any of the fields are empty
                if (type.isEmpty() || outid.isEmpty() || whatsapp.isEmpty() || description.isEmpty()|| detail.isEmpty()) {
                    // Display an error message or toast indicating that all fields are required
                    Toast.makeText(Address.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
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

}