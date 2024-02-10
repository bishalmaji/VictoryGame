package com.victory.game.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.victory.game.R;
import com.victory.game.RetrofitClient;
import com.victory.game.interfaces.ApiService;
import com.victory.game.models.OtpMailRequestModel;
import com.victory.game.models.OtpRequestModel;
import com.victory.game.models.CommonResponseModel;
import com.victory.game.models.RegisterMailRequestModel;
import com.victory.game.models.RegisterRequestModel;
import com.victory.game.utils.AppDataUtil;
import com.victory.game.utils.CustomProgressDialog;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Register#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Register extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String otp_str = "";
    private AppDataUtil appDataUtil;

    public Register() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Register.
     */
    // TODO: Rename and change types and number of parameters
    public static Register newInstance(String param1, String param2) {
        Register fragment = new Register();
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    Button register, otp_btn;
    EditText otp_code, refral, password, email, mobile;
    private CustomProgressDialog customProgressDialog;
    private Switch aSwitch;
    private boolean isRegisterMobile = false;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customProgressDialog = new CustomProgressDialog(getContext());
        ImageButton back;
        register = view.findViewById(R.id.register_btn);
        otp_btn = view.findViewById(R.id.otp_btn_register);
        back = view.findViewById(R.id.back_register);
        otp_code = view.findViewById(R.id.register_otp);
        refral = view.findViewById(R.id.register_refral);
        password = view.findViewById(R.id.register_password);
        email = view.findViewById(R.id.register_email);
        mobile = view.findViewById(R.id.register_phone);
        aSwitch = view.findViewById(R.id.registerSwitch);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mobile.setVisibility(View.VISIBLE);
                    email.setVisibility(View.GONE);
                    isRegisterMobile = true;
                    aSwitch.setText("Mobile");
                } else {
                    email.setVisibility(View.VISIBLE);
                    mobile.setVisibility(View.GONE);
                    isRegisterMobile = false;
                    aSwitch.setText("Email");
                }
            }

        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register.setEnabled(false);
                String validationMessage = validateTextField();

                String referralId="";
                int refId=0;
                referralId=refral.getText().toString().trim();
                if(!referralId.equals("")){
                    refId=Integer.parseInt(referralId);
                }

                if (isRegisterMobile) {

                    if (validationMessage.equals("success")) {
                        // All fields are valid, proceed with your logic

                        register(mobile.getText().toString().trim(), password.getText().toString().trim()
                                , otp_str, refId);
                    } else {
                        register.setEnabled(true);
                        // Display the validation error message to the user
                        Toast.makeText(getContext(), validationMessage, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (validationMessage.equals("success")) {
                        // All fields are valid, proceed with your logic

                        registerMail(email.getText().toString().trim(), password.getText().toString().trim()
                                , otp_str, refId);
                    } else {
                        register.setEnabled(true);
                        // Display the validation error message to the user
                        Toast.makeText(getContext(), validationMessage, Toast.LENGTH_SHORT).show();
                    }
                }


//            postRegister();
            }
        });
        otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRegisterMobile) {
                    String mobile_str = mobile.getText().toString().trim();
                    if (mobile_str.isEmpty()) {
                        Toast.makeText(getContext(), "email is required", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mobile_str.length() != 10) {
                        Toast.makeText(getContext(), "Mobile no must be of 10 digit", Toast.LENGTH_SHORT).show();
                        return;

                    }

                    getOtp(mobile_str);
                } else {
                    String email_str = email.getText().toString().trim();

                    if (email_str.isEmpty()) {
                        Toast.makeText(getContext(), "email is required", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    getMailOtp(email_str);
                }

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });

    }

    public String validateTextField() {
        // Check if OTP Code is empty
        String otpCodeText = otp_code.getText().toString();
        if (otpCodeText.isEmpty()) {
            return "OTP code is required";
        }
        if (otpCodeText.length() != 4) {
            return "OTP must be of 4 digit";
        }


        // Check if Password is empty and greater than 4 characters
        String passwordText = password.getText().toString();
        if (passwordText.isEmpty()) {
            return "Password is required";
        } else if (passwordText.length() <= 4) {
            return "Password must be greater than 4 characters";
        }

        // Check if Mobile is empty and has 10 digits
        String mobileText = email.getText().toString();
        if (mobileText.isEmpty()) {
            return "Mobile number is required";
        }
        String referralCodeText = refral.getText().toString();

        if(!referralCodeText.equals("")){
            if (!referralCodeText.matches("\\d+")) {
                return "Referral code must contain only numbers";
            }

        }
        // All conditions are met, return success message
        return "success";
    }


    private void postRegister() {
        if (menuHiddenListener != null) {
            menuHiddenListener.registerSuccess();
        }
    }

    private OnMenuHiddenListener menuHiddenListener;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnMenuHiddenListener) {
            menuHiddenListener = (OnMenuHiddenListener) context;

        } else {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        menuHiddenListener = null;

    }

    private void register(String phoneNo, String password, String otp, int refId) {
        customProgressDialog.show();
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            RegisterRequestModel registerRequestModel = new RegisterRequestModel(phoneNo, otp, password,String.valueOf(refId));

            ApiService apiService = RetrofitClient.getApiService();

            Call<CommonResponseModel> call = apiService.register(registerRequestModel);

            call.enqueue(new Callback<CommonResponseModel>() {
                @Override
                public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                    if (response.isSuccessful()) {
                        customProgressDialog.hide();

                        String authorizationHeader = response.headers().get("Authorization");

                        if (authorizationHeader != null) {
                            // Split the header to extract the token
                            String[] parts = authorizationHeader.split(" ");

                            if (parts.length == 2) {
                                String token = parts[1].trim();
                                Log.d("TAG", "token= " + token);
                                AppDataUtil appDataUtil = AppDataUtil.getInstance(requireActivity().getApplicationContext());
                                appDataUtil.setStringData(phoneNo, "field_id");
                                appDataUtil.setStringData(password,"field_password");
                                String encodedToken= appDataUtil.encodeString(token);
                                //save sp
                                if (appDataUtil.setStringData(encodedToken, "token")) {
                                    postRegister();
                                }

                            }
                            Log.d("TAG", "onResponse: Auth head" + authorizationHeader);
                        } else {
                            // Handle the case where the "Authorization" header is missing
                            Log.d("TAG", "Authorization header missing in response");
                        }

//
                    } else {
                        customProgressDialog.hide();
                        try {
                            // Handle the error response
                            String errorMessage = response.errorBody().string();
                            Toast.makeText(getContext(), "" + errorMessage, Toast.LENGTH_SHORT).show();
                            // Parse the error message from the JSON response
                            // You can use a JSON parsing library like Gson to extract the message field.
                            // Create a custom error response model if needed.
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // Handle the error response here
//                        Log.d("TAG", "onResponse1: error " + response.message());
                    }

                }

                @Override
                public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                    customProgressDialog.hide();
                    Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void registerMail(String email, String password, String otp, int refId) {
        customProgressDialog.show();
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            RegisterMailRequestModel registerRequestModel = new RegisterMailRequestModel(email, otp, password,String.valueOf(refId));

            ApiService apiService = RetrofitClient.getApiService();

            Call<CommonResponseModel> call = apiService.registerMail(registerRequestModel);

            call.enqueue(new Callback<CommonResponseModel>() {
                @Override
                public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                    if (response.isSuccessful()) {
                        customProgressDialog.hide();

                        String authorizationHeader = response.headers().get("Authorization");

                        if (authorizationHeader != null) {
                            // Split the header to extract the token
                            String[] parts = authorizationHeader.split(" ");

                            if (parts.length == 2) {
                                String token = parts[1].trim();
                                Log.d("TAG", "token my= " + token);
                                AppDataUtil appDataUtil = AppDataUtil.getInstance(requireActivity().getApplicationContext());
                                appDataUtil.setStringData(email, "field_id");
                                appDataUtil.setStringData(password,"field_password");
                                String encodedToken= appDataUtil.encodeString(token);
                                //save sp
                                if (appDataUtil.setStringData(encodedToken,"token")){
                                    postRegister();

                                }
//

                            }
                            Log.d("TAG", "onResponse: Auth head" + authorizationHeader);
                        } else {
                            // Handle the case where the "Authorization" header is missing
                            Log.d("TAG", "Authorization header missing in response");
                        }


                    } else {
                        customProgressDialog.hide();

                    }

                }

                @Override
                public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                    customProgressDialog.hide();
                    Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void getOtp(String phoneNumber) {
        customProgressDialog.show();
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            OtpRequestModel otpRequestModel = new OtpRequestModel(phoneNumber);

            ApiService apiService = RetrofitClient.getApiService();

            Call<CommonResponseModel> call = apiService.sendOtp(otpRequestModel);

            call.enqueue(new Callback<CommonResponseModel>() {
                @Override
                public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                    customProgressDialog.hide();
                    if (response.isSuccessful()) {

                        CommonResponseModel apiResponse = response.body();
                        boolean success = apiResponse.isSuccess();
                        otp_str = apiResponse.getMessage();
                        if (otp_str.length() == 4) {
                            otp_code.setText(otp_str);
                        }

                        // Handle the success and message here
                    } else {
                        // Handle the error response here
                        Log.d("TAG", "onResponse1: error " + response.message());
                    }
                    Log.d("TAG", "onResponse: " + response);

                }

                @Override
                public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                    Log.d("TAG", "onResponse22: error " + t.getMessage());
                }
            });
        });
    }

    private void getMailOtp(String email) {
        customProgressDialog.show();
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            OtpMailRequestModel otpRequestModel = new OtpMailRequestModel(email);

            ApiService apiService = RetrofitClient.getApiService();

            Call<CommonResponseModel> call = apiService.sendMailOtp(otpRequestModel);

            call.enqueue(new Callback<CommonResponseModel>() {
                @Override
                public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                    customProgressDialog.hide();
                    if (response.isSuccessful()) {

                        CommonResponseModel apiResponse = response.body();
                        boolean success = apiResponse.isSuccess();
                        otp_str = apiResponse.getMessage();
                        if (otp_str.length() == 4) {
                            otp_code.setText(otp_str);
                        }

                        // Handle the success and message here
                    } else {
                        // Handle the error response here
                        Log.d("TAG", "onRespons11111coming: error " + response.message());
                    }
                    Log.d("TAG", "onResponse: " + response);

                }

                @Override
                public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                    Log.d("TAG", "onResponse22: error " + t.getMessage());
                }
            });
        });
    }


    public interface OnMenuHiddenListener {
        void registerSuccess();
    }


}