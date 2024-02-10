package com.victory.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.victory.game.R;

public class AddWithdrawDetail extends AppCompatActivity {

   private TextView titleTv, descTv;
    private EditText username, ifscBank,accountBank, upiEt,nameBank, mobileEt;
    private String title,desc,mobile,type;
   private Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_withdraw_detail);
        titleTv=findViewById(R.id.add_w_title);
        descTv=findViewById(R.id.add_w_desc);
        
        username=findViewById(R.id.name_wa);
        ifscBank=findViewById(R.id.waifsc);
        nameBank=findViewById(R.id.waBankName);
        upiEt=findViewById(R.id.waUpi);
        mobileEt=findViewById(R.id.waMobile);
        accountBank=findViewById(R.id.waBankAccount);
        continueBtn=findViewById(R.id.continueBtn);

        Intent intent = getIntent();

        // Get the string extra data from the intent
         title = intent.getStringExtra("title");
         desc = intent.getStringExtra("desc");
         mobile = intent.getStringExtra("mobile");
         type = intent.getStringExtra("type");
        titleTv.setText(title);
        descTv.setText(desc);
        if (type != null && type.equals("upi")) {
            makeUpiDesign();
        }else{
            makeBakDesign();
        }
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type != null && type.equals("upi")) {
                    runUPISave();
                }else{
                    runBankSave();
                }
            }
        });
    }

    private void runBankSave() {
    }

    private void runUPISave() {
        
    }

    private void makeUpiDesign() {
      accountBank.setVisibility(View.GONE);
        nameBank.setVisibility(View.GONE);
        accountBank.setVisibility(View.GONE);
        ifscBank.setVisibility(View.GONE);
    }

    private void makeBakDesign() {
        upiEt.setVisibility(View.GONE);
    }
}