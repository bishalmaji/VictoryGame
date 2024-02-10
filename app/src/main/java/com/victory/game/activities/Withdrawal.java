package com.victory.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.victory.game.R;
import com.victory.game.RetrofitClientWithToken;
import com.victory.game.interfaces.ApiService;
import com.victory.game.models.AddWithdrawalRequestModel;
import com.victory.game.models.CommonResponseModel;
import com.victory.game.models.PUpdateRequestModel;
import com.victory.game.utils.AppDataUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Withdrawal extends AppCompatActivity {
    TextView balance,addBank,addUpi;
    EditText amount, withdrawPassword;
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
        addBank=findViewById(R.id.w_add_bank);
        addUpi=findViewById(R.id.w_add_upi);
        amount=findViewById(R.id.w_amount);
        withdrawPassword=findViewById(R.id.w_password);
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
    private void addWithdrawlRequest(){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //this is withdrawal request
                AppDataUtil appDataUtil = AppDataUtil.getInstance(Withdrawal.this.getApplicationContext());
                String encodedToken = appDataUtil.getStringData("token").trim();
                String userID = appDataUtil.getStringData("uid").trim();

                String decodedToken = appDataUtil.decodeString(encodedToken);
                AddWithdrawalRequestModel withdrawlRequestModel=new AddWithdrawalRequestModel(userID,"100","bal","fdlks","fsdk","ksd");
                ApiService apiService= RetrofitClientWithToken.getApiService(decodedToken);
                Call<CommonResponseModel> call= apiService.createWithdrawalRequest(decodedToken,withdrawlRequestModel);
                call.enqueue(new Callback<CommonResponseModel>() {
                    @Override
                    public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {

                    }

                    @Override
                    public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                    }
                });
            }
        });
    }
}