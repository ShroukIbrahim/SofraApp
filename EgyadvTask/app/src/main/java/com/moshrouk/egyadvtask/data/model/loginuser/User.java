
package com.moshrouk.egyadvtask.data.model.loginuser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("role_id")
    @Expose
    private String roleId;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("user_firstname")
    @Expose
    private String userFirstname;
    @SerializedName("user_lastname")
    @Expose
    private String userLastname;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_mobile")
    @Expose
    private String userMobile;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("user_lat")
    @Expose
    private String userLat;
    @SerializedName("user_lng")
    @Expose
    private String userLng;
    @SerializedName("email_verified_at")
    @Expose
    private Object emailVerifiedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("user_rate")
    @Expose
    private String userRate;
    @SerializedName("user_rate_count")
    @Expose
    private String userRateCount;
    @SerializedName("extra_mobile")
    @Expose
    private Object extraMobile;
    @SerializedName("user_bio")
    @Expose
    private Object userBio;
    @SerializedName("confirm_code")
    @Expose
    private Object confirmCode;
    @SerializedName("is_confirmed")
    @Expose
    private String isConfirmed;
    @SerializedName("is_accepted")
    @Expose
    private String isAccepted;
    @SerializedName("user_img")
    @Expose
    private String userImg;
    @SerializedName("user_header")
    @Expose
    private Object userHeader;
    @SerializedName("role")
    @Expose
    private Object role;
    @SerializedName("upload_id")
    @Expose
    private Object uploadId;
    @SerializedName("proff")
    @Expose
    private Object proff;
    @SerializedName("sub_proff")
    @Expose
    private Object subProff;
    @SerializedName("price_id")
    @Expose
    private Object priceId;
    @SerializedName("user_credit")
    @Expose
    private String userCredit;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("recover")
    @Expose
    private Object recover;
    @SerializedName("my_followers")
    @Expose
    private String myFollowers;
    @SerializedName("following")
    @Expose
    private String following;
    @SerializedName("is_verified")
    @Expose
    private String isVerified;
    @SerializedName("city")
    @Expose
    private City city;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getUserLat() {
        return userLat;
    }

    public void setUserLat(String userLat) {
        this.userLat = userLat;
    }

    public String getUserLng() {
        return userLng;
    }

    public void setUserLng(String userLng) {
        this.userLng = userLng;
    }

    public Object getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Object emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserRate() {
        return userRate;
    }

    public void setUserRate(String userRate) {
        this.userRate = userRate;
    }

    public String getUserRateCount() {
        return userRateCount;
    }

    public void setUserRateCount(String userRateCount) {
        this.userRateCount = userRateCount;
    }

    public Object getExtraMobile() {
        return extraMobile;
    }

    public void setExtraMobile(Object extraMobile) {
        this.extraMobile = extraMobile;
    }

    public Object getUserBio() {
        return userBio;
    }

    public void setUserBio(Object userBio) {
        this.userBio = userBio;
    }

    public Object getConfirmCode() {
        return confirmCode;
    }

    public void setConfirmCode(Object confirmCode) {
        this.confirmCode = confirmCode;
    }

    public String getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(String isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public String getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(String isAccepted) {
        this.isAccepted = isAccepted;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public Object getUserHeader() {
        return userHeader;
    }

    public void setUserHeader(Object userHeader) {
        this.userHeader = userHeader;
    }

    public Object getRole() {
        return role;
    }

    public void setRole(Object role) {
        this.role = role;
    }

    public Object getUploadId() {
        return uploadId;
    }

    public void setUploadId(Object uploadId) {
        this.uploadId = uploadId;
    }

    public Object getProff() {
        return proff;
    }

    public void setProff(Object proff) {
        this.proff = proff;
    }

    public Object getSubProff() {
        return subProff;
    }

    public void setSubProff(Object subProff) {
        this.subProff = subProff;
    }

    public Object getPriceId() {
        return priceId;
    }

    public void setPriceId(Object priceId) {
        this.priceId = priceId;
    }

    public String getUserCredit() {
        return userCredit;
    }

    public void setUserCredit(String userCredit) {
        this.userCredit = userCredit;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Object getRecover() {
        return recover;
    }

    public void setRecover(Object recover) {
        this.recover = recover;
    }

    public String getMyFollowers() {
        return myFollowers;
    }

    public void setMyFollowers(String myFollowers) {
        this.myFollowers = myFollowers;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
