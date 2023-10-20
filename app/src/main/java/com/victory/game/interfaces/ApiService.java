package com.victory.game.interfaces;

import com.victory.game.models.AddPayRequestModel;
import com.victory.game.models.AddRecordRequestModel;
import com.victory.game.models.AddWithdrawalRequestModel;
import com.victory.game.models.ColorUpdateRequest;
import com.victory.game.models.CurrentUserResponseModel;
import com.victory.game.models.GameResultResponseModel;
import com.victory.game.models.LoginRequestModel;
import com.victory.game.models.OtpRequestModel;
import com.victory.game.models.CommonResponseModel;
import com.victory.game.models.PUpdateRequestModel;
import com.victory.game.models.RegisterRequestModel;
import com.victory.game.models.UserRecordResponseModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/api/otp/send") // Endpoint for sending OTP
    Call<CommonResponseModel> sendOtp(@Body OtpRequestModel request);
    @POST("/api/users/register") // Endpoint for sending OTP
    Call<CommonResponseModel> register(@Body RegisterRequestModel request);

    @Headers({"Accept: application/json"})
    @POST("/api/users/login")
    Call<ResponseBody> login(@Body LoginRequestModel request);

    @POST("/api/game/update-color/user/{userId}")
    Call<CommonResponseModel> updateColor(
            @Header("Authorization") String token,
            @Path("userId") String userId,
            @Body ColorUpdateRequest updateRequest
    );
    @POST("/api/payments/payment/update/user/{userId}")
    Call<CommonResponseModel> updatePUser(
            @Header("Authorization") String token,
            @Path("userId") String userId,
            @Body PUpdateRequestModel request);

    @POST("/api/payments/payment/add")
    Call<CommonResponseModel> createPayment(
            @Header("Authorization") String token,
            @Body AddPayRequestModel request);

    @POST("/api/payments/withdrawal/add")
    Call<CommonResponseModel> createWithdrawalRequest(
            @Header("Authorization") String token,
            @Body AddWithdrawalRequestModel request);

    @GET("/api/users/current")
    Call<CurrentUserResponseModel> getCurrentUser(@Header("Authorization") String token);

    @GET("/api/users/get-key")
    Call<CommonResponseModel> getSecureKey();


    @GET("/api/game/get-color-result")
    Call<GameResultResponseModel> getColorResult();

    @POST("/api/users/record/add")
    Call<UserRecordResponseModel> addRecord(@Header("Authorization") String token, @Body AddRecordRequestModel recordRequest);

    @GET("/api/users/record/get/{userId}")
    Call<UserRecordResponseModel> getRecordsByUser(@Header("Authorization") String token,@Path("userId") String userId);


}
