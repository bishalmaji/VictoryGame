package com.victory.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.victory.game.fragments.Login;
import com.victory.game.fragments.Profile;
import com.victory.game.fragments.Register;
import com.victory.game.fragments.Win;
import com.victory.game.interfaces.ApiService;

import com.victory.game.models.CurrentUserResponseModel;


import com.victory.game.models.ResultUserModel;
import com.victory.game.utils.AppDataUtil;
import com.victory.game.utils.MessageDialog;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Profile.ChangeMainViewListener, Win.ChangeMainViewListener, Register.OnMenuHiddenListener, Login.OnMenuHiddenListener {
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDataUtil appDataUtil = AppDataUtil.getInstance(getApplicationContext());

//        referal system
//        Intent intent = getIntent();
//        Uri data = intent.getData();
//
//        if (data != null) {
//            String dataParam = data.getQueryParameter("data");
//            if (dataParam != null) {
//                Log.d("MyApp", "Data from URL: " + dataParam);
//                Toast.makeText(this, "referral code="+dataParam, Toast.LENGTH_SHORT).show();
//            }
//        }

        bottomNavigationView = findViewById(R.id.main_bottom_nav);

        bottomNavigationView.setItemActiveIndicatorEnabled(false);
        bottomNavigationView.setItemIconTintList(null);


        navController = Navigation.findNavController(this, R.id.nav_container);
        if (appDataUtil.isLoggedIn()) {
            String encodedToken = appDataUtil.getStringData("token").trim();
            String decodedToken = appDataUtil.decodeString(encodedToken);
            getCurrentUserWithToken(decodedToken);
            bottomNavigationView.getMenu().findItem(R.id.profile).setVisible(true);
            bottomNavigationView.getMenu().findItem(R.id.win).setVisible(true);
            bottomNavigationView.getMenu().findItem(R.id.login).setVisible(false);
            MessageDialog.showAlert(this,"Alert","Dear Victoryshop.in members. About us - www.victoryshop.in is Big " +
                    "Investment platform where anyone can come and get profit easily. Customer Service Team.\n Email:victoryvip@victoryvip.com");

        } else {
            runNotLoggedIn();
        }
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }


    private void getCurrentUserWithToken(String token) {
        Log.e("bkmmmmm", "decoded token: "+token );
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                ApiService apiService = RetrofitClientWithToken.getApiService(token);
                AppDataUtil appDataUtil = AppDataUtil.getInstance(getApplicationContext());
                Call<CurrentUserResponseModel> call = apiService.getCurrentUser("Bearer " + token);

                call.enqueue(new Callback<CurrentUserResponseModel>() {
                    @Override
                    public void onResponse(Call<CurrentUserResponseModel> call, Response<CurrentUserResponseModel> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            ResultUserModel data = response.body().getData();
                            appDataUtil.setStringData(data.getUid(), "user_uid");
                            appDataUtil.setStringData(data.getName(), "user_name");
                            appDataUtil.setStringData(data.getPhone(), "user_phone");
                            appDataUtil.setIntData(0, "user_amount");
                            Log.e("bige", "not log: "+response);

                        } else {
                            runNotLoggedIn();
                            Log.e("bige", "not log: "+response);

                        }
                    }

                    @Override
                    public void onFailure(Call<CurrentUserResponseModel> call, Throwable t) {
                        Log.e("bige", "onResponse:kjhgkjhkkjh "+t.getMessage());
                        runNotLoggedIn();
                    }
                });

            }
        });


    }


    private void runNotLoggedIn() {
        AppDataUtil appDataUtil=AppDataUtil.getInstance(getApplicationContext());
        appDataUtil.setBooleanData(false, "login");



        bottomNavigationView.getMenu().findItem(R.id.profile).setVisible(false);
        bottomNavigationView.getMenu().findItem(R.id.win).setVisible(false);
        bottomNavigationView.getMenu().findItem(R.id.login).setVisible(true);


    }

    private void runLoggedIn() {
        AppDataUtil appDataUtil=AppDataUtil.getInstance(getApplicationContext());
        appDataUtil.setBooleanData(true, "login");
        String encodedToken = appDataUtil.getStringData("token").trim();
        String decodedToken = appDataUtil.decodeString(encodedToken);
        Toast.makeText(this, ""+decodedToken, Toast.LENGTH_SHORT).show();

        getCurrentUserWithToken(decodedToken);
        bottomNavigationView.getMenu().findItem(R.id.profile).setVisible(true);
        bottomNavigationView.getMenu().findItem(R.id.win).setVisible(true);
        bottomNavigationView.getMenu().findItem(R.id.login).setVisible(false);

    }

    @Override
    public void registerSuccess() {
       runLoggedIn();
        navController.popBackStack(R.id.home, false);
    }


    @Override
    public void loginSuccess() {
        runLoggedIn();
        navController.popBackStack(R.id.home, false);
        Log.e("bkmmmmmmmm", "loginSuccess: ");
    }

    @Override
    public void loginFail() {
        runNotLoggedIn();
    }

    @Override
    protected void onDestroy() {
        runNotLoggedIn();
        super.onDestroy();
    }

    @Override
    public void gotoLoginWin() {
        runNotLoggedIn();
    }

    @Override
    public void gotoLoginProfile() {
        runNotLoggedIn();
        navController.popBackStack(R.id.profile, true);

    }
}