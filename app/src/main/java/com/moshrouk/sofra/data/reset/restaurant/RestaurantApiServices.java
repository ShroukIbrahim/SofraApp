package com.moshrouk.sofra.data.reset.restaurant;


import com.moshrouk.sofra.data.model.general.notifications.Notifications;
import com.moshrouk.sofra.data.model.general.notifications_token.NotificationsToken;
import com.moshrouk.sofra.data.model.general.password.newpassword.NewPassword;
import com.moshrouk.sofra.data.model.general.password.resetpassword.ResetPassword;
import com.moshrouk.sofra.data.model.restaurant.commissions.Commissions;
import com.moshrouk.sofra.data.model.restaurant.restaurantitems.RestaurantItems;
import com.moshrouk.sofra.data.model.restaurant.restaurantlogin.RestaurantLogin;
import com.moshrouk.sofra.data.model.restaurant.restaurantoffers.RestaurantOffers;
import com.moshrouk.sofra.data.model.restaurant.restaurantorder.Order;
import com.moshrouk.sofra.data.model.restaurant.restaurantorder.OrderData;
import com.moshrouk.sofra.data.model.restaurant.restaurantprofile.Profile;
import com.moshrouk.sofra.data.model.restaurant.restaurantregister.RestaurantRegister;

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

public interface RestaurantApiServices {

    //User

    @POST("login")
    @FormUrlEncoded
    Call<RestaurantLogin> restaurantLogin(@Field("email") String email, @Field("password") String password);


//    @POST("register")
//    @FormUrlEncoded
//    Call<RestaurantRegister> restaurantRegister(@Field("name") String name,
//                                                @Field("email") String email,
//                                                @Field("password") String password,
//                                                @Field("password_confirmation") String CPassword,
//                                                @Field("phone") String phone,
//                                                @Field("whatsapp") String whatsapp,
//                                                @Field("region_id") int region_id,
//                                                @Field("categories[]") List<Integer> categories,
//                                                @Field("delivery_cost") String delivery_cost,
//                                                @Field("minimum_charger") String minimum_charger,
//                                                @Field("profile_image") String image);

    @Multipart
    @POST("sign-up")
    Call<RestaurantRegister> restaurantRegister(
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("password_confirmation") RequestBody CPassword,
            @Part("phone") RequestBody phone,
            @Part("whatsapp") RequestBody whatsapp,
            @Part("region_id") RequestBody region_id,
            @Part("categories[]") List<RequestBody> categories,
            @Part("delivery_cost") RequestBody delivery_cost,
            @Part("minimum_charger") RequestBody minimum_charger,
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


    //Items

    @GET("my-items")
    Call<RestaurantItems> getAllItems(@Query("api_token") String api_token);

    @Multipart
    @POST("new-item")
    Call<RestaurantItems> addNewItems(@Part("description") RequestBody description,
                                      @Part("price") RequestBody price,
                                      @Part("preparing_time") RequestBody preparing_time,
                                      @Part("name") RequestBody name,
                                      @Part MultipartBody.Part file,
                                      @Part("api_token") RequestBody api_token);


    @Multipart
    @POST("update-item")
    Call<RestaurantItems> updateItem(@Part("description") RequestBody description,
                                     @Part("price") RequestBody price,
                                     @Part("preparing_time") RequestBody preparing_time,
                                     @Part("name") RequestBody name,
                                     @Part MultipartBody.Part file,
                                     @Part("item_id") RequestBody item_id,
                                     @Part("api_token") RequestBody api_token);

    @POST("delete-item")
    @FormUrlEncoded
    Call<RestaurantItems> deleteItem(@Field("item_id") int item_id, @Field("api_token") String api_token);

    //Order
    @GET("my-orders")
    Call<Order> getOrder(@Query("api_token") String api_token,
                         @Query("state") String state,
                         @Query("page") int page);


    @GET(" show-order")
    Call<OrderData> showOrder(@Query("api_token") String api_token,
                              @Query("order_id") int order_id);


    @POST("accept-order")
    @FormUrlEncoded
    Call<Order> acceptOrder(@Field("api_token") String api_token,
                         @Field("order_id") int order_id);

    @POST("reject-order")
    @FormUrlEncoded
    Call<Order> rejectOrder(@Field("api_token") String api_token,
                            @Field("order_id") int order_id);

    @POST("confirm-order")
    @FormUrlEncoded
    Call<Order> confirmOrder(@Field("order_id") int order_id,
                             @Field("api_token") String api_token);

    //Offers
    @GET("my-offers")
    Call<RestaurantOffers> getAllOffers(@Query("api_token") String api_token,
                                        @Query("page") int page);

    @Multipart
    @POST("new-offer")
    Call<RestaurantOffers> addNewOffer(@Part("description") RequestBody description,
                                       @Part("price") RequestBody price,
                                       @Part("starting_at") RequestBody starting_at,
                                       @Part("name") RequestBody name,
                                       @Part MultipartBody.Part file,
                                       @Part("ending_at") RequestBody ending_at,
                                       @Part("api_token") RequestBody api_token,
                                       @Part("offer_price") RequestBody offerPrice);

    @POST("delete-offer")
    @FormUrlEncoded
    Call<RestaurantOffers> deleteOffer(@Field("offer_id") int offer_id,
                                       @Field("api_token") String api_token);


    @Multipart
    @POST("update-offer")
    Call<RestaurantOffers> updateOffer(@Part("description") RequestBody description,
                                       @Part("price") RequestBody price,
                                       @Part("starting_at") RequestBody starting_at,
                                       @Part("name") RequestBody name,
                                       @Part MultipartBody.Part file,
                                       @Part("ending_at") RequestBody ending_at,
                                       @Part("api_token") RequestBody api_token,
                                       @Part("offer_id") RequestBody offerId);


    //commission
    @GET("commissions")
    Call<Commissions> getCommissions(@Query("api_token") String api_token);


    //Notifications
    @GET("notifications")
    Call<Notifications> getNotificationsList(@Query("api_token") String api_token);


    @POST("remove-token")
    Call<NotificationsToken> removeToken(@Field("token") String token, @Field("api_token") String api_token);

    @POST("register-token")
    Call<NotificationsToken> registerToken(@Field("token") String token, @Field("api_token") String api_token, @Field("platform") String platform);


}
