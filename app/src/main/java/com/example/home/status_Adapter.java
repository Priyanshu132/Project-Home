package com.example.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class status_Adapter extends RecyclerView.Adapter<status_Adapter.ViewHolder> {

    private ArrayList<Date_Time> list;

    public status_Adapter(ArrayList<Date_Time> list) {
        this.list = list;
    }

    @Override
    public status_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.status,parent,false);
        return new status_Adapter.ViewHolder(view);
    }

    public void onBindViewHolder(status_Adapter.ViewHolder holder, int position) {

       holder.time_.setText(list.get(position).getTime());
       holder.check_.setText(list.get(position).getCheck());
       holder.date_.setText(list.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView check_,date_,time_;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date_=(TextView)itemView.findViewById(R.id.date);
            check_=(TextView)itemView.findViewById(R.id.status);
            time_=(TextView)itemView.findViewById(R.id.time);


        }



    }
}
