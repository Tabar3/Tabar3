package com.example.tabar3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Charity_Info extends AppCompatActivity {
    TextView txtInfo;
    FirebaseFirestore fStore;
    DocumentReference dRef;
    StorageReference storageReference;
    ImageView imgInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_info);
        fStore = FirebaseFirestore.getInstance();
        txtInfo=findViewById(R.id.InfoChartxt);
        imgInfo =findViewById(R.id.InfoCharImg);
        Intent intent = getIntent();
        String s= intent.getStringExtra("CharitiesInfo");
        dRef = fStore.collection("Charities").document(s);
        dRef.get().addOnSuccessListener((documentSnapshot) -> {
            if (documentSnapshot != null && documentSnapshot.exists()) {
                txtInfo.setText(documentSnapshot.getString("charityName"));


                // StorageReference bookReference= FirebaseStorage. getInstance().getReferenceFromUrl("Newbook/"+s+"/mainImage.jpg");

            }
        });
        storageReference= FirebaseStorage.getInstance().getReference();
        if (s != null) {
            StorageReference bookReference = storageReference .child("Charities/"+s+"/mainImage.jpg");

            bookReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(Charity_Info.this).load(uri).into(imgInfo);
                }
            });
        }
    }}