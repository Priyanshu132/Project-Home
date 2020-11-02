package com.example.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mto;
    private TextView wardenName;
    private TextView position;
    private TextView mob;
    private TextView gender;
    private TextView religion;
    private TextView email;
    private TextView address;
    private TextView S_year;
    private TextView year;
    private TextView course;
    private TextView S_course;
    private DatabaseReference databaseReference;
    private String id;
    private CircleImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        drawerLayout = (DrawerLayout) findViewById(R.id.dra);
        mto = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mto);
        mto.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navi);
        navigationView.setNavigationItemSelectedListener(this);
        position = (TextView) findViewById(R.id.rb);
        wardenName = (TextView) findViewById(R.id.rs);
        religion = (TextView) findViewById(R.id.re);
        address = (TextView) findViewById(R.id.add);
        gender = (TextView) findViewById(R.id.gender);
        email = (TextView) findViewById(R.id.email);
        mob = (TextView) findViewById(R.id.mob);
        S_course=findViewById(R.id.static_course);
        S_year=findViewById(R.id.Static_year);
        year=findViewById(R.id.year);
        course=findViewById(R.id.course);
        pic=findViewById(R.id.pic);
        id = getIntent().getStringExtra("main");



        if (id.contentEquals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            if(id.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Owner's").child(id);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String Email = dataSnapshot.child("Owner_details").child("email").getValue().toString();
                    String name = dataSnapshot.child("Owner_details").child("name").getValue().toString();
                    String re = dataSnapshot.child("Owner_details").child("religion").getValue().toString();
                    String mobNo = dataSnapshot.child("Owner_details").child("mob").getValue().toString();
                    String gend = dataSnapshot.child("Owner_details").child("gender").getValue().toString();
                    String add = dataSnapshot.child("Owner_details").child("loc").getValue().toString();
                    Picasso.with(getApplicationContext()).load(dataSnapshot.child("Owner_details").child("Owner_Image").child("name").getValue().toString()).into(pic);
                    position.setText("Owner");
                    wardenName.setText(name);
                    religion.setText(re);
                    gender.setText(gend);
                    email.setText(Email);
                    mob.setText(mobNo);
                    address.setText(add);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Profile.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Owner's").child(id).child("Student_details").child("All_Students")
            .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                 databaseReference.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         String Email = dataSnapshot.child("email").getValue().toString();
                         String name = dataSnapshot.child("name").getValue().toString();
                         String re = dataSnapshot.child("religion").getValue().toString();
                         String mobNo = dataSnapshot.child("mob").getValue().toString();
                         String gend = dataSnapshot.child("gender").getValue().toString();
                         String add = dataSnapshot.child("address").getValue().toString();
                         String Year=dataSnapshot.child("year").getValue().toString();
                         String Course=dataSnapshot.child("course").getValue().toString();
                         Picasso.with(getApplicationContext()).load(dataSnapshot.child("imageUrl").getValue().toString()).into(pic);

                         position.setText("Student");
                         wardenName.setText(name);
                         religion.setText(re);
                         gender.setText(gend);
                         email.setText(Email);
                         mob.setText(mobNo);
                         address.setText(add);
                         S_course.setVisibility(View.VISIBLE);
                         S_year.setVisibility(View.VISIBLE);
                         year.setVisibility(View.VISIBLE);
                         course.setVisibility(View.VISIBLE);
                         if(Year.equals("1")){
                             Year=Year.concat("st");
                         }
                         if(Year.equals("2")){
                             Year=Year.concat("nd");
                         }
                         if(Year.equals("3")){
                             Year= Year.concat("rd");
                         }
                         if(Year.equals("4")){
                             Year=Year.concat("th");
                         }
                         year.setText(Year);
                         course.setText(Course);


                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {
                         Toast.makeText(Profile.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

                     }
                 });
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mto.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id= menuItem.getItemId();

        if(id==R.id.nav_payment){
            Intent intent=new Intent(Profile.this,Payment.class);
            startActivity(intent);
        }
        if(id==R.id.nav_profile){
            Intent intent=new Intent(Profile.this,Profile.class);
            startActivity(intent);
        }
        if(id==R.id.nav_mess){
            Intent intent=new Intent(Profile.this,Mess_time_table.class);
            startActivity(intent);
        }
        if(id==R.id.nav_about){
            Intent intent=new Intent(Profile.this,About.class);
            startActivity(intent);
        }
        if(id==R.id.nav_attend){
            Intent intent=new Intent(Profile.this,Attendance.class);
            startActivity(intent);
        }
        if(id==R.id.nav_faci){
            Intent intent=new Intent(Profile.this,Facility.class);
            startActivity(intent);
        }
        if(id==R.id.nav_pic){
            Intent intent=new Intent(Profile.this,Hostelpic.class);
            startActivity(intent);
        }
        if(id==R.id.nav_noti){
            Intent intent=new Intent(Profile.this,Notification.class);
            startActivity(intent);
        }
        if(id==R.id.nav_student){
            Intent intent=new Intent(Profile.this,Student_list.class);
            startActivity(intent);
        }


        return false;
    }
}
