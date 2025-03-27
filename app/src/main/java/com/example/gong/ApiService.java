package com.example.gong;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("register.php")  // PHP 파일 경로
    Call<ServerResponse> registerUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<ServerResponse> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );

}
