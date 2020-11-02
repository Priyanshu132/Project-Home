package com.example.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class Mess_time_table extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_time_table);

        section s=new section(this,getSupportFragmentManager());
        ViewPager viewPager=findViewById(R.id.view_pager);
        viewPager.setAdapter(s);
        TabLayout tabLayout=findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager) ;


        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        mto=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mto);
        mto.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navi);
        navigationView.setNavigationItemSelectedListener(this);
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
            Intent intent=new Intent(Mess_time_table.this,Payment.class);
            startActivity(intent);
        }
        if(id==R.id.nav_profile){
            Intent intent=new Intent(Mess_time_table.this,Profile.class);
            startActivity(intent);
        }
        if(id==R.id.nav_mess){
            Intent intent=new Intent(Mess_time_table.this,Mess_time_table.class);
            startActivity(intent);
        }
        if(id==R.id.nav_about){
            Intent intent=new Intent(Mess_time_table.this,About.class);
            startActivity(intent);
        }
        if(id==R.id.nav_attend){
            Intent intent=new Intent(Mess_time_table.this,Attendance.class);
            startActivity(intent);
        }
        if(id==R.id.nav_faci){
            Intent intent=new Intent(Mess_time_table.this,Facility.class);
            startActivity(intent);
        }
        if(id==R.id.nav_pic){
            Intent intent=new Intent(Mess_time_table.this,Hostelpic.class);
            startActivity(intent);
        }
        if(id==R.id.nav_noti){
            Intent intent=new Intent(Mess_time_table.this,Notification.class);
            startActivity(intent);
        }
        if(id==R.id.nav_student){
            Intent intent=new Intent(Mess_time_table.this,Student_list.class);
            startActivity(intent);
        }



        return false;
    }
}
