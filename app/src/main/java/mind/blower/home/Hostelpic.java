package mind.blower.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Hostelpic extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mto;
    private RecyclerView recyclerView;
    private ArrayList<mess_name> list;
    private DatabaseReference databaseReference;
    private Uri selectedImageUri;
    private StorageReference image;
    private StorageReference storageReference;
    private pic_Adapter pic_adapter;
    private int sizeOfList;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostelpic);
        recyclerView = findViewById(R.id.recycle_list1);
        id=getIntent().getStringExtra("main");



        databaseReference = FirebaseDatabase.getInstance().getReference().child("Owner's").child(id)
                .child("Hostel_pictures");
        storageReference= FirebaseStorage.getInstance().getReference().child("images");

    onStart();


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mto = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mto);
        mto.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navi);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot image : dataSnapshot.getChildren()) {
                    list.add(image.getValue(mess_name.class));
                }
                pic_adapter = new pic_Adapter(list, getApplicationContext());
                recyclerView.setAdapter(pic_adapter);
                sizeOfList=list.size();

progressDialog.dismiss();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Hostelpic.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });




    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();


        if (id == R.id.nav_payment) {
            Intent intent = new Intent(Hostelpic.this, Payment.class);
            startActivity(intent);
        }
        if (id == R.id.nav_profile) {
            Intent intent = new Intent(Hostelpic.this, Profile.class);
            startActivity(intent);
        }
        if (id == R.id.nav_mess) {
            Intent intent = new Intent(Hostelpic.this, Mess_time_table.class);
            startActivity(intent);
        }
        if (id == R.id.nav_about) {
            Intent intent = new Intent(Hostelpic.this, About.class);
            startActivity(intent);
        }
        if (id == R.id.nav_attend) {
            Intent intent = new Intent(Hostelpic.this, Attendance.class);
            startActivity(intent);
        }
        if (id == R.id.nav_faci) {
            Intent intent = new Intent(Hostelpic.this, Facility.class);
            startActivity(intent);
        }
        if (id == R.id.nav_pic) {
            Intent intent = new Intent(Hostelpic.this, Hostelpic.class);
            startActivity(intent);
        }
        if (id == R.id.nav_noti) {
            Intent intent = new Intent(Hostelpic.this, Notification.class);
            startActivity(intent);
        }
        if (id == R.id.nav_student) {
            Intent intent = new Intent(Hostelpic.this, Student_list.class);
            startActivity(intent);
        }


        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.upload_pic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(id.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            int idd = item.getItemId();
            if (idd == R.id.action_settings1) {

                SelectImage();
            }
        }
        else
            Toast.makeText(Hostelpic.this,"Permission Denied",Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

    private void SelectImage() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Select File"), 1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == 1) {

                selectedImageUri = data.getData();
               up();

            }
        }

    }
    private  void up(){

        if(selectedImageUri!=null){
           final String temp="pic_"+sizeOfList;
            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            image=storageReference.child("image/"+ UUID.randomUUID().toString());
            image.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            DatabaseReference dref=databaseReference.child(temp);
                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put("name",String.valueOf(uri));


                            dref.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Hostelpic.this,"done",Toast.LENGTH_SHORT).show();
                                }
                            });
                            progressDialog.dismiss();
                        }
                    });
                    // Toast.makeText(AddHostel.this,"done",Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double i=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded  "+(int)i+"%");
                }
            });

        }

    }

}

