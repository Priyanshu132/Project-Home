package mind.blower.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;



public class ListOfPaymentAdapter extends RecyclerView.Adapter<ListOfPaymentAdapter.ViewHolder> {

private ArrayList<transactionDetails> list;
        Context context;


public ListOfPaymentAdapter(ArrayList<transactionDetails> list, Context context) {
        this.list = list;
        this.context=context;
        }

@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_layout,parent,false);
        return new ListOfPaymentAdapter.ViewHolder(view);
        }

public void onBindViewHolder(ViewHolder holder, int position) {

        holder.date1.setText(list.get(position).getDate());
        holder.txnId1.setText(list.get(position).getTxn());
        holder.amt1.setText(list.get(position).getAmount());

        }

@Override
public int getItemCount() {
        return list.size();
        }
public class ViewHolder extends RecyclerView.ViewHolder {

    TextView date1,amt1,txnId1;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        date1=itemView.findViewById(R.id.date);
        txnId1=itemView.findViewById(R.id.txnId);
        amt1=itemView.findViewById(R.id.amt);



    }



}

        }
