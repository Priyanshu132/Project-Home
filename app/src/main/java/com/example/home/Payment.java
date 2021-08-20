package com.example.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Payment extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mto;
    private FloatingActionButton fab;
    private FloatingActionButton fab3;
    private ImageView usedse;
    private  Button done;
    private Button done1;
    private editPayment editPayment;
    private Total_Payment total_payment;
    private TextView thhf;
    private  TextView tssf;
    private TextView phhf;
    private TextView pssf;
    private TextView dhf;
    private TextView dsf;
    private TextView us;
    private String UID;
    private String owner_id;
    private  int temp;
    private int temp1;
     private DatabaseReference databaseReference;
     private  LinearLayout linearLayout;
     private TextView ins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ins = findViewById(R.id.instru);
        fab = findViewById(R.id.fab1);
        fab3 = findViewById(R.id.fab2);
        usedse = findViewById(R.id.used);
        thhf = findViewById(R.id.totalhostelfee);
        tssf = findViewById(R.id.totalsecurityfee);
        pssf = findViewById(R.id.paidsecurityfee);
        phhf = findViewById(R.id.paidhostelfee);
        us = findViewById(R.id.usedsecurity);
        dsf = findViewById(R.id.duesecurityfee);
        linearLayout=findViewById(R.id.forOwneroOnly);
        dhf = findViewById(R.id.duehostelfee);
        UID = getIntent().getStringExtra("main");
        owner_id = getIntent().getStringExtra("owner");
        total_payment = new Total_Payment();
        editPayment = new editPayment();

        if (FirebaseAuth.getInstance().getCurrentUser().getUid().contentEquals(owner_id)) {


            databaseReference = FirebaseDatabase.getInstance().getReference().child("Owner's").child(owner_id)
                    .child("Student_details").child("All_Students").child(UID);


            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String Email = dataSnapshot.child("Paid_payments").child("paid_Hostel_fee").getValue().toString();
                    String Email1 = "Rs. " + Email + ".00 /-";
                    phhf.setText(Email1);
                    String name11 = dataSnapshot.child("Total_payments").child("total_Hostel_fee").getValue().toString();
                    String name1 = "Rs. " + name11 + ".00 /-";
                    thhf.setText(name1);
                    String re1 = dataSnapshot.child("Paid_payments").child("paid_Security_fee").getValue().toString();
                    String re = "Rs. " + re1 + ".00 /-";
                    pssf.setText(re);
                    String mobNo1 = dataSnapshot.child("Total_payments").child("total_Security_fee").getValue().toString();
                    String m = "Rs. " + mobNo1 + ".00 /-";
                    tssf.setText(m);
                    temp = Integer.parseInt(name11) - Integer.parseInt(Email);
                    dhf.setText("Rs. " + Integer.toString(temp) + ".00 /-");
                    temp1 = Integer.parseInt(mobNo1) - Integer.parseInt(re1);
                    dsf.setText("Rs. " + Integer.toString(temp1) + ".00 /-");
                    us.setText("Rs. " + "0000.00 /-");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            usedse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(Payment.this);
                    builder.setTitle("Edit Total Payment");
                    //    builder.setView(R.layout.edit_payment);
                    builder.show();

                }
            });

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(Payment.this);
                    builder.setTitle("Edit Total Payment");
                    View view11 = getLayoutInflater().inflate(R.layout.edit_payment, null);
                    final EditText TotalHfee = view11.findViewById(R.id.thf);
                    final EditText TotalSfee = view11.findViewById(R.id.tsf);
                    done1 = view11.findViewById(R.id.update1);
                    done1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TotalHfee.getText().toString().isEmpty() || TotalSfee.getText().toString().isEmpty()) {
                                Toast.makeText(Payment.this, "Enter the Amount", Toast.LENGTH_SHORT).show();
                            } else {
                                total_payment.setTotal_Hostel_fee(TotalHfee.getText().toString());
                                total_payment.setTotal_Security_fee(TotalSfee.getText().toString());
                                databaseReference.child("Total_payments").setValue(total_payment);
                                Toast.makeText(Payment.this, "Amount is Updated", Toast.LENGTH_LONG).show();
                                TotalHfee.setText("");
                                TotalSfee.setText("");
                            }
                        }
                    });
                    builder.setView(view11);
                    AlertDialog dialog = builder.create();
                    dialog.show();


                }
            });
            fab3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Payment.this);
                    builder.setTitle("Edit Paid Payment");
                    View view1 = getLayoutInflater().inflate(R.layout.edit_paid_payment, null);
                    final EditText paidHfee = view1.findViewById(R.id.thf);
                    final EditText paidSfee = view1.findViewById(R.id.tsf);
                    done = view1.findViewById(R.id.update);
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (paidHfee.getText().toString().isEmpty() || paidSfee.getText().toString().isEmpty()) {
                                Toast.makeText(Payment.this, "Enter the Amount", Toast.LENGTH_SHORT).show();
                            } else {
                                editPayment.setPaid_Hostel_fee(paidHfee.getText().toString());
                                editPayment.setPaid_Security_fee(paidSfee.getText().toString());
                                databaseReference.child("Paid_payments").setValue(editPayment);
                                Toast.makeText(Payment.this, "Amount is Updated", Toast.LENGTH_LONG).show();
                                paidHfee.setText("");
                                paidSfee.setText("");

                                //  databaseReference.child("Paid_payments").setValue(editPayment);
                            }
                        }
                    });

                    //   builder.setView(R.layout.edit_paid_payment);
                    builder.setView(view1);
                    AlertDialog dialog = builder.create();
                    dialog.show();

//


                }
            });
        }
        else {
            linearLayout.setVisibility(View.GONE);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Owner's").child(owner_id)
                    .child("Student_details").child("All_Students").child(UID);


            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String Email = dataSnapshot.child("Paid_payments").child("paid_Hostel_fee").getValue().toString();
                    String Email1 = "Rs. " + Email + ".00 /-";
                    phhf.setText(Email1);
                    String name11 = dataSnapshot.child("Total_payments").child("total_Hostel_fee").getValue().toString();
                    String name1 = "Rs. " + name11 + ".00 /-";
                    thhf.setText(name1);
                    String re1 = dataSnapshot.child("Paid_payments").child("paid_Security_fee").getValue().toString();
                    String re = "Rs. " + re1 + ".00 /-";
                    pssf.setText(re);
                    String mobNo1 = dataSnapshot.child("Total_payments").child("total_Security_fee").getValue().toString();
                    String m = "Rs. " + mobNo1 + ".00 /-";
                    tssf.setText(m);
                    temp = Integer.parseInt(name11) - Integer.parseInt(Email);
                    dhf.setText("Rs. " + Integer.toString(temp) + ".00 /-");
                    temp1 = Integer.parseInt(mobNo1) - Integer.parseInt(re1);
                    dsf.setText("Rs. " + Integer.toString(temp1) + ".00 /-");
                    us.setText("Rs. " + "0000.00 /-");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            ins.setVisibility(View.VISIBLE);
            dhf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Botton bottomSheet = new Botton(owner_id);
                    bottomSheet.show(getSupportFragmentManager(), "TAG");
                }
            });

        }
            drawerLayout = findViewById(R.id.drawer);
            mto = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
            drawerLayout.addDrawerListener(mto);
            mto.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            NavigationView navigationView = findViewById(R.id.navi);
            navigationView.setNavigationItemSelectedListener(this);




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.transaction,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mto.onOptionsItemSelected(item)){
            return true;
        }
//        switch (item.getItemId()){
            if(R.id.transaxtion == item.getItemId()){
                Intent i = new Intent(getApplicationContext(),ListOfPayments.class);
                i.putExtra("ownerId",owner_id);
                i.putExtra("uid",UID);
                //Log.e("priyanshu","come");
                startActivity(i);
            }


//
//                Button button=(Button)findViewById(R.id.update);
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(Payment.this,"Updated",Toast.LENGTH_SHORT).show();
//                    }
//                });
             //   builder.show();
             //   return true;
             //   default:
                    return super.onOptionsItemSelected(item);


    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id= menuItem.getItemId();


        if(id==R.id.nav_payment){
            Intent intent=new Intent(Payment.this,Payment.class);
            startActivity(intent);
        }
        if(id==R.id.nav_profile){
            Intent intent=new Intent(Payment.this,Profile.class);
            startActivity(intent);
        }
        if(id==R.id.nav_mess){
            Intent intent=new Intent(Payment.this,Mess_time_table.class);
            startActivity(intent);
        }
        if(id==R.id.nav_about){
            Intent intent=new Intent(Payment.this,About.class);
            startActivity(intent);
        }
        if(id==R.id.nav_attend){
            Intent intent=new Intent(Payment.this,Attendance.class);
            startActivity(intent);
        }
        if(id==R.id.nav_faci){
            Intent intent=new Intent(Payment.this,Facility.class);
            startActivity(intent);
        }
        if(id==R.id.nav_pic){
            Intent intent=new Intent(Payment.this,Hostelpic.class);
            startActivity(intent);
        }
        if(id==R.id.nav_noti){
            Intent intent=new Intent(Payment.this,Notification.class);
            startActivity(intent);
        }
        if(id==R.id.nav_student){
            Intent intent=new Intent(Payment.this,Student_list.class);
            startActivity(intent);
        }




        return false;
    }





}
