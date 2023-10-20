package com.victory.game.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.victory.game.R;
import com.victory.game.RetrofitClient;
import com.victory.game.interfaces.ApiService;
import com.victory.game.models.LoginRequestModel;
import com.victory.game.utils.AppDataUtil;
import com.victory.game.utils.CurrentUserFetchWorker;
import com.victory.game.utils.CustomProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CustomProgressDialog progressDialog;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
    Button goToRegister, goToForget,login;
    ImageButton back;
    EditText phone_no, password_login;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Create an instance of CustomProgressDialog
        progressDialog = new CustomProgressDialog(getContext());


        back=view.findViewById(R.id.back_login);
        goToRegister=view.findViewById(R.id.goto_register);
        goToForget=view.findViewById(R.id.goto_forget);
        login=view.findViewById(R.id.login_btn);
        phone_no=view.findViewById(R.id.login_phone);
        password_login=view.findViewById(R.id.login_password);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                login.setEnabled(false);
                String validationMessage = validateLoginFields();
                if (validationMessage.equals("success")) {
                    progressDialog.show();
                    // All fields are valid, proceed with the login
                    String phone = phone_no.getText().toString().trim();
                    String password = password_login.getText().toString().trim();
                    login(phone, password);
                } else {

                    // Display the validation error message to the user
                    Toast.makeText(getContext(), validationMessage, Toast.LENGTH_SHORT).show();
                }


            }
        });

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController= Navigation.findNavController(requireActivity(),R.id.nav_container);
                navController.navigate(R.id.register);
            }
        });
        goToForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController= Navigation.findNavController(requireActivity(),R.id.nav_container);
                navController.navigate(R.id.resetPassword);
            }
        });

    }
    public String validateLoginFields() {
        // Check if Phone Number is empty
        String phoneText = phone_no.getText().toString().trim();
        if (phoneText.isEmpty()) {
            return "Phone number is required";
        } else if (phoneText.length() != 10) {
            return "Phone number must be 10 digits";
        }

        // Check if Password is empty
        String passwordText = password_login.getText().toString().trim();
        if (passwordText.isEmpty()) {
            return "Password is required";
        }

        // All conditions are met, return success message
        return "success";
    }
    private void postLogin(boolean b) {
        Log.e("TAG", "postLogin: came int opost login login result="+b );
        //hide the menu
        if (b){
        if (menuHiddenListener!=null){
                menuHiddenListener.loginSuccess();

        }
        }
        else{
            if (menuHiddenListener!=null){
                menuHiddenListener.loginFail();

            }
        }
        //post login method
    }

    private Login.OnMenuHiddenListener menuHiddenListener;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Login.OnMenuHiddenListener){
            menuHiddenListener=(Login.OnMenuHiddenListener) context;

        }else{
            throw new ClassCastException(context.toString());
        }
    }

    private void login(String phone, String password) {
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            LoginRequestModel loginRequestModel = new LoginRequestModel(phone, password);

            ApiService apiService = RetrofitClient.getApiService();

            Call<ResponseBody> call = apiService.login(loginRequestModel);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        progressDialog.hide();
                        // Retrieve the "Authorization" header from the response
                        String authorizationHeader = response.headers().get("Authorization");

                        if (authorizationHeader != null) {
                            // Split the header to extract the token
                            String[] parts = authorizationHeader.split(" ");

                            if (parts.length == 2) {
                                String token = parts[1].trim();
                                Log.d("TAG", "token= "+token);
                                AppDataUtil appDataUtil = AppDataUtil.getInstance(requireActivity().getApplicationContext());
                                String encodedToken=  appDataUtil.encodeString(token);
                                appDataUtil.setStringData(encodedToken, "token");
//                                long expTime = getExpiryTimeFromToken(token);
                                schedulePeriodicWork();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        postLogin(true);
                                    }
                                },2000);

                            }
                            Log.d("TAG", "onResponse: Auth head"+authorizationHeader);
                        } else {

                            postLogin(false);
                            // Handle the case where the "Authorization" header is missing
                            Log.d("TAG", "Authorization header missing in response");
                        }
                    } else {
                        progressDialog.hide();
                        Toast.makeText(getContext(), "Server Error Try Again", Toast.LENGTH_LONG).show();
                        postLogin(false);
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDialog.hide();
                    Toast.makeText(getContext(), "Login Failed"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    postLogin(false);
                }
            });
        });
    }

    private void schedulePeriodicWork() {
        long repeatInterval = 17;
        // Schedule a periodic work
        PeriodicWorkRequest periodicWork = new PeriodicWorkRequest.Builder(
                CurrentUserFetchWorker.class, repeatInterval, TimeUnit.MINUTES)
                .setInitialDelay(0, TimeUnit.MINUTES)  // Start immediately
                .build();

        WorkManager.getInstance(getContext())
                .enqueueUniquePeriodicWork(
                        "fetchCurrentUser",
                        ExistingPeriodicWorkPolicy.REPLACE,  // Replace existing work if any
                        periodicWork);

    }


    @Override
    public void onDetach() {
        super.onDetach();
        menuHiddenListener=null;
    }
    public interface OnMenuHiddenListener {
        void loginSuccess();

        void loginFail();
    }
}


//login data agter encode token=
//if tokenflow
//        if () {
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MINUTE, 20); // Add 3 minutes to the current time
//        Date expiryTime = calendar.getTime();
//
//        // Format the date as a string (you can use any format you prefer)
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String expiryTimeString = sdf.format(expiryTime);
//
//        if(appDataUtil.putStringData(expiryTimeString,"loginExpireTime")){
//        postLogin();
//        }
//        }

