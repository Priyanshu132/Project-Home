package com.example.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Facility_Adapter extends RecyclerView.Adapter<Facility_Adapter.MyViewHolder> {
    ArrayList<mess_name> list;
    int i_=0;

    public Facility_Adapter(ArrayList<mess_name> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.facility_layout,parent,false);
        return new Facility_Adapter.MyViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder,int i){
        myViewHolder.name.setText(list.get(i).getName().toUpperCase());
        myViewHolder.num.setText(" "+String.valueOf(i_)+".");


    }


    @Override
    public int getItemCount(){
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,num;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            name=itemView.findViewById(R.id.name);
            num=itemView.findViewById(R.id.number);
            i_++;

        }
    }
}
