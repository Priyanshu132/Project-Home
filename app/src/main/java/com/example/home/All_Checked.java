package com.example.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class All_Checked extends AppCompatActivity {
    private String id_owner;
    private String id_current;
    private ArrayList<Date_Time> list;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__checked);
        id_current= getIntent().getStringExtra("main");
        id_owner=getIntent().getStringExtra("owner");

        if(id_owner.equals(id_current)) {

            databaseReference=FirebaseDatabase.getInstance().getReference().child("Owner's").child(id_owner).child("Owner_details").child("Check_Status");
        }
        else {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Owner's")
                    .child(id_owner).child("Student_details").child("All_Students").child(id_current).child("Check_Status");
        }
            recyclerView = (RecyclerView) findViewById(R.id.recycle_list2);


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

                            list.add(ds.getValue(Date_Time.class));

                        }
                        status_Adapter adapter=new status_Adapter(list);
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(All_Checked.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }



    }

}
