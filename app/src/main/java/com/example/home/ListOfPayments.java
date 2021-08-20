package com.example.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListOfPayments extends AppCompatActivity {

    RecyclerView recyclerView;
    private ArrayList<transactionDetails> list;
    private DatabaseReference databaseReference;

    String id;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_payments);
        Log.d("priyanshu","come");
        id = getIntent().getStringExtra("ownerId");
        uid = getIntent().getStringExtra("uid");
        recyclerView = findViewById(R.id.listOfPayments);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Owner's")
                .child(id).child("Student_details").child("All_Students").child(uid).child("List_of_payments");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    list = new ArrayList<>();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        list.add(ds.getValue(transactionDetails.class));
                    }
                    ListOfPaymentAdapter listOfPaymentAdapter = new ListOfPaymentAdapter(list,getApplicationContext());
                    recyclerView.setAdapter(listOfPaymentAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(getApplicationContext(),"ju",Toast.LENGTH_LONG).show();


    }


}

