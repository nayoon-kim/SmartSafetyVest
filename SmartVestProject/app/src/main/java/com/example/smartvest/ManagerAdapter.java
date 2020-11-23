package com.example.smartvest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ManagerAdapter extends RecyclerView.Adapter<ManagerAdapter.ViewHolder> {
    ArrayList<Worker> items;
    Context context;
    public ManagerAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<Worker>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_safety_manager, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Worker item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Worker item){
        items.add(item);
    }

    public void setItems(ArrayList<Worker> items){
        this.items = items;
    }

    public Worker getItem(int position) {
        return items.get(position);
    }

    public void clearItems() {
        this.items.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView layout;
        TextView vest_number, vest_danger, vest_safety;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            layout = (CardView) itemView;
            vest_number = itemView.findViewById(R.id.vest_number);
            vest_danger = itemView.findViewById(R.id.vest_danger);
            vest_safety = itemView.findViewById(R.id.vest_safety);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        Worker item = items.get(position);
                        if (item.report_signal == 1) {
                            ConnectServer connectServer = new ConnectServer();
                            connectServer.request_update_warning_information(item.worker_id, false);
                            Toast.makeText(context, "신고를 확인하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            popUp(item);
                        }
                    }
                }
            });
        }

        public void setItem(Worker item) {
            if (item == null)
                return;
            String number = Integer.toString(item.worker_id);
            String state = "좋음";
            String factor = "없음";
            int color = ContextCompat.getColor(context, R.color.good);
            int color_back;
            if (item.report_signal != 1 && (item.state_methane == 1 || item.state_lpg == 1 || item.state_carbon_monoxide == 1)) {
                color_back = ContextCompat.getColor(context, R.color.warning_background);
                layout.setCardBackgroundColor(color_back);
                state = "주의";
                factor = "가스 농도 주의";
                color = ContextCompat.getColor(context, R.color.warning);
            }
            else if (item.report_signal != 1 && item.state_temperature == 1) {
                color_back = ContextCompat.getColor(context, R.color.warning_background);
                layout.setCardBackgroundColor(color_back);
                state = "주의";
                factor = "온도 주의";
                color = ContextCompat.getColor(context, R.color.warning);
            }
            else if (item.report_signal != 1 && item.state_humidity == 1) {
                color_back = ContextCompat.getColor(context, R.color.warning_background);
                layout.setCardBackgroundColor(color_back);
                state = "주의";
                factor = "습도 주의";
                color = ContextCompat.getColor(context, R.color.warning);
            }
            else if (item.report_signal != 1 && (item.state_methane == 2 || item.state_lpg == 2 || item.state_carbon_monoxide == 2)) {
                color_back = ContextCompat.getColor(context, R.color.bad_background);
                layout.setCardBackgroundColor(color_back);
                state = "위험";
                factor = "가스 농도 높음";
                color = ContextCompat.getColor(context, R.color.bad);
            }
            else if (item.report_signal != 1 && item.state_temperature == 2) {
                color_back = ContextCompat.getColor(context, R.color.bad_background);
                layout.setCardBackgroundColor(color_back);
                state = "위험";
                factor = "온도 위험";
                color = ContextCompat.getColor(context, R.color.bad);
            }
            else if (item.report_signal != 1 && item.state_humidity == 2) {
                color_back = ContextCompat.getColor(context, R.color.bad_background);
                layout.setCardBackgroundColor(color_back);
                state = "위험";
                factor = "습도 위험";
                color = ContextCompat.getColor(context, R.color.bad);
            }
            else if (item.report_signal == 1) {
                color_back = ContextCompat.getColor(context, R.color.bad_background);
                layout.setCardBackgroundColor(color_back);
                state = "신고 접수";
                factor = "신고";
                color = ContextCompat.getColor(context, R.color.bad);
            }
            else {
                color_back = ContextCompat.getColor(context, R.color.colorWhite);
                layout.setCardBackgroundColor(color_back);
            }

            vest_number.setText(number);
            vest_danger.setText(factor);
            vest_safety.setText(state);
            vest_safety.setTextColor(color);
        }

        private void popUp(Worker item) {
            PopupFragment popupFragment = new PopupFragment();
            Bundle bundle = new Bundle();
            AppCompatActivity activity = (AppCompatActivity)context;

            String title = "작업자 " + item.worker_id;
            String content = activity.getString(R.string.text_safety_temp) + " : " +
                    (int)item.temperature + "℃ / " + (int)item.humidity + "%\n\n" +
                    activity.getString(R.string.text_safety_methane) + " : " +
                    (int)item.methane + "ppm\n\n" +
                    activity.getString(R.string.text_safety_lpg) + " : " +
                    (int)item.lpg + "ppm\n\n" +
                    activity.getString(R.string.text_safety_carbon) + " : " +
                    (int)item.carbon_monoxide + "ppm\n";

            bundle.putString("title", title);
            bundle.putString("content", content);
            popupFragment.setArguments(bundle);
            popupFragment.show(activity.getSupportFragmentManager(), PopupFragment.TAG_POPUP_DIALOG);
        }
    }
}
