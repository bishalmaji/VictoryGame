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
import com.victory.game.adapters.RecordWinAdapter;
import com.victory.game.adapters.ReferHistoryAdapter;
import com.victory.game.interfaces.ApiService;
import com.victory.game.models.ReferalResponseModel;
import com.victory.game.models.ReferralCommonModel;
import com.victory.game.models.UserRecordResponseModel;
import com.victory.game.utils.AppDataUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferHistory extends AppCompatActivity {
    RecyclerView recyclerView;
    ReferHistoryAdapter adapter;
    List<ReferalResponseModel> models;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_history);
        ImageView back=findViewById(R.id.rh_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
finish();
            }
        });
         recyclerView=findViewById(R.id.rhrecycler);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         getRecord();

    }
    private void getRecord() {
        AppDataUtil appDataUtil=AppDataUtil.getInstance(getApplicationContext());
        String encodedToken = appDataUtil.getStringData("token").trim();
        String u_id = appDataUtil.getStringData("user_uid").trim();

        String decodedToken = appDataUtil.decodeString(encodedToken);
        ApiService apiService = RetrofitClientWithToken.getApiService(decodedToken);
        Call<ReferralCommonModel> modelCall = apiService.getReferOfUser(decodedToken, u_id);
        modelCall.enqueue(new Callback<ReferralCommonModel>() {
            @Override
            public void onResponse(Call<ReferralCommonModel> call, Response<ReferralCommonModel> response) {
                if(response.isSuccessful() && response.body()!=null){
                    models=response.body().getData();
                    appDataUtil.setIntData(models.size(),"total_referal");
                    adapter=new ReferHistoryAdapter(models,ReferHistory.this);
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(ReferHistory.this, "No Referral Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReferralCommonModel> call, Throwable t) {

            }
        });


    }
}