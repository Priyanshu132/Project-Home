package com.example.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView hostelName;
    private String id;
    private TextView date_;
    private TextView time_;
    private TextView check_;
    private Date_Time date_time;
    private  long totalInOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date_=findViewById(R.id.date);
        time_=findViewById(R.id.time);
        check_=findViewById(R.id.check);
        date_time=new Date_Time();


        Button button1 = findViewById(R.id.profile);
        Button button2 = findViewById(R.id.mess);
        Button button3 = findViewById(R.id.payment);
        Button button4 = findViewById(R.id.student);
        Button button5 = findViewById(R.id.noti);
        Button button6 = findViewById(R.id.faci);
        Button button7 = findViewById(R.id.hostel);
        Button button8 = findViewById(R.id.about);
        hostelName=findViewById(R.id.hostelname);
        TextView t = findViewById(R.id.newt);
        LinearLayout linearLayout = findViewById(R.id.attendance);
        LinearLayout linearLayout1 = findViewById(R.id.scan);
        id=getIntent().getStringExtra("main");


            newThought(t);


            linearLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    scandata();
                }
            });


        DatabaseReference databaseReference;
        if(id.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Owner's");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child(id).child("Hostel_Detail").child("hostel_name").getValue().toString().toUpperCase();
                            String date = dataSnapshot.child(id).child("Owner_details").child("Last_Check_Status").child("date").getValue().toString();
                            String time = dataSnapshot.child(id).child("Owner_details").child("Last_Check_Status").child("time").getValue().toString();
                            String check = dataSnapshot.child(id).child("Owner_details").child("Last_Check_Status").child("check").getValue().toString();

                            date_.setText(date);
                            check_.setText("(" + check + ")");
                            time_.setText(time);
                            hostelName.setText(name);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Owner's");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child(id).child("Hostel_Detail").child("hostel_name").getValue().toString().toUpperCase();
                            String date = dataSnapshot.child(id).child("Student_details").child("All_Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Last_Check_Status").child("date").getValue().toString();
                            String time = dataSnapshot.child(id).child("Student_details").child("All_Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Last_Check_Status").child("time").getValue().toString();
                            String check = dataSnapshot.child(id).child("Student_details").child("All_Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Last_Check_Status").child("check").getValue().toString();

                            date_.setText(date);
                            check_.setText("(" + check + ")");
                            time_.setText(time);
                            hostelName.setText(name);



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,Profile.class);
                intent.putExtra("main",id);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Mess_time_table.class);
               // intent.putExtra("main",id);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.contentEquals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    Intent intent = new Intent(MainActivity.this, Student_payment_list.class);
                    intent.putExtra("main", id);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(MainActivity.this, Payment.class);
                    intent.putExtra("main", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    intent.putExtra("owner",id);    // for nothing
                    startActivity(intent);
                }
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Facility.class);
                intent.putExtra("main",id);
                startActivity(intent);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Hostelpic.class);
                intent.putExtra("main",id);
                startActivity(intent);
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,About.class);
                intent.putExtra("main",id);
                startActivity(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Student_list.class);
                intent.putExtra("main",id);
                startActivity(intent);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,Notification.class);
                intent.putExtra("main",id);
                startActivity(intent);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (id.contentEquals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    Intent intent = new Intent(MainActivity.this, Attendance.class);
                    intent.putExtra("main", id);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(MainActivity.this, All_Checked.class);
                    intent.putExtra("main", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    intent.putExtra("owner",id);    // for nothing
                    startActivity(intent);
                }
            }
        });
    }

    private void newThought(TextView newthought) {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        String dayOfMonthStr = String.valueOf(dayOfMonth);
        String t1="\"A day wasted on others is not wasted on one’s self\"";
        String t2="\"Creativity is intelligence having fun\"";
        String t3="\"Love only grows by sharing. You can only have more for yourself by giving it away to others\"";
        String t4="\"Kindness in words creates confidence. Kindness in thinking creates profoundness. Kindness in giving creates love\"";
        String t5="\"One who knows how to show and to accept kindness will be a friend better than any possession\"";
        String t6="\"Life is really generous to those who pursue their personal legend\"";
        String t7="\"There is no magic to achievement. It's really about hard work, choices and persistence\"";
        String t8="\"Every moment brings us some benediction. Even the rough hand of trial holds in its clasp for us some treasure of love\"";
        String t9="\"Actions speak louder than words and your love creates peace and goodwill among men\"";
        String t10="\"Success gives you the motivation to work more\"";
        String t11="\"When eating a fruit, think of the person who planted the tree\"";
        String t12="\"Climb the mountain so you can see the world, not so the world can see you\"";
        String t13="\"To forgive means pardoning the unpardonable\"";
        String t14="\"If you're not confident it's going to be hard to get that job, to get people to listen to you...\"";
        String t15="\"You only live once. But if you do it right, once is enough\"";
        String t16="\"The expert in anything was once a beginner\"";
        String t17="\"Preparation is the key to success\"";
        String t18="\"The most certain way to succeed is always to try just one more time\"";
        String t19="\"Be positive, patient and persistent. And you will be successful\"";
        String t20="\"Do not give up, the beginning is always the hardest\"";
        String t21="\"Mistakes are proof that you are trying\"";
        String t22="\"In a world in which you can be anything, be kind\"";
        String t23="\"Inhale courage, exhale fear\"";
        String t24="\"Make your vision so clear that your fears become irrelevant\"";
        String t25="\"Character is doing the right thing when nobody is looking\"";
        String t26="\"You don't have to be perfect to be amazing\"";
        String t27="\"Imagination is the highest kite that can fly\"";
        String t28="\"Imagination means nothing without doing\"";
        String t29="\"Paint is only wasted when it is in the tube\"";
        String t30="\"A book is a gift that you can open again and again\"";
        String t31="\"The best way to get rid of your problems is to face them\"";

        if(dayOfMonthStr.contentEquals("1")){
            newthought.setText(t1);
        }
        if(dayOfMonthStr.contentEquals("2")){
            newthought.setText(t2);
        }
        if(dayOfMonthStr.contentEquals("3")){
            newthought.setText(t3);
        }

        if(dayOfMonthStr.contentEquals("4")){
            newthought.setText(t4);
        }

        if(dayOfMonthStr.contentEquals("5")){
            newthought.setText(t5);
        }

        if(dayOfMonthStr.contentEquals("6")){
            newthought.setText(t6);
        }

        if(dayOfMonthStr.contentEquals("7")){
            newthought.setText(t7);
        }
        if(dayOfMonthStr.contentEquals("8")){
            newthought.setText(t8);
        }
        if(dayOfMonthStr.contentEquals("9")){
            newthought.setText(t9);
        }

        if(dayOfMonthStr.contentEquals("10")){
            newthought.setText(t10);
        }

        if(dayOfMonthStr.contentEquals("11")){
            newthought.setText(t11);
        }

        if(dayOfMonthStr.contentEquals("12")){
            newthought.setText(t12);
        }

        if(dayOfMonthStr.contentEquals("13")){
            newthought.setText(t13);
        }

        if(dayOfMonthStr.contentEquals("14")){
            newthought.setText(t14);
        }

        if(dayOfMonthStr.contentEquals("15")){
            newthought.setText(t15);
        }

        if(dayOfMonthStr.contentEquals("16")){
            newthought.setText(t16);
        }

        if(dayOfMonthStr.contentEquals("17")){
            newthought.setText(t17);
        }

        if(dayOfMonthStr.contentEquals("18")){
            newthought.setText(t18);
        }

        if(dayOfMonthStr.contentEquals("19")){
            newthought.setText(t19);
        }

        if(dayOfMonthStr.contentEquals("20")){
            newthought.setText(t20);
        }

        if(dayOfMonthStr.contentEquals("21")){
            newthought.setText(t21);
        }
        if(dayOfMonthStr.contentEquals("22")){
            newthought.setText(t22);
        }

        if(dayOfMonthStr.contentEquals("23")){
            newthought.setText(t23);
        }

        if(dayOfMonthStr.contentEquals("24")){
            newthought.setText(t24);
        }

        if(dayOfMonthStr.contentEquals("25")){
            newthought.setText(t25);
        }

        if(dayOfMonthStr.contentEquals("26")){
            newthought.setText(t26);
        }

        if(dayOfMonthStr.contentEquals("27")){
            newthought.setText(t27);
        }

        if(dayOfMonthStr.contentEquals("28")){
            newthought.setText(t28);
        }

        if(dayOfMonthStr.contentEquals("29")){
            newthought.setText(t29);
        }

        if(dayOfMonthStr.contentEquals("30")){
            newthought.setText(t30);
        }

        if(dayOfMonthStr.contentEquals("31")){
            newthought.setText(t31);
        }



    }

    private void scandata(){

        IntentIntegrator intentIntegrator=new IntentIntegrator(this);
        intentIntegrator.setCaptureActivity(Capture.class);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Scanning Code");
        intentIntegrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        IntentResult intentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(intentResult!=null){
            if(intentResult.getContents()!=null){
                Calendar calendar=Calendar.getInstance();
                String currentDate= DateFormat.getDateInstance().format(calendar.getTime());
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, HH:mm", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                date_.setText(currentDate);
                time_.setText(currentDateandTime);


                Task<Void> databaseReference1;
                if(id.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {

                    DatabaseReference d=FirebaseDatabase.getInstance().getReference().child("Owner's").child(id).child("Owner_details")
                            .child("Check_Status");

                    d.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                           totalInOut=dataSnapshot.getChildrenCount();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(MainActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });
                    if(totalInOut%2==0){
                        check_.setText("(In) ");
                        date_time.setCheck("In");
                    }
                    else {
                        check_.setText("(Out) ");
                        date_time.setCheck("Out");
                    }

                    date_time.setDate(currentDate);
                    date_time.setTime(currentDateandTime);
                    String cs="check_status_"+totalInOut;
                    databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Owner's").child(id).child("Owner_details")
                            .child("Check_Status").child(cs).setValue(date_time);
                    databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Owner's").child(id).child("Owner_details")
                            .child("Last_Check_Status").setValue(date_time);
                }
                else {
                    DatabaseReference d=FirebaseDatabase.getInstance().getReference().child("Owner's").child(id).child("Student_details")
                            .child("All_Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Check_Status");
                    d.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            totalInOut=dataSnapshot.getChildrenCount();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(MainActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    if(totalInOut%2==0){
                        check_.setText("(In) ");
                        date_time.setCheck("In");
                    }
                    else {
                        check_.setText("(Out) ");
                        date_time.setCheck("Out");
                    }

                    date_time.setDate(currentDate);
                    date_time.setTime(currentDateandTime);
                    String cs="check_status_"+totalInOut;
                    databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Owner's").child(id).child("Student_details")
                                .child("All_Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Check_Status").child(cs).setValue(date_time);
                    databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Owner's").child(id).child("Student_details")
                            .child("All_Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Last_Check_Status").setValue(date_time);
                }

                data.setAction(intentResult.getContents());
                AlertDialog.Builder dialog=new AlertDialog.Builder(this);
                dialog.setMessage(intentResult.getContents()+" Marked");
                dialog.setTitle("Scanning Result");
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog builder=dialog.create();
                builder.show();
            }
            else {
                Toast.makeText(MainActivity.this, "No result", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.profile,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(MainActivity.this,Login.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }
}
