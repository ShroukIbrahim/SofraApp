package com.moshrouk.egyadvtask.data.reset;


import com.moshrouk.egyadvtask.data.model.loginuser.LoginUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiServices {

    //User
    @POST("LoginUser")
    @FormUrlEncoded
    Call<LoginUser> onLogin(@Field("login") String login, @Field("password") String password);


}
