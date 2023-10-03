package com.victory.game.interfaces;

import com.victory.game.models.LoginRequestModel;
import com.victory.game.models.OtpRequestModel;
import com.victory.game.models.CommonResponseModel;
import com.victory.game.models.RegisterRequestModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface ApiService {
    @POST("/api/otp/send") // Endpoint for sending OTP
    Call<CommonResponseModel> sendOtp(@Body OtpRequestModel request);
    @POST("/api/users/register") // Endpoint for sending OTP
    Call<CommonResponseModel> register(@Body RegisterRequestModel request);
    @POST("/api/users/login")
    Call<CommonResponseModel> login(@Body LoginRequestModel request);
}
