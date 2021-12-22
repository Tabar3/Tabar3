package com.example.tabar3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Edit_Camp_Info extends AppCompatActivity {
    EditText na, d1,d2;
    FirebaseFirestore fStore;
    DocumentReference dRef;
    StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseStorage storage;
    String s;
    Button button, btnImg;
    public Uri mImageUri;
    ImageView img2;int x;
    FirebaseAuth mAuth;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_camp_info);
        button = findViewById(R.id.edit3);
        na = findViewById(R.id.CampNameE);
        d1 = findViewById(R.id.CampDes1E);
        d2=findViewById(R.id.CampDes2E);
        img2 = findViewById(R.id.imgECamp);
        btnImg = findViewById(R.id.button_camp_image);
        fStore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Intent intent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        id =mAuth.getCurrentUser().getUid();

        s = intent.getStringExtra("EditCampInfo");
        x=0;
        dRef = fStore.collection("Charities").document(id).collection("Campaign").document(s);
        dRef.get().addOnSuccessListener((documentSnapshot) -> {
            if (documentSnapshot != null && documentSnapshot.exists()) {
                na.setText(documentSnapshot.getString("campName"));
                d1.setText(documentSnapshot.getString("campDes1"));
                d2.setText(documentSnapshot.getString("campDes2"));

                btnImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openFileChooser();
                        x=1;

                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddToDB();
                        Intent i2 = new Intent(Edit_Camp_Info.this, Camp_Info.class);
                       i2.putExtra("Camp", s);
                        i2.putExtra("Char", id);
                        startActivity(i2);
                    }
                });


            }
        });
        storageReference = FirebaseStorage.getInstance().getReference();
        if (s != null) {
            StorageReference bookReference = storageReference.child("Campaign/"+s+"/mainImage.jpg");

            bookReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(Edit_Camp_Info.this).load(uri).into(img2);
                }
            });
        }

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            img2.setImageURI(mImageUri);

        }
    }


    public void AddToDB() {
        fStore = FirebaseFirestore.getInstance();

        DocumentReference documentReference = fStore.collection("Charities").document(id).collection("Campaign").document(s);
        documentReference.update("campId", s,"campName", na.getText().toString()
        ,"campDes1", d1.getText().toString(),"campDes2", d2.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Edit_Camp_Info.this, "mabrooooooook", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Edit_Camp_Info.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                Toast.makeText(Edit_Camp_Info.this,s, Toast.LENGTH_LONG).show();
                Log.d("myTag", e.getMessage());

            }
        });
        if(x==1){
        uploadImg(mImageUri); }

    }


    private void uploadImg(Uri uri) {

        final ProgressDialog pd = new ProgressDialog((this));
        pd.setTitle("Uploading Image ...");
        pd.show();

        //final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("Campaign/"+s+"/mainImage.jpg");
        riversRef.putFile(mImageUri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Failed To Upload", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Image Uploded", Snackbar.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Percentage:" + (int) progressPercent + "%");
            }
        });
    }


}