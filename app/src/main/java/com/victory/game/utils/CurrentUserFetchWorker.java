package com.victory.game.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.victory.game.MainActivity;
import com.victory.game.RetrofitClientWithToken;
import com.victory.game.interfaces.ApiService;
import com.victory.game.models.CurrentUserResponseModel;
import com.victory.game.models.ResultUserModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentUserFetchWorker extends Worker {

    public CurrentUserFetchWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        AppDataUtil appDataUtil = AppDataUtil.getInstance(getApplicationContext());
        // Fetch the token from SharedPreferences or other storage
        // Call the method to get the current user with the token
        String encodedToken =appDataUtil.getStringData("token").trim();
        // Decoding the token
        String decodedToken= appDataUtil.decodeString(encodedToken);
        getCurrentUserWithToken(decodedToken);
        return Result.failure();
    }



    private void getCurrentUserWithToken(String token) {

        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                AppDataUtil appDataUtil = AppDataUtil.getInstance(getApplicationContext());

                ApiService apiService = RetrofitClientWithToken.getApiService(token);

                Call<CurrentUserResponseModel> call = apiService.getCurrentUser("Bearer "+token);

                call.enqueue(new Callback<CurrentUserResponseModel>() {
                    @Override
                    public void onResponse(Call<CurrentUserResponseModel> call, Response<CurrentUserResponseModel> response) {

                        if(response.isSuccessful() && response.body()!=null){
                            ResultUserModel data = response.body().getData();
                            appDataUtil.setBooleanData(true,"login");
                            appDataUtil.setStringData(data.getUid(),"user_uid");
                            appDataUtil.setStringData(data.getName(),"user_name");
                            appDataUtil.setStringData(data.getPhone(),"user_phone");
                            appDataUtil.setIntData(data.getAmount(),"user_amount");


                        }else{

                            appDataUtil.setBooleanData(false, "login");
                        }

                    }

                    @Override
                    public void onFailure(Call<CurrentUserResponseModel> call, Throwable t) {

                        appDataUtil.setBooleanData(false, "login");
                    }
                });

            }
        });

    }

}

