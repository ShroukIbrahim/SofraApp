package com.moshrouk.sofra.data.reset.general;


import com.moshrouk.sofra.data.model.general.addContact.AddContact;
import com.moshrouk.sofra.data.model.general.categories.Categories;
import com.moshrouk.sofra.data.model.general.cities.Cities;
import com.moshrouk.sofra.data.model.general.notifications_token.NotificationsToken;
import com.moshrouk.sofra.data.model.general.offers.Offers;
import com.moshrouk.sofra.data.model.general.restaurant_items.Items;
import com.moshrouk.sofra.data.model.general.restaurantdetails.RestaurantDetails;
import com.moshrouk.sofra.data.model.general.restaurantreviews.RestaurantReviews;
import com.moshrouk.sofra.data.model.general.restaurants_list.RestaurantsList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GeneralApiServices {

    //User
    @GET("cities-not-paginated")
    Call<Cities> getAllCities();

    @GET("regions-not-paginated")
    Call<Cities> getregons(@Query("city_id") int id);

    @GET("categories")
    Call<Categories> getAllCategories();


    //restaurants
    @GET("restaurants")
    Call<RestaurantsList> getAllRestaurants();

    @GET("restaurants")
    Call<RestaurantsList> getFilterRestaurants( @Query("keywork") String keywork ,@Query("region_id") int region_id);

    @GET("restaurant")
    Call<RestaurantDetails> getRestaurantsDetails(@Query("restaurant_id") int restaurant_id);

    @GET("items")
    Call<Items> getAllItems(@Query("restaurant_id") int restaurant_id);

     //offers
     @GET("offers")
     Call<Offers> getAllOffers();


    @GET("restaurant/reviews")
    Call<RestaurantReviews> getRestaurantComments(@Query("api_token") String api_token,@Query("restaurant_id") int restaurant_id);

    @POST("restaurant/remove-token")
    @FormUrlEncoded
    Call<NotificationsToken >restaurantRemoveToken(@Field("token") String token, @Field("api_token") String api_token);

    @POST("restaurant/register-token")
    @FormUrlEncoded
    Call<NotificationsToken>restaurantRegisterToken(@Field("token") String token
            , @Field("api_token") String api_token, @Field("type") String type);

    @POST("client/register-token")
    @FormUrlEncoded
    Call<NotificationsToken>clientRegisterToken(@Field("token") String token
            , @Field("api_token") String api_token, @Field("type") String type);


    @POST("client/remove-token")
    @FormUrlEncoded
    Call<NotificationsToken >clientRemoveToken(@Field("token") String token, @Field("api_token") String api_token);


    @POST("contact")
    @FormUrlEncoded
    Call<AddContact>addContact(@Field("name") String name, @Field("email") String email, @Field("phone") String phone
            , @Field("type") String type, @Field("content") String content);





//    @POST("register")
//    @FormUrlEncoded
//    Call<> onRegister(@Field("name") String name,
//                              @Field("email") String email,
//                              @Field("birth_date") String birth_date,
//                              @Field("city_id") int city_id,
//                              @Field("phone") String phone,
//                              @Field("donation_last_date") String donation_last_date,
//                              @Field("password") String password,
//                              @Field("password_confirmation") String password_confirmation,
//                              @Field("blood_type_id") int blood_type_id);
//
//    @POST("reset-password")
//    @FormUrlEncoded
//    Call<ResetPassword> resetPassword(@Field("phone") String phone);
//
//    @POST("new-password")
//    @FormUrlEncoded
//    Call<NewPassword> newPassword(@Field("password") String password,
//                                  @Field("password_confirmation") String confirm_password,
//                                  @Field("pin_code") String pin_code,
//                                  @Field("phone") String phone);
//    @POST("profile")
//    @FormUrlEncoded
//    Call<Login> getUserData(@Field("api_token") String api_token);
//
//    @POST("profile")
//    @FormUrlEncoded
//    Call<Login> editUserData(@Field("name") String name,
//                             @Field("email") String email,
//                             @Field("birth_date") String birth_date,
//                             @Field("city_id") int city_id,
//                             @Field("phone") String phone,
//                             @Field("donation_last_date") String donation_last_date,
//                             @Field("password") String password,
//                             @Field("password_confirmation") String password_confirmation,
//                             @Field("blood_type_id") int blood_type_id,
//                             @Field("api_token") String api_token);
//
//    //General
//
//    @GET("governorates")
//    Call<Governorates> getGovernorates();
//
//    @GET("cities")
//    Call<Governorates> getCities(@Query("governorate_id") int cities_id);
//
//    @GET("cities")
//    Call<Governorates> getAllCities();
//
//    @GET("blood-types")
//    Call<BloodTypes> getBloodTypes();
//
//    @GET("categories")
//    Call<Categories> getCategories();
//
//    @GET("settings")
//    Call<Settings> getSetting(@Query("api_token") String api_token);
//
//    @POST("contact")
//    @FormUrlEncoded
//    Call<Contact> contact(@Field("api_token") String api_token,
//                          @Field("title") String tittle,
//                          @Field("message") String massage);
//
//    //POSTS
//
//    @GET("posts")
//    Call<Posts> getAllPost(@Query("page") int page, @Query("api_token") String api_token);
//
//    @GET("posts")
//    Call<Posts> getFilterPost(@Query("api_token") String api_token,
//                              @Query("keyword") String keyword,
//                              @Query("category_id") int category_id);
//    @GET("posts")
//    Call<Post> getPostDetails(@Query("api_token") String api_token,
//                              @Query("post_id") int id
//    );
//
//    @GET("my-favourites")
//    Call<Posts> getFavouritePost(@Query("api_token") String api_token);
//
//    @POST("post-toggle-favourite")
//    @FormUrlEncoded
//    Call<PostFavourite> add_removeFavourit(@Field("post_id") int post_id,
//                                           @Field("api_token") String api_token);
//
////DONATION
//    @GET("donation-requests")
//    Call<DonationRequests> getAllDonation(@Query("api_token") String api_token, @Query("page") int page);
//
//    @GET("donation-requests")
//    Call<DonationRequests> getDonationFilter(@Query("api_token") String api_token,
//                                             @Query("blood_type_id") int blood_type_id,
//                                             @Query("city_id") int city_id);
//    @GET("donation-request")
//    Call<DonationRequestt> getDonationDetails(@Query("api_token") String api_token,
//                                              @Query("donation_id") int id);
//
//    @POST("donation-request/create")
//    @FormUrlEncoded
//    Call<CreateDonationRequest> createDonationRequest(@Field("api_token") String apiToken,
//                                                      @Field("patient_name") String patientName,
//                                                      @Field("patient_age") String patientAge,
//                                                      @Field("blood_type_id") int bloodTypeId,
//                                                      @Field("bags_num") int bagsNum,
//                                                      @Field("hospital_name") String hospitalName,
//                                                      @Field("hospital_address") String hospitalAddress,
//                                                      @Field("city_id") int cityId,
//                                                      @Field("phone") String phone,
//                                                      @Field("notes") String notes,
//                                                      @Field("latitude") double latitude,
//                                                      @Field("longitude") double longitude);
//
//
//
////Notification
//    @POST("notifications-settings")
//    @FormUrlEncoded
//    Call<NotificationsSettings> getNotificationsSettings(@Field("api_token") String api_token);
//
//    @POST("notifications-settings")
//    @FormUrlEncoded
//    Call<NotificationsSettings> ChangeNotificationsSettings(@Field("api_token") String api_token,
//                                                            @Field("governorates[]") List<Integer> governorates,
//                                                            @Field("blood_types[]") List<Integer> blood_types);
//
//    @GET("notifications")
//    Call<Notifications> getNotificationsList(@Query("api_token") String api_token);
//
//
//
//
//    @GET("notifications-count")
//    Call<NotificationsCount> getNotificationCount(@Query("api_token") String api_token);
//
//    @POST("remove-token")
//    Call<RemoveToken> removeToken(@Field("token") String token, @Field("api_token") String api_token);
//
//    @POST("register-token")
//    Call<RegisterToken> registerToken(@Field("token") String token, @Field("api_token") String api_token, @Field("platform") String platform);


}
