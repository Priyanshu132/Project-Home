package com.example.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class mess_adapter extends RecyclerView.Adapter<mess_adapter.ViewHolder> {

    private ArrayList<mess_name> list;


    public mess_adapter(ArrayList<mess_name> list) {
        this.list = list;


    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mess_layout, parent, false);
        return new mess_adapter.ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.name.setText(list.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView name;

        //  RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //  relativeLayout=(RelativeLayout)itemView.findViewById(R.id.relative);
            name = (TextView) itemView.findViewById(R.id.name_1);


        }


    }
}
