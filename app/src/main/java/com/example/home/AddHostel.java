package com.example.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class AddHostel extends AppCompatActivity {

    private EditText hostelname;
    private EditText hosteladdress;
    private EditText landline;
    private ImageView logo;
    private Button button;
     private TextView tempo;
     private ImageView piclayout;
   //  private  Uri selectedImageUri;
     private ImageButton camera;
    private DatabaseReference databaseReference;
    private HostelDetails hostelDetails;
   //private StorageReference storageReference;
   // private StorageReference image;
   // public Uri imageUri;
   // private StorageTask storageTask;
   // Bitmap bitmap;
    private Owner_details ownerDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hostel);

        button=(Button)findViewById(R.id.bu);
        hosteladdress=(EditText)findViewById(R.id.loc);
        logo=(ImageView)findViewById(R.id.logo_pic);
        hostelname=(EditText)findViewById(R.id.name);
        landline=(EditText)findViewById(R.id.landline);
        camera=(ImageButton)findViewById(R.id.camera);
        tempo=(TextView)findViewById(R.id.temp);
        piclayout=(ImageView)findViewById(R.id.custom_pic);
        hostelDetails=new HostelDetails();
        ownerDetails=new Owner_details();
     // storageReference= FirebaseStorage.getInstance().getReference().child("images");

//      camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SelectImage();
//            }
//        });
//
//      logo.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//                 displayPopupImage();
//
//          }
//      });

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Owner's").child(FirebaseAuth.getInstance().getCurrentUser().getUid());



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hostelname.getText().toString().isEmpty()|| hosteladdress.getText().toString().isEmpty()|| landline.getText().toString().isEmpty()) {
                    Toast.makeText(AddHostel.this, "Fill All the details", Toast.LENGTH_SHORT).show();
                }else
                 {
                    hostelDetails.setHostel_name(hostelname.getText().toString().trim());
                    hostelDetails.setAddress_of_hostel(hosteladdress.getText().toString().trim());
                    hostelDetails.setLandline_of_hostel(landline.getText().toString().trim());

                    databaseReference.child("Hostel_Detail").setValue(hostelDetails);

                   // up();

                    Toast.makeText(AddHostel.this, "Hostel Added Successfully", Toast.LENGTH_SHORT).show();
                     FirebaseAuth.getInstance().signOut();
                    Intent intent=new Intent(AddHostel.this,Login.class);
                    startActivity(intent);

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(AddHostel.this,Login.class);
        startActivity(intent);
        finish();
    }

    private void SelectImage(){

//        final CharSequence[] items={"Camera","Gallery","Cancel"};
//
//        AlertDialog.Builder builder=new AlertDialog.Builder(AddHostel.this);
//        builder.setTitle("Add Image");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if(items[which].equals("Camera")){
//
//                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(intent,1);
//                    tempo.setVisibility(View.INVISIBLE);
//
//
//                }else if(items[which].equals("Gallery")){
//
//                    Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    intent.setType("image/*");
//                    startActivityForResult(intent.createChooser(intent,"Select File"),1);
//                    tempo.setVisibility(View.INVISIBLE);


//
//                }else if(items[which].equals("Cancel")){
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(resultCode== Activity.RESULT_OK){

//            if(requestCode==0){
//
//                Bundle bundle=data.getExtras();
//                 bitmap=(Bitmap)bundle.get("data");
//                logo.setImageBitmap(bitmap);
////              Uri i=data.getData();
////              logo.setImageURI(i);
//
//
//            }else
//        if(requestCode==1){
//
//                 selectedImageUri=data.getData();
//                logo.setImageURI(selectedImageUri);
//
//            }
//        }
//
//    }


//    public void displayPopupImage(){
//
//        Dialog dialog=new Dialog(AddHostel.this);
//        dialog.setContentView(R.layout.pic_layout);
//        dialog.show();
//
//       AlertDialog.Builder builder=new AlertDialog.Builder(AddHostel.this);
//
//       builder.setView(logo);
//       builder.create();
//       builder.show();
   // }

//    private String getExtension(Uri uri){
//
//        ContentResolver cr=getContentResolver();
//        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
//        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
//    }

// private  void up(){
//
//        if(selectedImageUri!=null){
//
//            final ProgressDialog progressDialog=new ProgressDialog(this);
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();
//
//            image=storageReference.child("image/"+UUID.randomUUID().toString());
//                image.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                        image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//
//                                DatabaseReference dref=databaseReference.child(uid).child("Hostel_Detail").child("Hostel_pic");
//                                HashMap<String,String> hashMap=new HashMap<>();
//                                hashMap.put("imageUrl",String.valueOf(uri));
//
//                                dref.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                    }
//                                });
//                                progressDialog.dismiss();
//                            }
//                        });
//                        // Toast.makeText(AddHostel.this,"done",Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                        double i=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//                        progressDialog.setMessage("Uploaded"+(int)i+"%");
//                    }
//                });
//
//        }
 }




