package mind.blower.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Student_payment_list extends AppCompatActivity {

    private ArrayList<Payment_show> list;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    SearchView searchView;
    private Payment_Adapter.RecyclerViewClickListner listner;
    private TextView noresult;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_payment_list);
        id=getIntent().getStringExtra("main");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Owner's")
                .child(id).child("Student_details").child("All_Students");

        recyclerView=(RecyclerView)findViewById(R.id.recycle_list);
        searchView=(SearchView)findViewById(R.id.search);
        noresult=findViewById(R.id.noresult);



    }
    @Override
    public void onStart() {
        super.onStart();
        if (databaseReference != null) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        list = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                            list.add(ds.getValue(Payment_show.class));

                        }
                        Payment_Adapter adapter=new Payment_Adapter(list,listner,getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Student_payment_list.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(searchView!=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }

        listner=new Payment_Adapter.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {

                String s=list.get(position).getKey();
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),Payment.class);
                intent.putExtra("main", s);
                intent.putExtra("owner",id);
                startActivity(intent);
            }
        };
    }
    private void search(String str){

        ArrayList<Payment_show> mylist=new ArrayList<>();
        for(Payment_show obj: list){

            if(obj.getName().toLowerCase().contains(str.toLowerCase())){
                mylist.add(obj);
            }
        }
        if(mylist.isEmpty()){
            noresult.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
        else {
            noresult.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

        }
       Payment_Adapter adapter=new Payment_Adapter(mylist,listner,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

}
