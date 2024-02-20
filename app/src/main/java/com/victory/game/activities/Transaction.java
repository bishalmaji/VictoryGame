package com.victory.game.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.victory.game.R;
import com.victory.game.RetrofitClientWithToken;
import com.victory.game.adapters.PaymentAdapter;
import com.victory.game.adapters.RecordWinAdapter;
import com.victory.game.interfaces.ApiService;
import com.victory.game.models.UserPaymentModel;
import com.victory.game.models.UserPaymentResponseModel;
import com.victory.game.models.UserRecordResponseModel;
import com.victory.game.utils.AppDataUtil;
import com.victory.game.utils.CustomProgressDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Transaction extends AppCompatActivity {
    PaymentAdapter paymenAdapter;
    List<UserPaymentModel> paymentRecord=new ArrayList<>();
    RecyclerView payRecycler;
    private CustomProgressDialog progressDialog;
    private String TAG="trans";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        ImageView back=findViewById(R.id.tBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        payRecycler=findViewById(R.id.t_recycler);
        payRecycler.setLayoutManager(new LinearLayoutManager(this));
        progressDialog = new CustomProgressDialog(this);
        progressDialog.show();
        getPayments();

    }
    private void getPayments() {
         AppDataUtil appDataUtil = AppDataUtil.getInstance(Transaction.this);
        String encodedToken = appDataUtil.getStringData("token").trim();
        String u_id = appDataUtil.getStringData("user_uid").trim();
        String u_decodedToken = appDataUtil.decodeString(encodedToken);
        ApiService apiService = RetrofitClientWithToken.getApiService(u_decodedToken);
        Call<UserPaymentResponseModel> modelCall = apiService.getPaymentsByUser(u_decodedToken, u_id);
        modelCall.enqueue(new Callback<UserPaymentResponseModel>() {
            @Override
            public void onResponse(Call<UserPaymentResponseModel> call, Response<UserPaymentResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                     paymentRecord = response.body().getData();
                    if (paymentRecord != null && !paymentRecord.isEmpty()) {
                        paymenAdapter = new PaymentAdapter(paymentRecord, Transaction.this);
                        payRecycler.setAdapter(paymenAdapter);
                    }
                }
                Log.e(TAG, "onResponse: "+response );
                progressDialog.hide();
            }

            @Override
            public void onFailure(Call<UserPaymentResponseModel> call, Throwable t) {
                Log.e(TAG, "fail= "+t.getMessage() );

                Toast.makeText(Transaction.this, "Fail to get Transaction", Toast.LENGTH_SHORT).show();
              progressDialog.show();
            }
        });


    }
}