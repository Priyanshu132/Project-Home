package com.example.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class pic_Adapter extends RecyclerView.Adapter<pic_Adapter.ViewHolder> {

    private ArrayList<mess_name> list;
    Context context;



    public pic_Adapter(ArrayList<mess_name> list,Context context) {
        this.list = list;
        this.context=context;


    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pic_adapter_layout,parent,false);
        return new pic_Adapter.ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {


        Picasso.with(context).load(list.get(position).getName()).into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            imageView=itemView.findViewById(R.id.imageView);

        }


    }
}
