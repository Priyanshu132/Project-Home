package mind.blower.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class About extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mto;
    private TextView phone,email;
    private DatabaseReference databaseReference;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        mto=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mto);
        mto.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navi);
        navigationView.setNavigationItemSelectedListener(this);
        phone=findViewById(R.id.Owner_phone);
        email=findViewById(R.id.owner_email);
        id=getIntent().getStringExtra("main");

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Owner's").child(id)
        .child("Owner_details");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String email1=dataSnapshot.child("email").getValue().toString();
                email.setText(email1);
                String phone1=dataSnapshot.child("mob").getValue().toString();
                phone.setText(phone1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
            Intent intent=new Intent(About.this,Payment.class);
            startActivity(intent);
        }
        if(id==R.id.nav_profile){
            Intent intent=new Intent(About.this,Profile.class);
            startActivity(intent);
        }
        if(id==R.id.nav_mess){
            Intent intent=new Intent(About.this,Mess_time_table.class);
            startActivity(intent);
        }
        if(id==R.id.nav_about){
            Intent intent=new Intent(About.this,About.class);
            startActivity(intent);
        }
        if(id==R.id.nav_attend){
            Intent intent=new Intent(About.this,Attendance.class);
            startActivity(intent);
        }
        if(id==R.id.nav_faci){
            Intent intent=new Intent(About.this,Facility.class);
            startActivity(intent);
        }
        if(id==R.id.nav_pic){
            Intent intent=new Intent(About.this,Hostelpic.class);
            startActivity(intent);
        }
        if(id==R.id.nav_noti){
            Intent intent=new Intent(About.this,Notification.class);
            startActivity(intent);
        }
        if(id==R.id.nav_student){
            Intent intent=new Intent(About.this,Student_list.class);
            startActivity(intent);
        }




        return false;
    }
}
