package com.moshrouk.sofra.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HelperMethod {

    // Method Replace used to transaction between activity to fragment and set tittle of activity
    public static void replace(Fragment fragment, FragmentManager fragmentManager, int id, TextView bar_tittle, String tittle) {
       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        if (bar_tittle != null) {
            bar_tittle.setText(tittle);

        }
    }
    public static void openAlbum( Context context, final ArrayList<AlbumFile> ImagesFiles, Action<ArrayList<AlbumFile>> action) {
        Album album = new Album();
        Album.initialize(AlbumConfig.newBuilder(context)
                .setAlbumLoader(new MediaLoader())
                .setLocale(Locale.ENGLISH).build());
                 album.image(context)// Image and video mix options.
                .multipleChoice()// Multi-Mode, Single-Mode: singleChoice().
                .checkedList(ImagesFiles) // To reverse the list.
                .onResult(action)
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                          // The user canceled the operation.
                    }
                })
                .start();
    }

    private static class MediaLoader implements AlbumLoader {
        @Override
        public void load(ImageView imageView, AlbumFile albumFile) {
            load(imageView, albumFile.getPath());
        }

        @Override
        public void load(ImageView imageView, String url) {
            Glide.with(imageView.getContext()).load(url)
                    .into(imageView);
        }
    }
    public static RequestBody convertToRequestBody(String part) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), part);
        return requestBody;
    }

    public static MultipartBody.Part convertTOMultipart(String pathImageFile, String Key) {
        File file = new File(pathImageFile);
        RequestBody reqFileselect = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part Imagebody = MultipartBody.Part.createFormData(Key, file.getName(), reqFileselect);
        return Imagebody;
    }



    public static void disappearKeypad(Activity activity, View v) {
        try {
            if (v != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (Exception e) {
        }
    }

    //Calender
    public static void showCalender(Context context, String title, final TextView text_view_data, final DateModel data1) {
        DatePickerDialog mDatePicker = new DatePickerDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                        DecimalFormat mFormat = new DecimalFormat("00");
                        if (!data1.getMonth().equals(mFormat.format(Double.valueOf(selectedMonth)))) {
                            String data = selectedYear + "-" + mFormat.format(Double.valueOf((selectedMonth + 1))) + "-" + mFormat.format(Double.valueOf(selectedDay));
                            data1.setDate_txt(data);
                            data1.setDay(mFormat.format(Double.valueOf(selectedDay)));
                            data1.setMonth(mFormat.format(Double.valueOf(selectedMonth + 1)));
                            data1.setYear(String.valueOf(selectedYear));
                            text_view_data.setText(data);
                        }
                    }
                }, Integer.parseInt(data1.getYear()), Integer.parseInt(data1.getMonth()), Integer.parseInt(data1.getDay()));
        mDatePicker.setTitle(title);
        mDatePicker.show();
    }

    //This method for make Call
    public static void makePhoneCall(Context context, String phone) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phone));
        context.startActivity(callIntent);

    }




}
