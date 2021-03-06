package com.example.tabar3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Edit_Charity_Info extends AppCompatActivity {
    EditText n,d,l,p,e;
    FirebaseFirestore fStore;
    DocumentReference dRef;
    StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseStorage storage;
    String s;
    Button button,btnImg;
    public Uri mImageUri;
    ImageView img;
    int x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_charity_info);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#0060FF"));
        actionBar.setBackgroundDrawable(colorDrawable);
        button=findViewById(R.id.edit1);
        n=findViewById(R.id.charName);
        d=findViewById(R.id.charDes);
        l=findViewById(R.id.charLoc);
        p=findViewById(R.id.charPho);
        e=findViewById(R.id.charEmail);
        img= findViewById(R.id.imgInfoEdit);
        btnImg=findViewById(R.id.Edit_choose_image);
        fStore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        x=0;
        Intent intent = getIntent();
         s= intent.getStringExtra("EditCharitiesInfo");
        dRef = fStore.collection("Charities").document(s);
        dRef.get().addOnSuccessListener((documentSnapshot) -> {
        if (documentSnapshot != null && documentSnapshot.exists()) {
        n.setText(documentSnapshot.getString("charityName"));
        d.setText(documentSnapshot.getString("charityDes"));
        l.setText(documentSnapshot.getString("charityLoc"));
        p.setText(documentSnapshot.getString("charityPhone"));
        e.setText(documentSnapshot.getString("charityEmail"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToDB();
                Intent i2 = new Intent(Edit_Charity_Info.this, Charity_Info.class);
                i2.putExtra("CharitiesInfo",s);
                startActivity(i2);
            }
        });
        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
                x=1;
            }
        });

        }
        });
        storageReference= FirebaseStorage.getInstance().getReference();
        if (s != null) {
            StorageReference bookReference = storageReference .child("Charities/"+s+"/mainImage.jpg");

            bookReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(Edit_Charity_Info.this).load(uri).into(img);
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
            img.setImageURI(mImageUri);

        }
    }


    public void AddToDB() {
        fStore = FirebaseFirestore.getInstance();

        DocumentReference documentReference = fStore.collection("Charities").document(s);
        documentReference.update("charityName", n.getText().toString(),
                "charityDes", d.getText().toString(),
                "charityLoc", l.getText().toString(),
                "charityPhone", p.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Edit_Charity_Info.this, "mabrooooooook", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Edit_Charity_Info.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                Toast.makeText(Edit_Charity_Info.this, s, Toast.LENGTH_LONG).show();
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
        StorageReference riversRef = storageReference.child("Charities/" + s + "/mainImage.jpg");
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