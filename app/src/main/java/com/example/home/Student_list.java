package com.example.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Student_list extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mto;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private FirebaseAuth auth1;
    private String uid;
    private String owner;
    private Profile_details profile_details;
    private Student_deatils student_deatils;
    private editPayment editPayment;
    private Total_Payment total_payment;
    private Date_Time date_time;
    private Uri selectedImageUri;
    private StorageReference image;
    private StorageReference storageReference;
    private CircleImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        auth = FirebaseAuth.getInstance();
        auth1 = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference().child("images");

        uid = auth.getCurrentUser().getUid();
        owner=getIntent().getStringExtra("main");
        profile_details = new Profile_details();
        student_deatils = new Student_deatils();
        editPayment = new editPayment();
        total_payment = new Total_Payment();
        date_time=new Date_Time();


        section_for_list s = new section_for_list(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(s);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mto = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mto);
        mto.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navi);
        navigationView.setNavigationItemSelectedListener(this);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addstudents, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mto.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        if (owner.contentEquals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {


            if (id == R.id.addStudent) {

                Intent intent = new Intent(Student_list.this, AddStudent.class);
                 intent.putExtra("main", owner);
                 startActivity(intent);



//                final AlertDialog.Builder builder = new AlertDialog.Builder(Student_list.this, R.style.CustomAlertDialog);
//                builder.setTitle("Add Student");
//                ViewGroup viewGroup = findViewById(android.R.id.content);
//                final View dialogView = LayoutInflater.from(Student_list.this).inflate(R.layout.activity_add_student, viewGroup, false);
//                final EditText editText = dialogView.findViewById(R.id.SN);
//                final EditText editText1 = dialogView.findViewById(R.id.email);
//                final EditText editText2 = dialogView.findViewById(R.id.C);
//                final EditText editText3 = dialogView.findViewById(R.id.Mn);
//                final EditText editText4 = dialogView.findViewById(R.id.B);
//                final EditText editText5 = dialogView.findViewById(R.id.RN);
//                  imageView = dialogView.findViewById(R.id.logo_pic);
//                final EditText religion = dialogView.findViewById(R.id.reli);
//                final EditText address = dialogView.findViewById(R.id.address);
//                final EditText pass = dialogView.findViewById(R.id.pas);
//                final EditText year = dialogView.findViewById(R.id.year);
//                final EditText gen=dialogView.findViewById(R.id.gender);
//
//                imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        SelectImage();
//                    }
//                });
//
//
//                builder.setView(dialogView);
//
//                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(final DialogInterface dialogInterface, final int i) {
//
////                        final ProgressDialog progressDialog = new ProgressDialog(Student_list.this);
////                        progressDialog.setTitle("Loading...");
////                        progressDialog.show();
//                        if (editText.getText().toString().isEmpty() || editText1.getText().toString().isEmpty() || editText2.getText().toString().isEmpty()
//                                || editText3.getText().toString().isEmpty() || editText4.getText().toString().isEmpty() || editText5.getText().toString().isEmpty()
//                                || pass.getText().toString().isEmpty() || address.getText().toString().isEmpty() || religion.getText().toString().isEmpty() ||
//                                year.getText().toString().isEmpty() || gen.getText().toString().isEmpty())
//                            Toast.makeText(Student_list.this, "Fill All the Details", Toast.LENGTH_SHORT).show();
//                        else {
//                            auth1.createUserWithEmailAndPassword(editText1.getText().toString().trim(), pass.getText().toString().trim())
//                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if (task.isSuccessful()) {
//                                        up(auth1.getUid());
//                                        profile_details.setBranch(editText4.getText().toString().trim());
//                                        profile_details.setCourse(editText2.getText().toString().trim());
//                                        profile_details.setEmail(editText1.getText().toString().trim());
//                                        profile_details.setReligion(religion.getText().toString().trim());
//                                        profile_details.setMob(Integer.parseInt(editText3.getText().toString().trim()));
//                                        profile_details.setName(editText.getText().toString().trim());
//                                        profile_details.setRoom(Integer.parseInt(editText5.getText().toString().trim()));
//                                        profile_details.setAddress(address.getText().toString().trim());
//                                        profile_details.setYear(Integer.parseInt(year.getText().toString().trim()));
//                                        profile_details.setKey(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                                        profile_details.setGender(gen.getText().toString().trim());
//                                        // for year wise
//                                        student_deatils.setName(editText.getText().toString().trim());
//                                        student_deatils.setCourse(editText2.getText().toString().trim());
//                                        student_deatils.setBranch(editText4.getText().toString().trim());
//                                        student_deatils.setMob(Integer.parseInt(editText3.getText().toString().trim()));
//                                        student_deatils.setRoom(Integer.parseInt(editText5.getText().toString().trim()));
//                                        // for payments
//
//                                        total_payment.setTotal_Security_fee("0000");
//                                        total_payment.setTotal_Hostel_fee("0000");
//
//                                        editPayment.setPaid_Security_fee("0000");
//                                        editPayment.setPaid_Hostel_fee("0000");
//
//                                        String s = "Scan QR";
//                                        String s1 = "to start";
//                                        String s2 = "Attendace";
//                                        date_time.setTime(s2);
//                                        date_time.setDate(s1);
//                                        date_time.setCheck(s);
//
//
//                                        if (Integer.parseInt(year.getText().toString().trim()) == 1)
//                                            databaseReference.child("Owner's").child(uid).child("Student_details").child("First_year").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(student_deatils);
//                                        if (Integer.parseInt(year.getText().toString().trim()) == 2)
//                                            databaseReference.child("Owner's").child(uid).child("Student_details").child("Second_year").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(student_deatils);
//                                        if (Integer.parseInt(year.getText().toString().trim()) == 3)
//                                            databaseReference.child("Owner's").child(uid).child("Student_details").child("Third_year").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(student_deatils);
//                                        if (Integer.parseInt(year.getText().toString().trim()) == 4)
//                                            databaseReference.child("Owner's").child(uid).child("Student_details").child("Final_year").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(student_deatils);
//
//
//                                        databaseReference.child("IDs").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(uid);
//                                        databaseReference.child("Owner's").child(uid).child("Student_details").child("All_Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(profile_details);
//                                        databaseReference.child("Owner's").child(uid).child("Student_details").child("All_Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Paid_payments").setValue(editPayment);
//                                        databaseReference.child("Owner's").child(uid).child("Student_details").child("All_Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Total_payments").setValue(total_payment);
//
//                                        DatabaseReference drefg = databaseReference.child("Owner's").child(uid).child("Student_details")
//                                                .child("All_Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Last_Check_Status");
//                                        drefg.setValue(date_time);
////                                        auth1.signOut();
////                                         auth1.signInWithEmailAndPassword("priyanshugupta132@gmail.com","123456");
//                                        Toast.makeText(getApplicationContext(), "Student Added Sucessfully", Toast.LENGTH_SHORT).show();
//                                        //   progressDialog.dismiss();
//
//                                        Intent intent = new Intent(Student_list.this, MainActivity.class);
//                                        intent.putExtra("main", owner);
//                                        startActivity(intent);
//
//
//                                    }
//
//
//                                }
//                            });
//                        }
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        // do nothing
//                    }
//                });
//
//                final AlertDialog alertDialog = builder.create();
//
//                alertDialog.show();
//
            }
        }
        else {
            if (id == R.id.addStudent)
                Toast.makeText(Student_list.this,"Permission Denied",Toast.LENGTH_SHORT).show();
        }

            return super.onOptionsItemSelected(item);
        }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id= menuItem.getItemId();


        if(id==R.id.nav_payment){
            Intent intent=new Intent(Student_list.this,Payment.class);

            startActivity(intent);
        }
        if(id==R.id.nav_profile){
            Intent intent=new Intent(Student_list.this,Profile.class);

            startActivity(intent);
        }
        if(id==R.id.nav_mess){
            Intent intent=new Intent(Student_list.this,Mess_time_table.class);

            startActivity(intent);
        }
        if(id==R.id.nav_about){
            Intent intent=new Intent(Student_list.this,About.class);
            startActivity(intent);
        }
        if(id==R.id.nav_attend){
            Intent intent=new Intent(Student_list.this,Attendance.class);
            startActivity(intent);
        }
        if(id==R.id.nav_faci){
            Intent intent=new Intent(Student_list.this,Facility.class);
            startActivity(intent);
        }
        if(id==R.id.nav_pic){
            Intent intent=new Intent(Student_list.this,Hostelpic.class);
            startActivity(intent);
        }
        if(id==R.id.nav_noti){
            Intent intent=new Intent(Student_list.this,Notification.class);
            startActivity(intent);
        }
        if(id==R.id.nav_student){
            Intent intent=new Intent(Student_list.this,Student_list.class);
            startActivity(intent);
        }




        return false;
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
                imageView.setImageURI(selectedImageUri);



            }
        }

    }

    public void up(final String id) {
        Toast.makeText(Student_list.this,"up called",Toast.LENGTH_SHORT).show();

        if (selectedImageUri != null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            image = storageReference.child("image/" + UUID.randomUUID().toString());
            image.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            DatabaseReference dref = databaseReference.child("Owner's").child(owner).child("Student_details")
                                    .child("All_Students").child(id).child("image");
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("imageUrl", String.valueOf(uri));

                            dref.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
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
                    double i = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded" + (int) i + "%");
                }
            });

        }
    }
}
