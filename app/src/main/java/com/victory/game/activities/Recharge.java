package com.victory.game.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.victory.game.R;
import com.victory.game.RetrofitClientWithToken;
import com.victory.game.interfaces.ApiService;
import com.victory.game.models.AddPayRequestModel;
import com.victory.game.models.ColorUpdateRequest;
import com.victory.game.models.CommonResponseModel;
import com.victory.game.models.PUpdateRequestModel;
import com.victory.game.utils.AppDataUtil;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Recharge extends AppCompatActivity implements PaymentResultListener {

    private Button addCashButton;
    TextView a100,a300,a500,a1000,a2000,a5000,a10000,a20000,a50000;
    TextView amountShow;
    TextView amountPayTv;
    LinearLayout gPayL,phonePeL,paytmL,upL;
    RadioButton s1,s2;
    ConstraintLayout upi_layout, add_cash_layout, razorpay_layout;
    int cash=100;
    int chosen_server=1;//1 for upi 2 for pay

    private  ActivityResultLauncher<Intent> gpayLauncher;
    private  ActivityResultLauncher<Intent> phonePaLauncher;

    private  ActivityResultLauncher<Intent> payTmLauncher;

    private  ActivityResultLauncher<Intent> upiLauncher;

    String pa="";
    String tn="";
    String am= "";
    String cu="";
    String pn="";
    String tr="";
    String p_name="name";
    String tid="";
    private boolean prePayDataUpdated=false;
    private String decodedToken="";
    private String uid="";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        if(!isLoggedIn()){
            Toast.makeText(this, "Login Expired", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        appDataUtil = AppDataUtil.getInstance(getApplicationContext());

        String token = appDataUtil.getStringData("token").trim();
        uid = appDataUtil.getStringData("user_uid").trim();
        decodedToken = appDataUtil.decodeString(token);

        tid=String.valueOf(System.currentTimeMillis());
        runBeforePayment();
        addCashButton =findViewById(R.id.add_cash_btn);
        a100=findViewById(R.id.a100);
        a300=findViewById(R.id.a300);
        a500=findViewById(R.id.a500);
        a1000=findViewById(R.id.a1000);
        a2000=findViewById(R.id.a2000);
        a5000=findViewById(R.id.a5000);
        a10000=findViewById(R.id.a10000);
        a20000=findViewById(R.id.a20000);
        a50000=findViewById(R.id.a50000);
        a100.setOnClickListener(view -> onButtonClick(100));
        a300.setOnClickListener(view -> onButtonClick(300));
        a500.setOnClickListener(view -> onButtonClick(500));
        a1000.setOnClickListener(view -> onButtonClick(1000));
        a2000.setOnClickListener(view -> onButtonClick(2000));
        a5000.setOnClickListener(view -> onButtonClick(5000));
        a10000.setOnClickListener(view -> onButtonClick(10000));
        a20000.setOnClickListener(view -> onButtonClick(20000));
        a50000.setOnClickListener(view -> onButtonClick(50000));

        s1=findViewById(R.id.radioBtnS1);
        s2=findViewById(R.id.radioBtnS2);
        RadioGroup radioGroup=findViewById(R.id.radioGroup);


        // Set up an OnCheckedChangeListener for the radio group
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioBtnS1) {
                    chosen_server = 1;
                    // Option 1 is checked, update the global integer to 1
                } else if (checkedId == R.id.radioBtnS2) {
                    chosen_server = 2;
                    // Option 2 is checked, update the global integer to 2
                }
            }
        });

        add_cash_layout=findViewById(R.id.add_cash_layout);
        upi_layout=findViewById(R.id.upi_server_layout);
        razorpay_layout=findViewById(R.id.razorpay_server_layout);
        amountShow=findViewById(R.id.recharge_amount_show);
        amountPayTv=findViewById(R.id.amount_payable_tv);

        gPayL=findViewById(R.id.gpayLbtn);
        phonePeL=findViewById(R.id.phonePeLbtn);
        paytmL=findViewById(R.id.paytmLbtn);
        upL=findViewById(R.id.upiLbtn);

        gPayL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prePayDataUpdated){
                    openGpay();
                }
                else {
                    Toast.makeText(Recharge.this, "Try Again After Sometime", Toast.LENGTH_SHORT).show();
                    runBeforePayment();
                }

            }
        });
        phonePeL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prePayDataUpdated){
                    openPhonePay();
                }else {
                    Toast.makeText(Recharge.this, "Try Again After Sometime", Toast.LENGTH_SHORT).show();
                    runBeforePayment();
                }

            }
        });
        upL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prePayDataUpdated){
                    openUPI();
                }else {
                    Toast.makeText(Recharge.this, "Try Again After Sometime", Toast.LENGTH_SHORT).show();
                    runBeforePayment();
                }

            }
        });
        paytmL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prePayDataUpdated){
                    openpaytm();
                }else {
                    Toast.makeText(Recharge.this, "Try Again After Sometime", Toast.LENGTH_SHORT).show();
                    runBeforePayment();
                }
            }
        });

        addCashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (chosen_server==1){
                   run1();
               }else {
                   if(prePayDataUpdated){
                       run2();
                       openRazorpay();
                   }else {
                       Toast.makeText(Recharge.this, "Try Again After Sometime", Toast.LENGTH_SHORT).show();
                       runBeforePayment();
                   }


               }
            }
        });

        // Declare the ActivityResultLauncher
        gpayLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.d("TAG", "onActivityResult: "+result.toString());
                        if (result.getResultCode() == RESULT_OK) {
                            // Handle success case
                            // The intent was successful\
//                            result.getData().toString()
                            runAddPayment(am,p_name,"ADD","GPay","success");
                            Log.e("payment", "onActivityResult: success" );
                        } else {
                            // Handle failure case
                            // The intent was not successful
                            Log.e("payment", "onActivityResult: fail" );

                        }
                    }
                }
        );
        phonePaLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.d("TAG", "onActivityResult: "+result.toString());
                        if (result.getResultCode() == RESULT_OK) {
                            // Handle success case
                            // The intent was successful
                            runAddPayment(am,p_name,"ADD","PhonePe","success");
                            Log.e("payment", "onActivityResult: success" );
                        } else {
                            // Handle failure case
                            // The intent was not successful
                            Log.e("payment", "onActivityResult: fail" );

                        }
                    }
                }
        );
        payTmLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.d("TAG", "onActivityResult: "+result.toString());
                        if (result.getResultCode() == RESULT_OK) {
                            // Handle success case
                            // The intent was successful
                            runAddPayment(am,p_name,"ADD","Paytm","success");
                            Log.e("payment", "onActivityResult: success" );
                        } else {
                            // Handle failure case
                            // The intent was not successful
                            Log.e("payment", "onActivityResult: fail" );

                        }
                    }
                }
        );
        upiLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            // Handle success case
                            // The intent was successful
                            runAddPayment(am,p_name,"ADD","upi","success");
                            Log.e("payment", "onActivityResult: success" );
                        } else {
                            // Handle failure case
                            // The intent was not successful
                            Log.e("payment", "onActivityResult: fail" );

                        }
                    }
                }
        );


    }
    AppDataUtil appDataUtil;
    private void runBeforePayment(){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {


                PUpdateRequestModel pUpdateRequestModel=new PUpdateRequestModel(tid);
                ApiService apiService=RetrofitClientWithToken.getApiService(decodedToken);
                Call<CommonResponseModel> call= apiService.updatePUser(decodedToken,uid,pUpdateRequestModel);
                call.enqueue(new Callback<CommonResponseModel>() {
                    @Override
                    public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                        prePayDataUpdated=true;
                    }

                    @Override
                    public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                        prePayDataUpdated=false;
                    }
                });
            }
        });
    }
    private void runAddPayment(String amount, String name,String type,String method,String status){

            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    AppDataUtil appDataUtil =AppDataUtil.getInstance(getApplicationContext());

                    String token = appDataUtil.getStringData("token").trim();
                    String uid = appDataUtil.getStringData("user_uid").trim();
                    String decodedToken = appDataUtil.decodeString(token);

                    AddPayRequestModel model=new AddPayRequestModel(uid,name,type,amount,method,tid,status);

                    ApiService apiService = RetrofitClientWithToken.getApiService(decodedToken);

                    Call<CommonResponseModel> call2 = apiService.createPayment("Bearer " + decodedToken, model);
                    call2.enqueue(new Callback<CommonResponseModel>() {
                        @Override
                        public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                            if(response.isSuccessful() && response.body()!=null){
                                int amount=Integer.parseInt(response.body().getMessage());
                                appDataUtil.setIntData(amount ,"user_amount");

                                Toast.makeText(Recharge.this, "Payment Add Success", Toast.LENGTH_SHORT).show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                },300);

                            }else{
                                Toast.makeText(Recharge.this, "Failed to add payment contact support", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                            Toast.makeText(Recharge.this, "Failed to add contact support", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

    }


    public boolean isLoggedIn() {
        AppDataUtil appDataUtil=AppDataUtil.getInstance(getApplicationContext());
        return appDataUtil.isLoggedIn();
    }


    private void run2() {
        add_cash_layout.setVisibility(View.GONE);
        upi_layout.setVisibility(View.GONE);
        razorpay_layout.setVisibility(View.VISIBLE);
    }

    private void run1() {
        add_cash_layout.setVisibility(View.GONE);
        upi_layout.setVisibility(View.VISIBLE);
        razorpay_layout.setVisibility(View.GONE);
    }
    private void runDefault(){
        add_cash_layout.setVisibility(View.VISIBLE);
        upi_layout.setVisibility(View.GONE);
        razorpay_layout.setVisibility(View.GONE);
    }

    private void openUPI() {
    }

    private void onButtonClick(int i) {
        cash=i;
        amountShow.setText("Amount: "+ i);
        amountPayTv.setText("₹"+i);
    }

    private void openPhonePay(){
        pa = "7654702363@okbizaxis";
        tn = tid;
        am = cash + ".00";
        cu = "INR";
        pn = "PG";
        try {
            // Specify the package name for Google Pay
            String phonePePackageName="com.phonepe.app";
//            intent.setPackage(phonePePackageName);

            // Create an Intent with the action ACTION_VIEW and the specified URI
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("phonepe://pay?pa="+pa+"&tn="+tn+"&am="+am+"&cu="+cu+"&pn="+pn));

            // Set the package name for Google Pay
            intent.setPackage(phonePePackageName);

            // Check if Google Pay is installed and can handle this intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                // Start the activity with the intent
                phonePaLauncher.launch(intent);
            } else {
                // Handle the case where Google Pay is not installed or cannot handle the intent
                Toast.makeText(this, "Google Pay is not installed or cannot handle this intent.", Toast.LENGTH_SHORT).show();
            }
        } catch (ActivityNotFoundException e) {
            // Handle the case where the activity is not found
            Toast.makeText(this, "Activity not found.", Toast.LENGTH_SHORT).show();
        }

    }

    private void openGpay() {
        runBeforePayment();
        try {
            // Specify the package name for Google Pay
            String gpayPackageName = "com.google.android.apps.nbu.paisa.user";

            pa = "7654702363@okbizaxis";
            tn = tid;
            am = cash + ".00";
            cu = "INR";
            pn = "PG";

            // Create an Intent with the action ACTION_VIEW and the specified URI
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("gpay://upi/pay?pa=im.257202869878@indus&tn=9jl&am=100.00&cu=INR&pn=PG"));
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("gpay://upi/pay?pa=" + pa + "&tn=" + tn + "&am=" + am + "&cu=" + cu + "&pn=" + pn));

            // Set the package name for Google Pay
            intent.setPackage(gpayPackageName);

            // Check if Google Pay is installed and can handle this intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                // Start the activity with the intent
//                startActivity(intent);
                gpayLauncher.launch(intent);
            } else {
                // Handle the case where Google Pay is not installed or cannot handle the intent
                Toast.makeText(this, "Google Pay is not installed or cannot handle this intent.", Toast.LENGTH_SHORT).show();
            }
        } catch (ActivityNotFoundException e) {
            // Handle the case where the activity is not found
            Toast.makeText(this, "Activity not found.", Toast.LENGTH_SHORT).show();
        }
    }
    private void openpaytm() {
        pa="7654702363@okbizaxis";
        tn=tid;
        am= cash +".00";
        cu="INR";
        pn="pay";
        tr="r4emqr";
        try {
            // Create an Intent with the action ACTION_VIEW and the URI provided
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("paytmmp://pay?pa="+pa+"&pn="+pn+"&am="+am+"&tn="+tn+"&tr="+tr+"&cu="+cu));

//            String ptmPackageName="";
//            intent.setPackage(ptmPackageName);

            // Check if there's an app that can handle this intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                // Start the activity with the intent
                payTmLauncher.launch(intent);
            } else {
                // Handle the case where no app can handle the intent
                Toast.makeText(this, "No app can handle this intent.", Toast.LENGTH_SHORT).show();
            }
        } catch (ActivityNotFoundException e) {
            // Handle the case where the activity is not found
            Toast.makeText(this, "Activity not found.", Toast.LENGTH_SHORT).show();
        }

    }

    private void openRazorpay(){
        // amount that is entered by user.
        am = String.valueOf(cash);

        String samount = am;
        cu="INR";

        // rounding off the amount.
        int amount = Math.round(Float.parseFloat(samount) * 100);

        // initialize Razorpay account.
        Checkout checkout = new Checkout();

        String keyId="rzp_test_ykuMmaqza69m13";
        // set your id as below
        checkout.setKeyID(keyId);

        // set image
        checkout.setImage(R.drawable.baseline_games_24);

        // initialize json object
        JSONObject object = new JSONObject();
        try {
            // to put name
            object.put("name", "Bishal Maji");

            // put description
            object.put("description", "test pay");

            // to set theme color
//            object.put("theme.color", "");

            // put the currency
            object.put("currency", cu);

            // put amount
            object.put("amount", amount);

            // put mobile number
            object.put("prefill.contact", "9734606970");

            // put email
            object.put("prefill.email", "bishalmaji.in@gmail.com");

            // open razorpay to checkout activity
            checkout.open(Recharge.this, object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
//        finish();
        runAddPayment(am,p_name,"ADD","GPay","success");

        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        runDefault();
        Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
    }
}


