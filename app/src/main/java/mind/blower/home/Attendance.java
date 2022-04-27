package mind.blower.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Attendance extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mto;
    private String id;
    private ArrayList<Payment_show> list;
    private DatabaseReference databaseReference;
    SearchView searchView;
    private Payment_Adapter.RecyclerViewClickListner listner;
    private RecyclerView recyclerView;
    private TextView noresult;
    private LinearLayout linearLayout;
    private CircleImageView cir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        linearLayout=findViewById(R.id.OwnerStatus);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        mto=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mto);
        mto.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navi);
        navigationView.setNavigationItemSelectedListener(this);
        id=getIntent().getStringExtra("main");
        cir=findViewById(R.id.image_1);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Attendance.this,All_Checked.class);
                intent.putExtra("main",id);
                intent.putExtra("owner",id);
                startActivity(intent);
            }
        });

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Owner's")
                .child(id).child("Student_details").child("All_Students");//.child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        recyclerView=(RecyclerView)findViewById(R.id.recycle_list1);
        searchView=(SearchView)findViewById(R.id.search);
        noresult=findViewById(R.id.noresult);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
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
                    Toast.makeText(Attendance.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
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
           //    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),All_Checked.class);
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



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id= menuItem.getItemId();

        if(id==R.id.nav_payment){
            Intent intent=new Intent(Attendance.this,Payment.class);
            startActivity(intent);
        }
        if(id==R.id.nav_profile){
            Intent intent=new Intent(Attendance.this,Profile.class);
            startActivity(intent);
        }
        if(id==R.id.nav_mess){
            Intent intent=new Intent(Attendance.this,Mess_time_table.class);
            startActivity(intent);
        }
        if(id==R.id.nav_about){
            Intent intent=new Intent(Attendance.this,About.class);
            startActivity(intent);
        }
        if(id==R.id.nav_attend){
            Intent intent=new Intent(Attendance.this,Attendance.class);
            startActivity(intent);
        }
        if(id==R.id.nav_faci){
            Intent intent=new Intent(Attendance.this,Facility.class);
            startActivity(intent);
        }
        if(id==R.id.nav_pic){
            Intent intent=new Intent(Attendance.this,Hostelpic.class);
            startActivity(intent);
        }
        if(id==R.id.nav_noti){
            Intent intent=new Intent(Attendance.this,Notification.class);
            startActivity(intent);
        }
        if(id==R.id.nav_student){
            Intent intent=new Intent(Attendance.this,Student_list.class);
            startActivity(intent);
        }


        return false;
    }
}
