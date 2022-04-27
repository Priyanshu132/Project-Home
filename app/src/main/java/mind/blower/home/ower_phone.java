package mind.blower.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ower_phone extends AppCompatActivity {

    private Button button;
    private EditText name1;
    private EditText email1;
    private EditText gender1;
    private EditText religion1;
    private EditText loc1;
    private EditText pass1;
    private EditText cpass1;
    private EditText mobi1;
    private FirebaseAuth auth;
    private String uid;
    private DatabaseReference databaseReference;
    private SignUp_details details;
    private editPayment editPayment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ower_phone);

        button=(Button)findViewById(R.id.bu);
        name1=(EditText)findViewById(R.id.name);
        gender1=(EditText)findViewById(R.id.gender);
        mobi1=(EditText)findViewById(R.id.mobi);
        loc1=(EditText)findViewById(R.id.loc);
        auth=FirebaseAuth.getInstance();
        pass1=(EditText)findViewById(R.id.pass);
        cpass1=(EditText)findViewById(R.id.cpass);
        email1=(EditText)findViewById(R.id.email);
        religion1=(EditText)findViewById(R.id.reli);
        uid=getIntent().getStringExtra("UID");
        details=new SignUp_details();
        editPayment=new editPayment();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Owner's");



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pass1.getText().toString().isEmpty()|| loc1.getText().toString().isEmpty() || religion1.getText().toString().isEmpty()|| email1.getText().toString().isEmpty()|| name1.getText().toString().isEmpty()|| gender1.getText().toString().isEmpty()|| mobi1.getText().toString().isEmpty()) {
                    Toast.makeText(ower_phone.this, "Please Fill all the details", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass1.getText().toString().contentEquals(cpass1.getText().toString())) {

                                auth.createUserWithEmailAndPassword(email1.getText().toString(), pass1.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            String s = "0000";
                                            details.setName(name1.getText().toString().trim());
                                            details.setLoc(loc1.getText().toString().trim());
                                            details.setReligion(religion1.getText().toString().trim());
                                            details.setEmail(email1.getText().toString());
                                            details.setGender(gender1.getText().toString().trim());
                                            details.setMob(mobi1.getText().toString().trim());
                                          //  details.setID(FirebaseAuth.getInstance().getCurrentUser().getUid());

//                                            databaseReference.child("Students").child("Paid_payments").child("paid_Hostel_fee").setValue(s);
//                                            databaseReference.child("Students").child("Paid_payments").child("paid_Security_fee").setValue(s);
//                                            databaseReference.child("Students").child("Total_payments").child("total_Hostel_fee").setValue(s);
//                                            databaseReference.child("Students").child("Total_payments").child("total_Security_fee").setValue(s);
                                            databaseReference.child(uid).child("Warden_details").setValue(details);

                                            Toast.makeText(ower_phone.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ower_phone.this, Login.class);
                                            startActivity(intent);


                                        } else {
                                            Toast.makeText(ower_phone.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(ower_phone.this, "Confirm Password did't match", Toast.LENGTH_LONG).show();
                            }

                }
            }
        });



    }
}
