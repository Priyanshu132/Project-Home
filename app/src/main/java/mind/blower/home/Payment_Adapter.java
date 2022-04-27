package mind.blower.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Payment_Adapter extends RecyclerView.Adapter<Payment_Adapter.ViewHolder> {

    private ArrayList<Payment_show> list;
    private RecyclerViewClickListner recyclerViewClickListner;
        int i_=0;
       Context context;

    public Payment_Adapter(ArrayList<Payment_show> list,RecyclerViewClickListner listner,Context context) {
        this.list = list;
        this.context=context;
        this.recyclerViewClickListner=listner;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_layout,parent,false);
        return new Payment_Adapter.ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.name.setText(list.get(position).getName());
        holder.num.setText(" "+String.valueOf(i_)+".");
        Picasso.with(context).load(list.get(position).getImageUrl()).into(holder.cir);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView name,num;
            CircleImageView cir;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name_1);
            num=itemView.findViewById(R.id.number);
            cir=itemView.findViewById(R.id.image_1);
            i_++;


            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            recyclerViewClickListner.onClick(view,getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListner{
         void onClick(View v,int position);
    }
}
