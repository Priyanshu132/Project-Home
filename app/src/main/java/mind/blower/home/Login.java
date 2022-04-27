package mind.blower.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private EditText user;
    private EditText pass;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private TextView textView;
    private Button button1;
    private TextView sign;
    private  DatabaseReference databaseReference;
    private TextView nor;
    private FirebaseAuth mauth;
    private  String uid;



    Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            button1.setVisibility(View.VISIBLE);
            linearLayout1.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            sign.setVisibility(View.VISIBLE);
            nor.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        mauth = FirebaseAuth.getInstance();
        FirebaseUser currentuser = mauth.getCurrentUser();

        if(currentuser!=null){
            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Please wait...");
            progressDialog.setMessage("Signing");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            uid=currentuser.getUid();



                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String id = dataSnapshot.child(uid).getValue().toString();
                        progressDialog.dismiss();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("main", id);
                        startActivity(intent);
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Login.this,databaseError.getMessage(),Toast.LENGTH_SHORT);
                    }
                });


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button1=(Button)findViewById(R.id.bu);
        linearLayout1=(LinearLayout)findViewById(R.id.la2);
        linearLayout2=(LinearLayout)findViewById(R.id.la3);

        mauth=FirebaseAuth.getInstance();
        textView=(TextView)findViewById(R.id.fpass);
        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.pass);
        sign=(TextView)findViewById(R.id.signup);
        nor=(TextView)findViewById(R.id.normal);

databaseReference=FirebaseDatabase.getInstance().getReference().child("IDs");

        handler.postDelayed(runnable,1000);


        button1.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if (user.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
                                               Toast.makeText(Login.this, "Fill all the details", Toast.LENGTH_SHORT).show();
                                           } else {
                                               mauth.signInWithEmailAndPassword(user.getText().toString(), pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<AuthResult> task) {
                                                       if (task.isSuccessful()) {
                                                            uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                            databaseReference.addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    String id=dataSnapshot.child(uid).getValue().toString();
                                                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                                                    intent.putExtra("main",id);
                                                                    startActivity(intent);
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                    Toast.makeText(Login.this,databaseError.getMessage(),Toast.LENGTH_SHORT);
                                                                }
                                                            });

                                                       } else {
                                                           Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                       }
                                                   }
                                               });
                                           }
                                       }
                                   });


                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Login.this, Forgetpass.class);
                        startActivity(intent);
                    }
                });
                sign.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Login.this, SignUp.class);
                        startActivity(intent);
                    }
                });


            }
}
