package com.victory.game.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.victory.game.R;
import com.victory.game.RetrofitClient;
import com.victory.game.RetrofitClientWithToken;
import com.victory.game.activities.Recharge;
import com.victory.game.adapters.GameWinAdapter;
import com.victory.game.adapters.RecordWinAdapter;
import com.victory.game.interfaces.ApiService;
import com.victory.game.models.AddRecordRequestModel;
import com.victory.game.models.ColorUpdateRequest;
import com.victory.game.models.CommonResponseModel;
import com.victory.game.models.GameResultResponseModel;
import com.victory.game.models.ResultModel;
import com.victory.game.models.UserRecordModel;
import com.victory.game.models.UserRecordResponseModel;
import com.victory.game.utils.AppDataUtil;
import com.victory.game.utils.CustomDialog;
import com.victory.game.utils.CustomProgressDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Win extends Fragment {


    private int colorValue=100;
    private CountDownTimer countdownTimer;
    private TextView timer,current_game_id;
    private long timeDifference;
    private GameWinAdapter adapter;
    private String u_decodedToken,u_id;
    private int u_amount=0;
//    private boolean isPlayedColor=false;
//    private boolean isPlayedNumber=false;
//    private int redValue=0,greenValue=0,pinkValue=0, oneV=0,twoV=0,threeV=0,fourV=0,fiveV=0,sixV=0,sevenV=0,eightV=0
//            ,nineV=0,tenV=0;
    private RecordWinAdapter recordAdapter;
    private TextView winAvlBlnc;
    private Button winMainRechargeBtn,winReadRules;
    private ImageView winRetry;
    private ImageView gameRprev,gameRnext;


    public Win() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_win, container, false);
    }
    private Button join_green, join_red, join_pink;
    private Button one, two, three, four, five,six, seven, eight,nine,ten;
    private RecyclerView gameResultRecycler,recordRecycler;

    private ConstraintLayout gameLayout;
    private CustomProgressDialog customProgressDialog;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customProgressDialog=new CustomProgressDialog(getContext());
        if(isLoggedIn()){
            appDataUtil=AppDataUtil.getInstance(requireActivity().getApplicationContext());
            resetGlobalValues();
            String encodedToken = appDataUtil.getStringData("token").trim();
            u_decodedToken = appDataUtil.decodeString(encodedToken);
            u_id= appDataUtil.getStringData("user_uid");
            u_amount=  appDataUtil.getIntData("user_amount");
        }else{
            if (changeMainViewListener!=null){
                changeMainViewListener.gotoLoginWin();
                return;
            }
        }

        join_pink = view.findViewById(R.id.join_violet_btn);
        join_green = view.findViewById(R.id.join_green_btn);
        join_red = view.findViewById(R.id.join_red_btn);
        timer = view.findViewById(R.id.win_timer);
        current_game_id=view.findViewById(R.id.current_game_id);

        one = view.findViewById(R.id.button1);
        two = view.findViewById(R.id.button2);
        three = view.findViewById(R.id.button3);
         four= view.findViewById(R.id.button4);
        five= view.findViewById(R.id.button5);
        six = view.findViewById(R.id.button6);
        seven = view.findViewById(R.id.button7);
        eight = view.findViewById(R.id.button8);
         nine = view.findViewById(R.id.button9);
        ten = view.findViewById(R.id.button10);
        
        winAvlBlnc=view.findViewById(R.id.win_aval_blnc);
        winMainRechargeBtn=view.findViewById(R.id.win_main_recharge_btn);
        winRetry=view.findViewById(R.id.win_retry);
        winReadRules=view.findViewById(R.id.win_read_rules);



        gameLayout=view.findViewById(R.id.cl_one);
        gameResultRecycler = view.findViewById(R.id.game_result_recycler);
        recordRecycler=view.findViewById(R.id.record_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        winAvlBlnc.setText("Available Balance: ₹"+u_amount);
        winMainRechargeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(requireActivity(), Recharge.class);
                startActivity(intent);
            }
        });
        winRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        winReadRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(v.getTag().toString());
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(v.getTag().toString());
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(v.getTag().toString());
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(v.getTag().toString());
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(v.getTag().toString());
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(v.getTag().toString());
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(v.getTag().toString());
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(v.getTag().toString());
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(v.getTag().toString());
            }
        });
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(v.getTag().toString());
            }
        });
        join_pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomDialog("pink");
            }
        });
        join_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomDialog("red");
            }
        });
        join_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomDialog("green");
            }
        });


        gameResultRecycler.setLayoutManager(linearLayoutManager);

        recordRecycler.setLayoutManager(linearLayoutManager);

        recordAdapter=new RecordWinAdapter(new ArrayList<>(),getContext());
        recordRecycler.setAdapter(recordAdapter);

        adapter = new GameWinAdapter(getContext(), new ArrayList<>());

        gameResultRecycler.setHasFixedSize(true);

        // Initialize with an empty list
        gameResultRecycler.setAdapter(adapter);

            getResults();
            fetchTimeFromAPI();
        getRecord();
         gameRnext = view.findViewById(R.id.next_button);
         gameRprev=view.findViewById(R.id.previous_button);
        gameRnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        gameRprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
    // Define the click handler function
    private void handleButtonClick(String buttonTag) {
        switch (buttonTag) {
            case "1":
                openCustomDialog("one");
                // Handle Button One click
                break;
            case "2":
                openCustomDialog("two");
                // Handle Button Two click
                break;
            case "3":
                openCustomDialog("three");
                // Handle Button Three click
                break;
            case "4":
                openCustomDialog("four");
                // Handle Button One click
                break;
            case "5":
                openCustomDialog("five");
                // Handle Button Two click
                break;
            case "6":
                openCustomDialog("six");
                // Handle Button Three click
                break;
            case "7":
                openCustomDialog("seven");
                // Handle Button One click
                break;
            case "8":
                openCustomDialog("eight");
                // Handle Button Two click
                break;
            case "9":
                openCustomDialog("nine");
                // Handle Button Three click
                break;

            case "10":
                openCustomDialog("ten");
                // Handle Button One click
                break;
            // Add more cases for additional buttons
            default:
                // Handle the default case if needed
                break;
        }
    }

    private void startCountdownTimer(long timeInMillis) {
        // Cancel the existing countdown timer, if it exists
        countdownTimer = new CountDownTimer(timeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Calculate minutes and seconds from millisUntilFinished
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;

                // Format minutes and seconds as a string (e.g., "02:30")
                String time = String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds);

                if(minutes==0 && seconds<=30){
                    //make every btn disable
                    disableButtons();

                   if(seconds==0){
                       //get the winner color
                       getResults();
                       fetchTimeFromAPI();
                   }
                }else{
                    enableButtons();
                }
                // Update the TextView with the remaining time
                timer.setText(time);

            }

            @Override
            public void onFinish() {
                // When the timer finishes, fetch the time from the API again but this time after 50 second


            }
        };
     countdownTimer.start();
    }

    private void disableButtons() {
     join_green.setEnabled(false);
     join_pink.setEnabled(false);
     join_red.setEnabled(false);
     one.setEnabled(false);
        two.setEnabled(false);
        three.setEnabled(false);
        four.setEnabled(false);
        five.setEnabled(false);
        six.setEnabled(false);
        seven.setEnabled(false);
        eight.setEnabled(false);
        nine.setEnabled(false);
        ten.setEnabled(false);

    }
    private void enableButtons() {
        join_green.setEnabled(true);
        join_pink.setEnabled(true);
        join_red.setEnabled(true);
        one.setEnabled(true);
        two.setEnabled(true);
        three.setEnabled(true);
        four.setEnabled(true);
        five.setEnabled(true);
        six.setEnabled(true);
        seven.setEnabled(true);
        eight.setEnabled(true);
        nine.setEnabled(true);
        ten.setEnabled(true);
    }


    AppDataUtil appDataUtil;




    private void getResults() {
        Log.e("TAG", "getResults: " );
        if(isLoggedIn()){
            Log.e("TAG", "logged in: " );
            customProgressDialog.show();
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {

                    ApiService gameApiService = RetrofitClientWithToken.getApiService(u_decodedToken);

                    Call<GameResultResponseModel> call = gameApiService.getColorResult();

                    call.enqueue(new Callback<GameResultResponseModel>() {
                        @Override
                        public void onResponse(Call<GameResultResponseModel> call, Response<GameResultResponseModel> response) {
                            if (response.isSuccessful()) {
                                // Check if the response indicates success
                                GameResultResponseModel resultModels = response.body();
                                if (resultModels != null && resultModels.isSuccess()) {
                                    List<ResultModel> gameResult = resultModels.getData();
                                    if (gameResult != null && !gameResult.isEmpty()) {
                                        Collections.reverse(gameResult);
                                        Log.d("TAG", "onResponse: "+gameResult.get(0));
                                        adapter = new GameWinAdapter(getContext(), gameResult);
                                        gameResultRecycler.setAdapter(adapter);
                                        if( gameRprev.getVisibility()!=View.VISIBLE){
                                            gameRprev.setVisibility(View.VISIBLE);
                                            gameRprev.setVisibility(View.VISIBLE);
                                        }

                                        customProgressDialog.hide();

                                        boolean isPlayedNumber= appDataUtil.getBooleanData("PLAY_NUMBER");
                                        boolean isPlayedColor= appDataUtil.getBooleanData("PLAY_COLOR");
                                        int initial_amount=appDataUtil.getIntData("INIT_AMOUNT");
                                        int userWalet=appDataUtil.getIntData("user_amount");
                                        int expence=initial_amount-userWalet;

                                        Log.d("game result", "total bet= "+expence);
                                        //update the user according to the win factor
                                        if(isPlayedNumber||isPlayedColor){
                                            // Retrieve and update variables using SharedPreferences
                                            int oneV = appDataUtil.getIntData("one");
                                            int twoV = appDataUtil.getIntData("two");
                                            int threeV = appDataUtil.getIntData("three");
                                            int fourV = appDataUtil.getIntData("four");
                                            int fiveV = appDataUtil.getIntData("five");
                                            int sixV = appDataUtil.getIntData("six");
                                            int sevenV = appDataUtil.getIntData("seven");
                                            int eightV = appDataUtil.getIntData("eight");
                                            int nineV =appDataUtil.getIntData("nine");
                                            int tenV =appDataUtil.getIntData("ten");

                                            int redValue = appDataUtil.getIntData("red");
                                            int greenValue = appDataUtil.getIntData("green");
                                            int pinkValue = appDataUtil.getIntData("pink");

                                            Log.e("record add test", "onResponse: is played");
                                            ResultModel latestResult = gameResult.get(0);//last result(latest)
                                            List<String> latestWinColor = latestResult.getWinColor();
                                            String latestWinShowId = latestResult.getWinShowId();
                                            String latestWinPrice = latestResult.getWinPrice();
                                            String latestWinNumber = latestResult.getWinNumber();
                                            int latWN=Integer.parseInt(latestWinNumber);

                                            int numberWinAmount=0;
                                            if(isPlayedNumber){
                                                 Log.e("record add test", "onResponse: is played nunber");

                                                 if(latWN==1&& oneV>0){
                                                     numberWinAmount=oneV*9;
                                                     double deduction = 0.05 * numberWinAmount;
                                                     numberWinAmount -= deduction;
                                                 }else if(latWN==2&& twoV>0){
                                                     numberWinAmount=twoV*9;
                                                     double deduction = 0.05 * numberWinAmount;
                                                     numberWinAmount -= deduction;
                                                 }
                                                 else if(latWN==3&& threeV>0){
                                                     numberWinAmount=threeV*9;
                                                     double deduction = 0.05 * numberWinAmount;
                                                     numberWinAmount -= deduction;
                                                 }
                                                 else if(latWN==4&& fourV>0){
                                                     numberWinAmount=fourV*9;
                                                     double deduction = 0.05 * numberWinAmount;
                                                     numberWinAmount -= deduction;
                                                 }
                                                 else if(latWN==5&& fiveV>0){
                                                     numberWinAmount=fiveV*9;
                                                     double deduction = 0.05 * numberWinAmount;
                                                     numberWinAmount -= deduction;
                                                 }
                                                 else if(latWN==6&& sixV>0){
                                                     numberWinAmount=sixV*9;
                                                     double deduction = 0.05 * numberWinAmount;
                                                     numberWinAmount -= deduction;
                                                 }
                                                 else if(latWN==7&& sevenV>0){
                                                     numberWinAmount=sevenV*9;
                                                     double deduction = 0.05 * numberWinAmount;
                                                     numberWinAmount -= deduction;
                                                 }else if(latWN==8&& eightV>0){
                                                     numberWinAmount=eightV*9;
                                                     double deduction = 0.05 * numberWinAmount;
                                                     numberWinAmount -= deduction;
                                                 }else if(latWN==9&& nineV>0){
                                                     numberWinAmount=nineV*9;
                                                     double deduction = 0.05 * numberWinAmount;
                                                     numberWinAmount -= deduction;
                                                 }else if(latWN==10&& tenV>0){
                                                     numberWinAmount=tenV*9;
                                                     double deduction = 0.05 * numberWinAmount;
                                                     numberWinAmount -= deduction;
                                                 }

                                             }
                                            int winAmount=0;
                                                if(latestWinColor.size()==1){
                                                    Log.e("record add test", "sizw colr=1 and red val green val="+redValue+"grn="+greenValue);
                                                    String winColor=latestWinColor.get(0);
                                                    if(winColor.equals("red") && redValue>0){
                                                        Log.e("record add test", "reg val>0");
                                                        //update the user walet
                                                         winAmount=(redValue*2);
                                                        // Calculate 5% of winAmount
                                                        double deduction = 0.05 * winAmount;
                                                        winAmount -= deduction;

                                                    }
                                                    if (winColor.equals("green") && greenValue>0){
                                                        Log.e("record add test", "green val>0");
                                                        //update the user walet
                                                         winAmount=(greenValue*2);
                                                        // Calculate 5% of winAmount
                                                        double deduction = 0.05 * winAmount;
                                                        winAmount -= deduction;

                                                    }

                                                }
                                                else if(latestWinColor.size()==2){
                                                    Log.e("record add test", "color size=2");
                                                    String winColor=latestWinColor.get(0);
                                                    if(pinkValue>0){
                                                        Log.e("record add test", "pink val>0");
                                                        //5x
                                                        winAmount=winAmount+pinkValue*5;
                                                        double deduction = 0.05 * winAmount;
                                                        winAmount-=deduction;
                                                        if (winColor.equals("red") && redValue > 0) {
                                                            //update the user walet
                                                            double fortyFivePercent = 0.45 * redValue;
                                                            winAmount+=fortyFivePercent;
                                                            double ded = 0.05 * winAmount;
                                                            winAmount-=ded;
                                                        }
                                                        if (winColor.equals("green") && greenValue > 0) {
                                                            double fortyFivePercent = 0.45 * greenValue;
                                                            winAmount+=fortyFivePercent;
                                                            double ded = 0.05 * winAmount;
                                                            winAmount-=ded;
                                                        }
                                                    }else {
                                                        //45%
                                                        if (winColor.equals("red") && redValue > 0) {
                                                            //update the user walet
                                                            double fortyFivePercent = 0.45 * redValue;
                                                            winAmount+=fortyFivePercent;
                                                            double deduction = 0.05 * winAmount;
                                                            winAmount-=deduction;
                                                        }
                                                        if (winColor.equals("green") && greenValue > 0) {
                                                            double fortyFivePercent = 0.45 * greenValue;
                                                            winAmount+=fortyFivePercent;
                                                            double deduction = 0.05 * winAmount;
                                                            winAmount-=deduction;
                                                        }
                                                    }
                                                }

                                                int totalWon=winAmount+numberWinAmount;

                                                if(totalWon>expence){
                                                    runAddRecord(totalWon,latestWinShowId, true,latestWinNumber,latestWinColor,String.valueOf(expence),u_id);
                                                }else{
                                                    if(numberWinAmount>0 && winAmount<=0){
                                                        runAddRecord(numberWinAmount,latestWinShowId, true,latestWinNumber,latestWinColor,String.valueOf(expence),u_id);
                                                    }else if(numberWinAmount<=0 && winAmount>0){
                                                        runAddRecord(winAmount,latestWinShowId, true,latestWinNumber,latestWinColor,String.valueOf(expence),u_id);
                                                    }else{
                                                        runAddRecord(expence,latestWinShowId, false,latestWinNumber,latestWinColor,String.valueOf(expence),u_id);
                                                    }

                                                }

                                                //here deceide win or loss
                                            Log.e("record", "end of is played now reset value");
//                                            AddToUserWalet(winAmount+numberWinAmount,latestWinShowId,latestWinNumber,latestWinColor,String.valueOf(totalBet));

                                          }//user played number or colour
                                    }
                                } else {
                                    // Handle API response indicating failure
                                    Log.d("TAG", "API response indicates failure.");
                                    if (changeMainViewListener!=null){
                                        changeMainViewListener.gotoLoginWin();
                                    }
                                }

                            } else {
                                customProgressDialog.hide();
                                if (changeMainViewListener!=null){
                                    changeMainViewListener.gotoLoginWin();
                                }
                            }
                        }



                        @Override
                        public void onFailure(Call<GameResultResponseModel> call, Throwable t) {
                            // Handle failure (e.g., network issues)

                            customProgressDialog.hide();
                            if (changeMainViewListener!=null){
                                changeMainViewListener.gotoLoginWin();
                                return;
                            }
                        }
                    });

                }
            });
        }else{
            if(changeMainViewListener!=null){
                changeMainViewListener.gotoLoginWin();
            }
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        if(isLoggedIn()){
            AppDataUtil appDataUtil=AppDataUtil.getInstance(requireActivity().getApplicationContext());
            int updated_amount=  appDataUtil.getIntData("user_amount");
            winAvlBlnc.setText("Available balance: ₹"+updated_amount);
        }
    }


    private void resetGlobalValues() {
        appDataUtil.setIntData(0, "INIT_AMOUNT");
        appDataUtil.setIntData(0, "one");
        appDataUtil.setIntData(0, "two");
        appDataUtil.setIntData(0, "three");
        appDataUtil.setIntData(0, "four");
        appDataUtil.setIntData(0, "five");
        appDataUtil.setIntData(0, "six");
        appDataUtil.setIntData(0, "seven");
        appDataUtil.setIntData(0, "eight");
        appDataUtil.setIntData(0, "nine");
        appDataUtil.setIntData(0, "ten");
        appDataUtil.setIntData(0, "red");
        appDataUtil.setIntData(0, "green");
        appDataUtil.setIntData(0, "pink");
        appDataUtil.setBooleanData(false, "PLAY_COLOR");
        appDataUtil.setBooleanData(false, "PLAY_NUMBER");
        Log.d("last ", "run:  value reset");


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countdownTimer != null) {
            countdownTimer.cancel();
        }
    }
    private void  fetchTimeFromAPI() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {

                ApiService apiService = RetrofitClient.getApiService();
                Call<CommonResponseModel> call = apiService.getSecureKey();
                call.enqueue(new Callback<CommonResponseModel>() {
                    @Override
                    public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                        Log.e("TAG", "onResponse: "+response.toString() );
                        if (response.isSuccessful()) {
                            CommonResponseModel securityResponse = response.body();
                            if (securityResponse != null) {
                                String apiTimeAndKey = securityResponse.getMessage();

                                // Split the string using the underscore "_" as the separator
                                String[] parts = apiTimeAndKey.split("_");

                                if (parts.length == 2) {
                                    String apiTime = parts[0];
                                    String gamePlayKey = parts[1];

                                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy, hh:mm:ss a", Locale.ENGLISH);
                                    dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata")); // Set to Indian Standard Time (IST)
                                    Log.e("TAG", "fetchTimeFromAPI: time key========"+gamePlayKey);

                                        current_game_id.setText(gamePlayKey);

                                    try {
                                        Date apiDate = dateFormat.parse(apiTime);

                                        long currentTimeMillis = System.currentTimeMillis();
                                        long apiTimeMillis = apiDate.getTime();
                                        long timeDifference = Math.abs(apiTimeMillis - currentTimeMillis);

                                        // Start the countdown timer with the calculated time difference
                                        startCountdownTimer(timeDifference);

                                        // Now you have both apiTime and gamePlayKey for further use
                                        Log.d("TAG", "apiTime: " + apiTime);
                                        Log.d("TAG", "gamePlayKey: " + gamePlayKey);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    // Handle the case where the string does not contain both parts as expected
                                    Log.d("TAG", "Invalid format: " + apiTimeAndKey);
                                }
                            }

                        } else {
                            // Handle API call failure
                            Log.d("TAG", "error: " + response);
                        }


                        ;
                    }
                    @Override
                    public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                        Log.d("TAG", "onFailure: "+t.getMessage());
                    }
                });
            }
        });


    }

    public boolean isLoggedIn() {
         appDataUtil= AppDataUtil.getInstance(requireActivity().getApplicationContext());
        return appDataUtil.isLoggedIn();
    }

    public void updateColorValue(String colorName, int colorValue) {
            if(isLoggedIn()){
                customProgressDialog.show();
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {

                        ColorUpdateRequest updateRequest = new ColorUpdateRequest(colorName, colorValue);
                        ApiService apiService = RetrofitClientWithToken.getApiService(u_decodedToken);

                        Call<CommonResponseModel> call = apiService.updateColor("Bearer " + u_decodedToken, u_id, updateRequest);
                        call.enqueue(new Callback<CommonResponseModel>() {
                            @Override
                            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {

                                if (response.isSuccessful()) {
                                    customProgressDialog.hide();
                                    CommonResponseModel apiResponse = response.body();
                                    if (apiResponse != null && apiResponse.isSuccess()) {
                                        String[] parts = apiResponse.getMessage().split("_");
                                        int initial_amount=Integer.parseInt(parts[0]);
                                        int walet_amount=Integer.parseInt(parts[1]);
                                        int amount = initial_amount-walet_amount;
                                        if(appDataUtil.getIntData("INIT_AMOUNT")==0){
                                            appDataUtil.setIntData(initial_amount,"INIT_AMOUNT");
                                        }
//                                        appDataUtil.setIntData(appDataUtil.getIntData("ROUND_TOTAL")+amount,"ROUND_TOTAL");
                                        appDataUtil.setIntData(walet_amount,"user_amount");
                                        winAvlBlnc.setText("Available Balance: ₹"+walet_amount);
                                        Toast.makeText(getContext(), "Amount Added="+amount, Toast.LENGTH_LONG).show();
                                        updateGlobalValues(colorName,colorValue);
                                        // Handle success message
                                    }
                                }

                            }


                            @Override
                            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                                customProgressDialog.hide();
                                if(changeMainViewListener!=null){
                                    changeMainViewListener.gotoLoginWin();
                                }
                         }
                        });

                    }
                });
            }else{
                if(changeMainViewListener!=null){
                    changeMainViewListener.gotoLoginWin();
                }
            }


    }

    private void updateGlobalValues(String colorName, int colorValue) {

        switch (colorName) {
            case "red", "green", "pink" -> {
                updateColorValue2(colorName, colorValue);
            }
            case "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten" -> {
                updateNumberValue(colorName, colorValue);
            }
            default -> {
                // Handle default case if needed
            }
        }

        // Perform additional actions based on isPlayedColor or isPlayedNumber if needed
    }

    private void updateColorValue2(String colorName, int colorValue) {
        int currentValue = appDataUtil.getIntData(colorName);
        appDataUtil.setBooleanData(true, "PLAY_COLOR");
        appDataUtil.setIntData(currentValue + colorValue, colorName);
    }

    private void updateNumberValue(String numberName, int colorValue) {
        int currentValue = appDataUtil.getIntData(numberName);
        appDataUtil.setBooleanData(true, "PLAY_NUMBER");
        appDataUtil.setIntData(currentValue + colorValue, numberName);
    }


    //    private void updateGlobalValues(String colorName, int colorValue) {
//        switch (colorName) {
//            case "red" -> {
//                appDataUtil.setBooleanData(true,"PLAY_COLOR");
//                int value= Integer.parseInt(appDataUtil.getStringData("red"));
//                appDataUtil.setStringData(String.valueOf(value+colorValue),"red");
////                isPlayedColor = true;
////                redValue = redValue + colorValue;
//            }
//            case "green" -> {
//                isPlayedColor = true;
//                greenValue = greenValue + colorValue;
//            }
//            case "pink" -> {
//                isPlayedColor = true;
//                pinkValue = pinkValue + colorValue;
//            }
//            case "one" -> {
//                isPlayedNumber = true;
//                oneV += colorValue;
//            }
//            case "two" -> {
//                isPlayedNumber = true;
//                twoV += colorValue;
//            }
//            case "three" -> {
//                isPlayedNumber = true;
//                threeV += colorValue;
//            }
//            case "four" -> {
//                isPlayedNumber = true;
//                fourV += colorValue;
//            }
//            case "five" -> {
//                isPlayedNumber = true;
//                fiveV += colorValue;
//            }
//            case "six" -> {
//                isPlayedNumber = true;
//                sixV += colorValue;
//            }
//            case "seven" -> {
//                isPlayedNumber = true;
//                sevenV += colorValue;
//            }
//            case "eight" -> {
//                isPlayedNumber = true;
//                eightV += colorValue;
//            }
//            case "nine" -> {
//                isPlayedNumber = true;
//                nineV += colorValue;
//            }
//            case "ten" -> {
//                isPlayedNumber = true;
//                tenV += colorValue;
//            }
//            default -> {
//            }
//        }
//    }
    private void  getRecord(){
        ApiService apiService = RetrofitClientWithToken.getApiService(u_decodedToken);
         Call<UserRecordResponseModel> modelCall=apiService.getRecordsByUser(u_decodedToken,u_id);
         modelCall.enqueue(new Callback<UserRecordResponseModel>() {
             @Override
             public void onResponse(Call<UserRecordResponseModel> call, Response<UserRecordResponseModel> response) {
                 if (response.isSuccessful()&&response.body()!=null){
                     List<UserRecordModel> model = response.body().getData();
                     recordAdapter=new RecordWinAdapter(model,getContext());
                     recordRecycler.setAdapter(recordAdapter);
                 }
                 Log.e("TAG", "get result=: "+response );
             }

             @Override
             public void onFailure(Call<UserRecordResponseModel> call, Throwable t) {
                 Log.e("TAG", "get result=: "+t.getMessage() );
             }
         });


    }

    private void runAddRecord(int amount,String gameId,boolean winOrLoos,String winNumber,
                              List<String > winColors,String totalAmount,String userId) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.e("record", "runAddRecord: called" );
                AddRecordRequestModel model=new AddRecordRequestModel(amount,gameId,winOrLoos,winNumber,winColors,totalAmount,userId);
                ApiService apiService = RetrofitClientWithToken.getApiService(u_decodedToken);

                Call<UserRecordResponseModel> call=apiService.addRecord("Bearer " + u_decodedToken,model);
                call.enqueue(new Callback<UserRecordResponseModel>() {
                    @Override
                    public void onResponse(Call<UserRecordResponseModel> call, Response<UserRecordResponseModel> response) {
                        if(response.isSuccessful()&& response.body()!=null){
                            List<UserRecordModel> userRecordModels=response.body().getData();
                            recordAdapter=new RecordWinAdapter(userRecordModels,getContext());
                            recordRecycler.setAdapter(recordAdapter);

                            appDataUtil.setIntData(Integer.parseInt(response.body().getMessage()),"user_amount");
                            winAvlBlnc.setText("Available Balance: ₹"+response.body().getMessage());
                            resetGlobalValues();
                            Toast.makeText(getContext(), "Game Result Updated", Toast.LENGTH_LONG).show();
                        }
                        Log.e("record", "onResponse: "+response );
                    }

                    @Override
                    public void onFailure(Call<UserRecordResponseModel> call, Throwable t) {
                     resetGlobalValues();
                        Log.e("record", "onResponse: "+t.getMessage() );
                    }
                });

            }
        });

//        Call<GetRecordResponseModel> call = apiService.addRecord("Bearer " + u_decodedToken,model);

    }

    private void UpdateRecordRecycler() {
    }

    private void openCustomDialog(String color) {
        CustomDialog customDialog = new CustomDialog(getActivity(), color,this); // 'this' refers to the current activity or context
        customDialog.show();
    }
    private ChangeMainViewListener changeMainViewListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Win.ChangeMainViewListener){
            changeMainViewListener=(Win.ChangeMainViewListener) context;

        }else{
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        changeMainViewListener=null;

    }

    public interface ChangeMainViewListener {
        void gotoLoginWin();
    }
}


























