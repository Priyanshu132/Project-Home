package mind.blower.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class feedback_activity extends AppCompatActivity {

    private TextView textView;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_activity);

        textView = findViewById(R.id.day);
        recyclerView = findViewById(R.id.recycle_list);
        day  = getIntent().getStringExtra("day");

        textView.setText("Feedback of "+day);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Owner's")
               .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("MessTimeTable").child(day).child("Feedback");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<feedback_detail> list = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    list.add(ds.getValue(feedback_detail.class));

                }

                Feedback_adapter feedback_adapter = new Feedback_adapter(list);
                recyclerView.setAdapter(feedback_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
