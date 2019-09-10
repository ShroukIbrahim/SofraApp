package com.moshrouk.sofra.data.reset.client;


import com.moshrouk.sofra.data.model.client.addReview.AddReview;
import com.moshrouk.sofra.data.model.client.clientlogin.ClientLogin;
import com.moshrouk.sofra.data.model.client.clientorders.ClientOrders;
import com.moshrouk.sofra.data.model.client.clientregister.ClientRegister;
import com.moshrouk.sofra.data.model.client.neworder.NewOrder;
import com.moshrouk.sofra.data.model.general.notifications.Notifications;
import com.moshrouk.sofra.data.model.general.notifications_token.NotificationsToken;
import com.moshrouk.sofra.data.model.general.password.newpassword.NewPassword;
import com.moshrouk.sofra.data.model.general.password.resetpassword.ResetPassword;
import com.moshrouk.sofra.data.model.restaurant.restaurantprofile.Profile;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ClientApiServices {

    //User
    @POST("login")
    @FormUrlEncoded
    Call<ClientLogin> clientLogin(@Field("email") String email, @Field("password") String password);

//
//    @POST("register")
//    @FormUrlEncoded
//    Call<ClientRegister> clientRegister(@Field("name") String name,
//                                        @Field("email") String email,
//                                        @Field("password") String password,
//                                        @Field("password_confirmation") String CPassword,
//                                        @Field("phone") String phone,
//                                        @Field("address") String address,
//                                        @Field("region_id") int region_id,
//                                        @Field("profile_image") int image);

    @Multipart
    @POST("sign-up")
    Call<ClientRegister> clientRegister(@Part("name") RequestBody name,
                                        @Part("email") RequestBody email,
                                        @Part("password") RequestBody password,
                                        @Part("password_confirmation") RequestBody CPassword,
                                        @Part("phone") RequestBody phone,
                                        @Part("address") RequestBody address,
                                        @Part("region_id") RequestBody region_id,
                                        @Part MultipartBody.Part file);


    @POST("reset-password")
    @FormUrlEncoded
    Call<ResetPassword> resetPassword(@Field("email") String email);

    @POST("new-password")
    @FormUrlEncoded
    Call<NewPassword> newPassword(@Field("code") String code,
                                  @Field("password") String password,
                                  @Field("password_confirmation") String password_confirmation);


    @POST("profile")
    @FormUrlEncoded
    Call<Profile> getUserData(@Field("api_token") String api_token);

    //order
    @GET("my-orders")
    Call<ClientOrders> getMyOrder(@Query("api_token") String api_token, @Query("state") String state, @Query("page") int page);

    @POST("new-order")
    @FormUrlEncoded
    Call<NewOrder> sendOrder(@Field("api_token") String api_token,
                             @Field("restaurant_id") int restaurant_id,
                             @Field("note") String note,
                             @Field("address") String address,
                             @Field("payment_method_id") int payment_method_id,
                             @Field("phone") String phone,
                             @Field("name") String name,
                             @Field("items[]") List<Integer> items,
                             @Field("quantities[]") List<Integer> quantities,
                             @Field("notes[]") List<String> notes);

    @POST("confirm-order")
    @FormUrlEncoded
    Call<ClientOrders> confirmOrder(@Field("order_id") int order_id,
                                   @Field("api_token") String api_token);

    @POST("decline-order")
    @FormUrlEncoded
    Call<ClientOrders> declineOrder( @Field("order_id") int order_id,
                                    @Field("api_token") String api_token);


    @POST("restaurant/review")
    @FormUrlEncoded
    Call<AddReview>AddReview(@Field("rate") float rate
            , @Field("comment") String comment   , @Field("restaurant_id") int restaurant_id
            , @Field("api_token") String api_token);



    //Notifications
    @GET("notifications")
    Call<Notifications> getNotificationsList(@Query("api_token") String api_token);


    @POST("remove-token")
    Call<NotificationsToken> removeToken(@Field("token") String token, @Field("api_token") String api_token);

    @POST("register-token")
    Call<NotificationsToken> registerToken(@Field("token") String token, @Field("api_token") String api_token, @Field("platform") String platform);

}
