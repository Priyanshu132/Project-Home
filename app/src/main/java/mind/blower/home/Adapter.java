package mind.blower.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    ArrayList<Student_deatils> list;

    public Adapter(ArrayList<Student_deatils> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new MyViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder,int i){
        myViewHolder.branch.setText(list.get(i).getBranch());
        myViewHolder.name.setText(list.get(i).getName());
        myViewHolder.room.setText(""+list.get(i).getRoom());
        myViewHolder.course.setText(list.get(i).getCourse());
        myViewHolder.mob.setText(""+list.get(i).getMob());
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,branch,room,course,mob;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.na);
            branch=(TextView)itemView.findViewById(R.id.br);
            mob=(TextView)itemView.findViewById(R.id.mo);
            course=(TextView)itemView.findViewById(R.id.co);
            room=(TextView)itemView.findViewById(R.id.ro);
        }
    }
}
