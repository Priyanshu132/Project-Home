package com.example.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class Saturday extends Fragment {

    private Button feedBack;
    private mess_name Mess_name;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference1;
    private LinearLayout morning_lay;
    private LinearLayout noon_lay;
    private LinearLayout night_lay;
    private  LinearLayout evening_lay;
    private ArrayList<mess_name> list;
    private ArrayList<mess_name> list1;
    private ArrayList<mess_name> list2;
    private ArrayList<mess_name> list3;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private int size_noon;
    private int size_night;
    private int size_morning;
    private int size_evening;
    private feedback_detail feedback_detail;
    private String id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_saturday,container,false);

        feedBack=(Button)view.findViewById(R.id.feedback);
        feedback_detail = new feedback_detail();


        recyclerView=view.findViewById(R.id.morning_recycle);
        recyclerView1=view.findViewById(R.id.evening_recycle);
        recyclerView2=view.findViewById(R.id.night_recycle);
        recyclerView3=view.findViewById(R.id.noon_recycle);
        morning_lay=view.findViewById(R.id.morning_layout);
        evening_lay=view.findViewById(R.id.evening_layout);
        night_lay=view.findViewById(R.id.night_layout);
        noon_lay=view.findViewById(R.id.noon_layout);
        Mess_name=new mess_name();




        return view;
    }

    ItemTouchHelper.SimpleCallback simpleCallback =new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int pos = viewHolder.getAdapterPosition();
            switch (direction) {

                case ItemTouchHelper.RIGHT:
                    final String delete_word = list.get(pos).getName();
                    databaseReference= FirebaseDatabase.getInstance().getReference().child("Owner's")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("MessTimeTable").child("Saturday").child("Morning");

                    Query query = databaseReference.orderByChild("name").equalTo(delete_word);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds: dataSnapshot.getChildren()){
                                ds.getRef().removeValue();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    break;


            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightActionIcon(R.drawable.ic_delete_black_24dp)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(),R.color.white))
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    ItemTouchHelper.SimpleCallback simpleCallback1 =new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int pos = viewHolder.getAdapterPosition();
            switch (direction) {

                case ItemTouchHelper.RIGHT:
                    final String delete_word = list1.get(pos).getName();
                    databaseReference1= FirebaseDatabase.getInstance().getReference().child("Owner's")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("MessTimeTable").child("Saturday").child("Evening");

                    Query query = databaseReference1.orderByChild("name").equalTo(delete_word);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds: dataSnapshot.getChildren()){
                                ds.getRef().removeValue();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });


                    break;


            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightActionIcon(R.drawable.ic_delete_black_24dp)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(),R.color.white))
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    ItemTouchHelper.SimpleCallback simpleCallback2 =new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int pos = viewHolder.getAdapterPosition();
            switch (direction) {

                case ItemTouchHelper.RIGHT:
                    final String delete_word = list2.get(pos).getName();
                    databaseReference1= FirebaseDatabase.getInstance().getReference().child("Owner's")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("MessTimeTable").child("Saturday").child("Night");

                    Query query = databaseReference1.orderByChild("name").equalTo(delete_word);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds: dataSnapshot.getChildren()){
                                ds.getRef().removeValue();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });


                    break;


            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightActionIcon(R.drawable.ic_delete_black_24dp)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(),R.color.white))
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    ItemTouchHelper.SimpleCallback simpleCallback3 =new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int pos = viewHolder.getAdapterPosition();
            switch (direction) {

                case ItemTouchHelper.RIGHT:
                    final String delete_word = list3.get(pos).getName();
                    databaseReference1= FirebaseDatabase.getInstance().getReference().child("Owner's")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("MessTimeTable").child("Saturday").child("Noon");

                    Query query = databaseReference1.orderByChild("name").equalTo(delete_word);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds: dataSnapshot.getChildren()){
                                ds.getRef().removeValue();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });


                    break;


            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightActionIcon(R.drawable.ic_delete_black_24dp)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(),R.color.white))
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
    @Override
    public void onStart() {
        super.onStart();

        FirebaseDatabase.getInstance().getReference().child("IDs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                id = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue().toString();

                databaseReference= FirebaseDatabase.getInstance().getReference().child("Owner's")
                        .child(id).child("MessTimeTable").child("Saturday");
                if(id.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {


                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                    ItemTouchHelper itemTouchHelper1 = new ItemTouchHelper(simpleCallback1);
                    ItemTouchHelper itemTouchHelper2 = new ItemTouchHelper(simpleCallback2);
                    ItemTouchHelper itemTouchHelper3 = new ItemTouchHelper(simpleCallback3);

                    itemTouchHelper.attachToRecyclerView(recyclerView);
                    itemTouchHelper1.attachToRecyclerView(recyclerView1);
                    itemTouchHelper2.attachToRecyclerView(recyclerView2);
                    itemTouchHelper3.attachToRecyclerView(recyclerView3);

                    noon_lay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomAlertDialog);
                                    ViewGroup viewGroup = view.findViewById(android.R.id.content);
                                    View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.mess_local, viewGroup, false);
                                    final EditText editText=dialogView.findViewById(R.id.no1);

                                    builder.setView(dialogView);
                                    builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (editText.getText().toString().isEmpty())
                                                Toast.makeText(getContext(), "Fill the empty Field", Toast.LENGTH_SHORT).show();
                                            else {
                                                Mess_name.setName(editText.getText().toString().trim());
                                                String s= "noon_"+String.valueOf(size_noon+1);
                                                databaseReference.child("Noon").child(s).setValue(Mess_name);
                                            }
                                        }
                                    });


                                    final AlertDialog alertDialog = builder.create();

                                    alertDialog.show();
                                }
                            });


                    night_lay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomAlertDialog);
                                    ViewGroup viewGroup = view.findViewById(android.R.id.content);
                                    View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.mess_local, viewGroup, false);
                                    final EditText editText=dialogView.findViewById(R.id.no1);
                                    TextView textView1=dialogView.findViewById(R.id.text_temp1);
                                    textView1.setVisibility(View.VISIBLE);
                                    editText.setVisibility(View.VISIBLE);
                                    builder.setView(dialogView);
                                    builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (editText.getText().toString().isEmpty())
                                                Toast.makeText(getContext(), "Fill the empty Field", Toast.LENGTH_SHORT).show();
                                            else {
                                                Mess_name.setName(editText.getText().toString().trim());
                                                String s= "night_"+String.valueOf(size_night+1);
                                                databaseReference.child("Night").child(s).setValue(Mess_name);
                                            }
                                        }
                                    });

                                    final AlertDialog alertDialog = builder.create();

                                    alertDialog.show();
                                }
                            });


                    evening_lay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomAlertDialog);
                                    ViewGroup viewGroup = view.findViewById(android.R.id.content);
                                    View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.mess_local, viewGroup, false);
                                    final EditText editText=dialogView.findViewById(R.id.no1);
//
                                    builder.setView(dialogView);
                                    builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (editText.getText().toString().isEmpty())
                                                Toast.makeText(getContext(), "Fill the empty Field", Toast.LENGTH_SHORT).show();
                                            else {
                                                Mess_name.setName(editText.getText().toString().trim());
                                                String s= "evening_"+String.valueOf(size_evening+1);
                                                databaseReference.child("Evening").child(s).setValue(Mess_name);
                                            }
                                        }
                                    });
//
                                    final AlertDialog alertDialog = builder.create();

                                    alertDialog.show();
                                }
                            });
//
//                        }
//                    });

                    morning_lay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomAlertDialog);
                                    ViewGroup viewGroup = view.findViewById(android.R.id.content);
                                    View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.mess_local, viewGroup, false);
                                    final EditText editText=dialogView.findViewById(R.id.no1);
//
                                    builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (editText.getText().toString().isEmpty())
                                                Toast.makeText(getContext(), "Fill the empty Field", Toast.LENGTH_SHORT).show();
                                            else {
                                                Mess_name.setName(editText.getText().toString().trim());
                                                String s= "morning_"+String.valueOf(size_morning+1);
                                                databaseReference.child("Morning").child(s).setValue(Mess_name);
                                            }
                                        }
                                    });

                                    final AlertDialog alertDialog = builder.create();

                                    alertDialog.show();
                                }
                            });




                    feedBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getContext(),feedback_activity.class);
                            i.putExtra("day","Saturday");
                            startActivity(i);
                        }
                    });

                }
                else {
                    feedBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Feedback For Saturday");
                            View view11 = getLayoutInflater().inflate(R.layout.feedback, null);
                            final EditText feed = (EditText) view11.findViewById(R.id.Mfeed);
                            final TextView n = view11.findViewById(R.id.name);
                            final TextView m = view11.findViewById(R.id.Mn);
                            DatabaseReference d= FirebaseDatabase.getInstance().getReference().child("Owner's")
                                    .child(id).child("Student_details").child("All_Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                            final String[] p_mob = new String[2];
                            d.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    p_mob[0] = dataSnapshot.child("mob").getValue().toString();
                                    p_mob[1] = dataSnapshot.child("name").getValue().toString();
                                    n.setText(p_mob[1]);
                                    m.setText(p_mob[0]);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                            builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {



                                    feedback_detail.setPerson_name(p_mob[1]);
                                    feedback_detail.setMobile(p_mob[0]);
                                    feedback_detail.setSub(feed.getText().toString());
                                    databaseReference.child("Feedback").child(p_mob[1]).setValue(feedback_detail);
                                    Toast.makeText(getActivity(), "Feedback Sended Successfully", Toast.LENGTH_SHORT).show();
                                }
                            });

                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.setView(view11);
                            builder.show();
                        }
                    });
                }

        if (databaseReference.child("Morning")!= null) {
            databaseReference.child("Morning").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        list = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                            list.add(ds.getValue(mess_name.class));

                        }
                        mess_adapter adapter = new mess_adapter(list);

                        recyclerView.setAdapter(adapter);
                        size_morning=list.size();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if (databaseReference.child("Night")!= null) {
            databaseReference.child("Night").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        list2 = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                            list2.add(ds.getValue(mess_name.class));

                        }
                        mess_adapter adapter = new mess_adapter(list2);

                        recyclerView2.setAdapter(adapter);
                        size_night=list2.size();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if (databaseReference.child("Evening")!= null) {
            databaseReference.child("Evening").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        list1 = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                            list1.add(ds.getValue(mess_name.class));

                        }
                        mess_adapter adapter = new mess_adapter(list1);

                        recyclerView1.setAdapter(adapter);
                        size_evening=list1.size();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if (databaseReference.child("Noon")!= null) {
            databaseReference.child("Noon").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        list3 = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                            list3.add(ds.getValue(mess_name.class));

                        }
                        mess_adapter adapter = new mess_adapter(list3);

                        recyclerView3.setAdapter(adapter);
                        size_noon=list3.size();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
