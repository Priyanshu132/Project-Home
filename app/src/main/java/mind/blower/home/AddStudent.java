package mind.blower.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddStudent extends AppCompatActivity {
    private static final int SE=100;
    private Button button;
    private EditText editText;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private  EditText editText4;
    private  EditText editText5;
    private EditText year;
    private EditText address;
    private EditText pass;
    private EditText religion;
    private FirebaseAuth auth;
    private String uid;
    private DatabaseReference databaseReference;
    private Profile_details profile_details;
    private Student_deatils student_deatils;
    private editPayment editPayment;
    private Total_Payment total_payment;
    private String owner;
    private EditText gen;

    private Date_Time date_time;
    private ProgressDialog progressDialog;
    private Uri selectedImageUri;
    private StorageReference image;
    private StorageReference storageReference;
    private CircleImageView imageView;
    private String login_email;
    private String login_pass;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        button=findViewById(R.id.add_);
        editText=findViewById(R.id.SN);
        editText1=findViewById(R.id.email);
        editText2=findViewById(R.id.C);
        editText3=findViewById(R.id.Mn);
        editText4=findViewById(R.id.B);
        editText5=findViewById(R.id.RN);
        imageView=findViewById(R.id.logo_pic);
        religion=findViewById(R.id.reli);
        address=findViewById(R.id.address);
        pass=findViewById(R.id.pas);
        year=findViewById(R.id.year);
        profile_details=new Profile_details();
        student_deatils=new Student_deatils();
        editPayment=new editPayment();
        date_time=new Date_Time();
        total_payment=new Total_Payment();
        owner=getIntent().getStringExtra("main");
        gen=findViewById(R.id.gender);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference().child("images");

        auth=FirebaseAuth.getInstance();
        uid= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   databaseReference.child("ek").child("lkl").setValue("wdw");
                SelectImage();
            }
        });

        databaseReference.child("Owner's").child(owner).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                login_email=dataSnapshot.child("Owner_details").child("email").getValue().toString();
                login_pass=dataSnapshot.child("Owner_details").child("pass").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

              //  ProgressBar progressBar=new ProgressBar(getApplicationContext());



                if(editText.getText().toString().isEmpty()|| editText1.getText().toString().isEmpty()|| editText2.getText().toString().isEmpty()|| editText3.getText().toString().isEmpty()||
                editText4.getText().toString().isEmpty()|| editText5.getText().toString().isEmpty()|| pass.getText().toString().isEmpty()|| religion.getText().toString().isEmpty() ||
                year.getText().toString().isEmpty()|| address.getText().toString().isEmpty()||gen.getText().toString().isEmpty()){
                    Toast.makeText(AddStudent.this, "Fill All the details", Toast.LENGTH_SHORT).show();
                }
                else {
                    auth.createUserWithEmailAndPassword(editText1.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                profile_details.setBranch(editText4.getText().toString().trim());
                                profile_details.setCourse(editText2.getText().toString().trim());
                                profile_details.setEmail(editText1.getText().toString().trim());
                                profile_details.setReligion(religion.getText().toString().trim());
                                profile_details.setMob(Integer.parseInt(editText3.getText().toString().trim()));
                                profile_details.setName(editText.getText().toString().trim());
                                profile_details.setRoom(Integer.parseInt(editText5.getText().toString().trim()));
                                profile_details.setAddress(address.getText().toString().trim());
                                profile_details.setYear(Integer.parseInt(year.getText().toString().trim()));
                                profile_details.setKey(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                profile_details.setGender(gen.getText().toString().trim());
                                // for year wise
                                student_deatils.setName(editText.getText().toString().trim());
                                student_deatils.setCourse(editText2.getText().toString().trim());
                                student_deatils.setBranch(editText4.getText().toString().trim());
                                student_deatils.setMob(Integer.parseInt(editText3.getText().toString().trim()));
                                student_deatils.setRoom(Integer.parseInt(editText5.getText().toString().trim()));
                                // for payments
                                total_payment.setTotal_Security_fee("0000");
                                total_payment.setTotal_Hostel_fee("0000");

                                editPayment.setPaid_Security_fee("0000");
                                editPayment.setPaid_Hostel_fee("0000");

                                String s = "Scan QR";
                                String s1 = "to start";
                                String s2 = "Attendace";
                                date_time.setTime(s2);
                                date_time.setDate(s1);
                                date_time.setCheck(s);
                                final String temp=FirebaseAuth.getInstance().getCurrentUser().getUid();

                                if (selectedImageUri != null) {

//                                    final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
//                                    progressDialog.setTitle("Uploading...");
//                                    progressDialog.show();

                                    image = storageReference.child("image/" + UUID.randomUUID().toString());
                                    image.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {

                                                    DatabaseReference dref = databaseReference.child("Owner's").child(owner).child("Student_details")
                                                            .child("All_Students").child(temp);//.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                            HashMap<String, String> hashMap = new HashMap<>();
//                            hashMap.put("imageUrl", String.valueOf(uri));
                                                    profile_details.setImageUrl(String.valueOf(uri));
                                                    dref.setValue(profile_details);

                                                    // public void up() {


//                                    else {
//                                        Toast.makeText(AddStudent.this,"null",Toast.LENGTH_SHORT).show();
//                                    }
                                                    DatabaseReference drefg = databaseReference.child("Owner's").child(uid).child("Student_details")
                                                            .child("All_Students").child(temp).child("Last_Check_Status");
                                                    drefg.setValue(date_time);

                                                    // databaseReference.child("Owner's").child(uid).child("Student_details").child("All_Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(profile_details);
                                                    databaseReference.child("Owner's").child(uid).child("Student_details").child("All_Students").child(temp).child("Paid_payments").setValue(editPayment);
                                                    databaseReference.child("Owner's").child(uid).child("Student_details").child("All_Students").child(temp).child("Total_payments").setValue(total_payment);
                                                    if (Integer.parseInt(year.getText().toString().trim()) == 1)
                                                        databaseReference.child("Owner's").child(uid).child("Student_details").child("First_year").child(temp).setValue(student_deatils);
                                                    if (Integer.parseInt(year.getText().toString().trim()) == 2)
                                                        databaseReference.child("Owner's").child(uid).child("Student_details").child("Second_year").child(temp).setValue(student_deatils);
                                                    if (Integer.parseInt(year.getText().toString().trim()) == 3)
                                                        databaseReference.child("Owner's").child(uid).child("Student_details").child("Third_year").child(temp).setValue(student_deatils);
                                                    if (Integer.parseInt(year.getText().toString().trim()) == 4)
                                                        databaseReference.child("Owner's").child(uid).child("Student_details").child("Final_year").child(temp).setValue(student_deatils);


                                                    databaseReference.child("IDs").child(temp).setValue(uid);
                                                   // FirebaseAuth.getInstance().signOut();



                                                   // databaseReference.child("ki").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                    //Log.d("After sign out",FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                    //.addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    //  @Override
//                                                        public void onSuccess(Void aVoid) {
//                                                        }
//                                                    });
//                                                    //  progressDialog.dismiss();
//                                                }
//                                            });
//                                            // Toast.makeText(AddHostel.this,"done",Toast.LENGTH_SHORT).show();
                                                }
//                                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                                        @Override
//                                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                                            double i = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                                            progressDialog.setMessage("Uploaded" + (int) i + "%");
//                                        }
//                                    });

                                            });
                                        }


                                        //  Toast.makeText(AddStudent.this, "Student Added Sucessfully", Toast.LENGTH_SHORT).show();
//                                Intent intent=new Intent(AddStudent.this,Student_list.class);
//                                intent.putExtra("main",owner);
//                                startActivity(intent);


                                    });


                                }

                                    FirebaseAuth.getInstance().signOut();
                                FirebaseAuth.getInstance().signInWithEmailAndPassword(login_email,login_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Intent intent=new Intent(AddStudent.this,MainActivity.class);
                                        intent.putExtra("main",owner);
                                        Toast.makeText(AddStudent.this,"Added Successfully",Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                    }
                                });

                            }
                        }

    });
                }
            }
        });

       // FirebaseAuth.getInstance().signOut();

        }

    private void SelectImage() {

   //     Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
     Intent intent=new Intent(Intent.ACTION_PICK);
        //intent.setType("image/*");
        intent.setDataAndType(intent.getData(),"image/*");
       // startActivity(intent);
        startActivityForResult(intent,1);
        //startActivityForResult(intent,SE);
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

       // if (resultCode == Activity.RESULT_OK) {

           // if (requestCode == SE) {

//                selectedImageUri = data.getData();
//                imageView.setImageURI(selectedImageUri);

//                    selectedImageUri=data.getData();
//                InputStream inputStream=null;
//                try{
//                    assert selectedImageUri !=null;
//                    inputStream= getContentResolver().openInputStream(selectedImageUri);
//
//
//                }catch (FileNotFoundException e){
//                    e.printStackTrace();
//                }
//
//                BitmapFactory.decodeStream(inputStream);
//                imageView.setImageURI(selectedImageUri);
//
//
//            }
       // }

    }



}
