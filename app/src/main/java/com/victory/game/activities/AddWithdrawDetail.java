package com.victory.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.victory.game.R;
import com.victory.game.utils.AppDataUtil;

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
                    String upi= upiEt.getText().toString().trim();
                    String uname=username.getText().toString().trim();
                    String umobile=mobileEt.getText().toString().trim();

                    if(upi.isEmpty()||
                            uname.isEmpty()||
                            umobile.isEmpty()){
                        Toast.makeText(AddWithdrawDetail.this, "All Fields Required", Toast.LENGTH_SHORT).show();
                    }else{
                        runUPISave(upi,uname,umobile);
                    }
                }else{
                    String ifsc=ifscBank.getText().toString().trim();
                    String uname=username.getText().toString().trim();
                    String umobile=mobileEt.getText().toString().trim();
                    String ubaccount=accountBank.getText().toString().trim();
                    String ubname=nameBank.getText().toString().trim();
                    if(ifsc.isEmpty()||
                            uname.isEmpty()||
                            umobile.isEmpty()||
                            ubaccount.isEmpty()||ubname.isEmpty()){
                        Toast.makeText(AddWithdrawDetail.this, "All Fields Required", Toast.LENGTH_SHORT).show();
                    }else{
                        runBankSave(ifsc,uname,umobile,ubaccount,ubname);
                    }
                }
            }
        });
    }

    private void runBankSave(String ifsc, String uname, String umobile, String ubaccount, String ubname) {
        AppDataUtil appDataUtil = AppDataUtil.getInstance(getApplicationContext());
        appDataUtil.setBooleanData(true,"wbank");
        appDataUtil.setStringData(ifsc,"wifsc");
        appDataUtil.setStringData(uname,"wname");
        appDataUtil.setStringData(umobile,"wmobile");
        appDataUtil.setStringData(ubaccount,"waccountbank");
        appDataUtil.setStringData(ubname,"wnamebank");

        appDataUtil.setBooleanData(false,"wisupi");
        appDataUtil.setStringData("","wupi");


        finish();

    }

    private void runUPISave(String upi, String uname, String umobile) {
        AppDataUtil appDataUtil = AppDataUtil.getInstance(getApplicationContext());
        appDataUtil.setStringData("","wifsc");
        appDataUtil.setStringData("","waccountbank");
        appDataUtil.setStringData("","wnamebank");
        appDataUtil.setBooleanData(false,"wbank");

        appDataUtil.setBooleanData(true,"wisupi");
        appDataUtil.setStringData(upi,"wupi");
        appDataUtil.setStringData(uname,"wname");
        appDataUtil.setStringData(umobile,"wmobile");
        finish();

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