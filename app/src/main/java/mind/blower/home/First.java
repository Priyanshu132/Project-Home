package mind.blower.home;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class First extends Fragment {

    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    ArrayList<Student_deatils> list;
    RecyclerView recyclerView;
    private TextView noresult;
    SearchView searchView;
    private String id;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first, container, false);




          // id=FirebaseAuth.getInstance().getCurrentUser().getUid();

        recyclerView = (RecyclerView) v.findViewById(R.id.recycle);
        searchView = (SearchView) v.findViewById(R.id.search);
        noresult = v.findViewById(R.id.noresult);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
         databaseReference1 = FirebaseDatabase.getInstance().getReference().child("IDs");
       databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                id=dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue().toString();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Owner's")
                        .child(id).child("Student_details").child("First_year");
                if (databaseReference != null) {
                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setTitle("Loading...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                list = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                    list.add(ds.getValue(Student_deatils.class));

                                }
                                Adapter adapter = new Adapter(list);

                                recyclerView.setAdapter(adapter);
                                progressDialog.dismiss();
                            } else
                                progressDialog.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            progressDialog.dismiss();
                        }
                    });
                }
                if (searchView != null) {
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        id=FirebaseAuth.getInstance().getCurrentUser().getUid();
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Owner's")
//                .child(id).child("Student_details").child("First_year");

//        if (databaseReference != null) {
//            final ProgressDialog progressDialog = new ProgressDialog(getContext());
//            progressDialog.setTitle("Loading...");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();
//
//            databaseReference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//
//                        list = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//
//                            list.add(ds.getValue(Student_deatils.class));
//
//                        }
//                        Adapter adapter = new Adapter(list);
//
//                        recyclerView.setAdapter(adapter);
//                        progressDialog.dismiss();
//                    } else
//                        progressDialog.dismiss();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    progressDialog.dismiss();
//                }
//            });
//        }

//            if (searchView != null) {
//                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                    @Override
//                    public boolean onQueryTextSubmit(String query) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onQueryTextChange(String newText) {
//                        search(newText);
//                        return true;
//                    }
//                });
//            }
        }



    private void search(String str) {

        ArrayList<Student_deatils> mylist = new ArrayList<>();

            for (Student_deatils obj : list) {

                if (obj.getName().toLowerCase().contains(str.toLowerCase())) {
                    mylist.add(obj);
                }
            }
            if (mylist.isEmpty()) {
                noresult.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
            } else {
                noresult.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

            }
            Adapter mya = new Adapter(mylist);
            recyclerView.setAdapter(mya);
        }


}
