package com.victory.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.victory.game.R;
import com.victory.game.RetrofitClientWithToken;
import com.victory.game.interfaces.ApiService;
import com.victory.game.models.AddWithdrawalRequestModel;
import com.victory.game.models.CommonResponseModel;
import com.victory.game.utils.AppDataUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Withdrawal extends AppCompatActivity {
    TextView balance,addBank,addUpi;
    String amountExt="Fee 0, Amount: 0";
    TextView amountDetail;
    EditText amount, withdrawPassword;
    int amountToWithdraw=0;
    private String TAG="withdraw";
    private AppDataUtil appDataUtil;
    private int amountd=30;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        ImageView back=findViewById(R.id.wback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        balance=findViewById(R.id.w_balance);
        appDataUtil=AppDataUtil.getInstance(getApplicationContext());
        balance.setText("Balance: "+ appDataUtil.getIntData("user_amount"));
        addBank=findViewById(R.id.w_add_bank);
        addUpi=findViewById(R.id.w_add_upi);

        amount=findViewById(R.id.w_amount);
        amountDetail=findViewById(R.id.w_amount_detail_tv);
        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              calculateFee(s.toString());
            }
        });
        withdrawPassword=findViewById(R.id.w_password);
        Button wBt=findViewById(R.id.w_btn);
        appDataUtil = AppDataUtil.getInstance(Withdrawal.this.getApplicationContext());

        wBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username,usermobile;
                AppDataUtil appDataUtil = AppDataUtil.getInstance(getApplicationContext());
                String ifsc= appDataUtil.getStringData("wifsc");
                 username=appDataUtil.getStringData("wname");
                 usermobile=appDataUtil.getStringData("wmobile");
                String useraccount=appDataUtil.getStringData("waccountbank");
                String userbankname=appDataUtil.getStringData("wnamebank");
                boolean isBank= appDataUtil.getBooleanData("wbank");

                boolean isUPI=appDataUtil.getBooleanData("wisupi");
                String upi=appDataUtil.getStringData("wupi");
                 username= appDataUtil.getStringData("wname");
                 usermobile=appDataUtil.getStringData("wmobile");

                if(amount.getText().toString().trim().isEmpty()||withdrawPassword.getText().toString().trim().isEmpty()){
                    Toast.makeText(Withdrawal.this, "All Fields Reqired", Toast.LENGTH_SHORT).show();
                }else{
                    if(isUPI && isBank){
                        if(upi.isEmpty()||usermobile.isEmpty()||username.isEmpty()||userbankname.isEmpty()||useraccount.isEmpty()||ifsc.isEmpty()){
                            Toast.makeText(Withdrawal.this, "All Fields Reqired", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }else if(isUPI){
                        if(upi.isEmpty()||usermobile.isEmpty()||username.isEmpty()){
                            Toast.makeText(Withdrawal.this, "All UPI Fields Reqired", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }else if(isBank){
                          if (usermobile.isEmpty()||username.isEmpty()||userbankname.isEmpty()||useraccount.isEmpty()||ifsc.isEmpty()){
                              Toast.makeText(Withdrawal.this, "All Bank Fields Reqired", Toast.LENGTH_SHORT).show();
                              return;
                          }
                    }else{
                        Toast.makeText(Withdrawal.this, "Add UPI Or Bank Card", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(!appDataUtil.getStringData("field_password").equals(withdrawPassword.getText().toString())){
                        Toast.makeText(Withdrawal.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(amountToWithdraw>appDataUtil.getIntData("user_amount")){
                        Toast.makeText(Withdrawal.this, "Low Balance", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    addWithdrawlRequest(amountToWithdraw,username,usermobile,ifsc,useraccount,userbankname,upi);

                }

            }
        });
        addUpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(this, .class);
                Intent intent = new Intent(Withdrawal.this, AddWithdrawDetail.class);
                // Put extra data with the intent
                intent.putExtra("title", "Add Upi Detail");
                intent.putExtra("desc", "Select UPI");
                intent.putExtra("mobile", "91");
                intent.putExtra("type","upi");
                // Start ActivityB
                startActivity(intent);

            }
        });
        addBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Withdrawal.this, AddWithdrawDetail.class);
                // Put extra data with the intent
                intent.putExtra("title", "Add Bank Card");
                intent.putExtra("desc", "Select Bank Card");
                intent.putExtra("mobile", "91");
                intent.putExtra("type","bank");
                // Start ActivityB
                startActivity(intent);
            }
        });

    }

    private void calculateFee(String amountString) {
        if (amountString.isEmpty()) {
            amountExt="Fee: 0, Amount: 0";
            amountDetail.setText(amountExt);
            return;
        }

         amountd = Integer.parseInt(amountString);
        int fee;

        if (amountd <= 1000) {
            fee = 30;
        } else if (amountd <= 10000) {
            fee = 300;
        } else if (amountd <= 100000) {
            fee = 3000;
        }else{
          fee=0;
          amountd=0;
          amount.setText("");

        }
        amountToWithdraw=amountd-fee;
        amountExt="Fee: "+fee+", Amount: "+amountToWithdraw;
        amountDetail.setText(amountExt);
    }
    @Override
    protected void onResume() {
        super.onResume();
         if(appDataUtil.getBooleanData("wisupi")){
             addUpi.setText("Upi Added");
         }
        if(appDataUtil.getBooleanData("wbank")){
            addBank.setText("Bank Added");

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appDataUtil.setBooleanData(false,"wbank");
        appDataUtil.setBooleanData(false,"wisupi");

    }

    private void addWithdrawlRequest(int amnt, String username, String usermobile, String ifsc, String useraccount, String userbankname, String upi){
       if((amnt+amountd)<230.0){
           Toast.makeText(Withdrawal.this, "Amount should be greater than 230", Toast.LENGTH_SHORT).show();
           return;
       }

        String userID = appDataUtil.getStringData("user_uid").trim();
        Log.e(TAG, "addWithdrawlRequest: data="+amnt+"name="+username+"id"+userID+"upi"+upi );
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //this is withdrawal request
                AppDataUtil appDataUtil = AppDataUtil.getInstance(Withdrawal.this.getApplicationContext());
                String encodedToken = appDataUtil.getStringData("token").trim();
                String userID = appDataUtil.getStringData("user_uid").trim();

                String decodedToken = appDataUtil.decodeString(encodedToken);
                AddWithdrawalRequestModel withdrawlRequestModel=new AddWithdrawalRequestModel(userID,String.valueOf(amnt),useraccount,ifsc,upi,username);
                ApiService apiService= RetrofitClientWithToken.getApiService(decodedToken);
                Call<CommonResponseModel> call= apiService.createWithdrawalRequest(decodedToken,withdrawlRequestModel);
                call.enqueue(new Callback<CommonResponseModel>() {
                    @Override
                    public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                        if(response.isSuccessful() && response.body()!=null){
                            String message = response.body().getMessage();

                            try {
                                // Attempt to parse the string as a number
                                int number = Integer.parseInt(message);
                                appDataUtil.setIntData(number,"user_amount");
                                Toast.makeText(Withdrawal.this, "Withdrawal Request Added", Toast.LENGTH_SHORT).show();

                            } catch (NumberFormatException e) {
                                // If parsing fails, it's not a number
                                Toast.makeText(Withdrawal.this, message, Toast.LENGTH_SHORT).show();

                            }

                            finish();

                        }else{
                            Toast.makeText(Withdrawal.this, "Withdrawal Request Fail", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                        Toast.makeText(Withdrawal.this, "Withdrawal Request Fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}