package mind.blower.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Notification extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mto;
    private ArrayList<mess_name> list;
    private RecyclerView recyclerView;
    private mess_name mess_name1;
    private DatabaseReference databaseReference;
    private int listsize;
    private String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        mto=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mto);
        mto.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navi);
        navigationView.setNavigationItemSelectedListener(this);
        id=getIntent().getStringExtra("main");

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Owner's")
                .child(id).child("Rules");

        recyclerView=(RecyclerView)findViewById(R.id.recycle);
        mess_name1=new mess_name();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.faci, menu);
        return true;
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

                            list.add(ds.getValue(mess_name.class));

                        }
                        Facility_Adapter adapter = new Facility_Adapter(list);
                        listsize=list.size();
                        recyclerView.setAdapter(adapter);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mto.onOptionsItemSelected(item)){
            return true;
        }

        if (id.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(Notification.this, R.style.CustomAlertDialog);
            builder.setTitle("Add Facility");
            ViewGroup viewGroup = findViewById(android.R.id.content);
            final View dialogView = LayoutInflater.from(Notification.this).inflate(R.layout.mess_local, viewGroup, false);
            final EditText editText = dialogView.findViewById(R.id.no1);
            editText.setVisibility(View.VISIBLE);
            builder.setView(dialogView);
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (editText.getText().toString().isEmpty())
                        Toast.makeText(Notification.this, "Fill the empty Field", Toast.LENGTH_SHORT).show();
                    else {
                        mess_name1.setName(editText.getText().toString().trim());
                        String s = "noti_" + String.valueOf(listsize + 1);
                        databaseReference.child(s).setValue(mess_name1);
                    }
                }
            });

            final AlertDialog alertDialog = builder.create();

            alertDialog.show();
        }
        else
            Toast.makeText(Notification.this,"Permission Denied",Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id= menuItem.getItemId();


        if(id==R.id.nav_payment){
            Intent intent=new Intent(Notification.this,Payment.class);
            startActivity(intent);
        }
        if(id==R.id.nav_profile){
            Intent intent=new Intent(Notification.this,Profile.class);
            startActivity(intent);
        }
        if(id==R.id.nav_mess){
            Intent intent=new Intent(Notification.this,Mess_time_table.class);
            startActivity(intent);
        }
        if(id==R.id.nav_about){
            Intent intent=new Intent(Notification.this,About.class);
            startActivity(intent);
        }
        if(id==R.id.nav_attend){
            Intent intent=new Intent(Notification.this,Attendance.class);
            startActivity(intent);
        }
        if(id==R.id.nav_faci){
            Intent intent=new Intent(Notification.this,Facility.class);
            startActivity(intent);
        }
        if(id==R.id.nav_pic){
            Intent intent=new Intent(Notification.this,Hostelpic.class);
            startActivity(intent);
        }
        if(id==R.id.nav_noti){
            Intent intent=new Intent(Notification.this,Notification.class);
            startActivity(intent);
        }
        if(id==R.id.nav_student){
            Intent intent=new Intent(Notification.this,Student_list.class);
            startActivity(intent);
        }




        return false;
    }
}
