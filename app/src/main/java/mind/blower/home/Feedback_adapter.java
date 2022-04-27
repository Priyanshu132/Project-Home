package mind.blower.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Feedback_adapter extends RecyclerView.Adapter<Feedback_adapter.MyViewHolder> {

    ArrayList<feedback_detail> list;

    public Feedback_adapter(ArrayList<feedback_detail> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Feedback_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_recycle_view,parent,false);
        return new Feedback_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Feedback_adapter.MyViewHolder holder, int position) {
        holder.mob.setText(list.get(position).getMobile());
        holder.name_.setText(list.get(position).getPerson_name());
        holder.sub.setText(list.get(position).getSub());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name_,mob,sub;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            name_=itemView.findViewById(R.id.name);
            mob=itemView.findViewById(R.id.Mn);
            sub=itemView.findViewById(R.id.subj);
        }
    }
}
