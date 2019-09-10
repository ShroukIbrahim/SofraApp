package com.moshrouk.sofra.adapter.general;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.general.notifications.DataNotify;
import com.moshrouk.sofra.helper.ISO8601DateParser;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    List<DataNotify> dataNotifies;
    Activity activity;


    public NotificationAdapter(List<DataNotify> dataNotifies, Activity activity) {
        this.dataNotifies = dataNotifies;
        this.activity = activity;
    }


    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_notfication_list, parent, false);
        return new NotificationViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.notifyName.setText(dataNotifies.get(position).getTitle());
        Date date = new Date();
        try {
            date = ISO8601DateParser.parse(dataNotifies.get(position).getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.time.setReferenceTime(date.getTime());


    }

    @Override
    public int getItemCount() {
        return dataNotifies.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notify_icon)
        ImageView notifyIcon;
        @BindView(R.id.notify_name)
        TextView notifyName;
        @BindView(R.id.time_icon)
        ImageView timeIcon;
        @BindView(R.id.time)
        RelativeTimeTextView time;
        View view;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
