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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText name1;
    private EditText email1;
    private String gender1;
    private EditText religion1;
    private EditText loc1;
    private EditText pass1;
    private EditText cpass1;
    private EditText mobi1;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference1;
    private SignUp_details details;
    private FirebaseAuth mauth;
    private Uri selectedImageUri;
    private StorageReference image;
    private StorageReference storageReference;
    private CircleImageView ownerImage;
    private Date_Time date_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button button = findViewById(R.id.bu);
        name1 = findViewById(R.id.name);
        mobi1 = findViewById(R.id.mobi);
        loc1 = findViewById(R.id.loc);
        pass1 = findViewById(R.id.pass);
        cpass1 = findViewById(R.id.cpass);
        email1 = findViewById(R.id.email);
        religion1 = findViewById(R.id.reli);
        details = new SignUp_details();
        date_time=new Date_Time();
        mauth = FirebaseAuth.getInstance();
        Spinner spinner = findViewById(R.id.gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        ownerImage = findViewById(R.id.ownerImage);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference1 = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference().child("images");

        ownerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pass1.getText().toString().isEmpty() || loc1.getText().toString().isEmpty() || religion1.getText().toString().isEmpty() ||
                        email1.getText().toString().isEmpty() || name1.getText().toString().isEmpty()
                        /*|| gender1.getText().toString().isEmpty()*/ || mobi1.getText().toString().isEmpty()) {
                    Toast.makeText(SignUp.this, "Please Fill all the details", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass1.getText().toString().contentEquals(cpass1.getText().toString())) {

                        mauth.createUserWithEmailAndPassword(email1.getText().toString(), pass1.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            details.setName(name1.getText().toString().trim());
                                            details.setLoc(loc1.getText().toString().trim());
                                            details.setReligion(religion1.getText().toString().trim());
                                            details.setEmail(email1.getText().toString());
                                            details.setGender(gender1);
                                            details.setMob(mobi1.getText().toString().trim());
                                            details.setPass(pass1.getText().toString().trim());
                                            //details.setID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                            String s="Scan QR";
                                            String s1="to start";
                                            String s2="Attendace";
                                            date_time.setTime(s);
                                            date_time.setDate(s1);
                                            date_time.setCheck(s2);
                                            databaseReference.child("IDs").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                            databaseReference.child("Owner's").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Owner_details").setValue(details);
                                            databaseReference1.child("Owner's").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Owner_details").child("Last_Check_Status")
                                                    .setValue(date_time);

                                            up();

                                            Toast.makeText(SignUp.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(SignUp.this, AddHostel.class);
                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(SignUp.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(SignUp.this, "Confirm Password is not matching", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        gender1 = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void SelectImage() {

//       Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setType("image/*");
//        startActivityForResult(intent.createChooser(intent, "Select File"), 1);

        Intent intent=new Intent(Intent.ACTION_PICK);
        //intent.setType("image/*");
        intent.setDataAndType(intent.getData(),"image/*");
        // startActivity(intent);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == 1) {

                selectedImageUri = data.getData();
                ownerImage.setImageURI(selectedImageUri);


            }
        }

    }


    private void up() {

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

                            DatabaseReference dref = databaseReference.child("Owner's").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("Owner_details").child("Owner_Image");
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("name", String.valueOf(uri));

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
