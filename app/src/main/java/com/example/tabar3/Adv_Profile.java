package com.example.tabar3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Adv_Profile extends AppCompatActivity {
    ImageView img,imgVi,delete1;
    TextView txtN,txtD,textna,tchN,tT;
    FirebaseFirestore fStore;
    DocumentReference dRef;
    StorageReference storageReference;
    String id;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_profile);
        img=findViewById(R.id.AdvImg);
        txtN=findViewById(R.id.AdvN);
        txtD=findViewById(R.id.AdvD);
        textna=findViewById(R.id.AdvNa);
        imgVi=findViewById(R.id.vi1);
        delete1=findViewById(R.id.delete1);
        tchN=findViewById(R.id.AdvchN);
        tT=findViewById(R.id.AdvT);
        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        id =mAuth.getCurrentUser().getUid();

        Intent intent = getIntent();
        String s= intent.getStringExtra("AdvId1");
        dRef = fStore.collection("Advertisement").document(s+"");
        dRef.get().addOnSuccessListener((documentSnapshot) -> {
            if (documentSnapshot != null && documentSnapshot.exists()) {

                textna.setText(documentSnapshot.getString("advName"));
                txtN.setText(documentSnapshot.getString("advNum"));
                txtD.setText(documentSnapshot.getString("advDes"));
                tT.setText(documentSnapshot.getString("typeOfAdv"));
                tchN.setText(documentSnapshot.getString("charName"));

                dRef = fStore.collection("Admin").document(id);
                dRef.get().addOnSuccessListener((documentSnapshot2) -> {
                    if (documentSnapshot2 != null && documentSnapshot2.exists()) {
                        delete1.setVisibility(View.VISIBLE);
                        delete1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dRef = fStore.collection("Advertisement").document(s+"");                        dRef.delete();
                                StorageReference bookReference = storageReference .child("Advertisement/"+s+"/mainImage.jpg");
                                bookReference.delete();
                                Intent intent = new Intent(Adv_Profile.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                    }});

                if(documentSnapshot.getString("charId").equals(id))
                {
                    imgVi.setVisibility(View.VISIBLE);
                    delete1.setVisibility(View.VISIBLE);

                imgVi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Adv_Profile.this, EditAdvInfo.class);
                        intent.putExtra("EditAdvInfo", s);
                        startActivity(intent);

                    }
                });
                delete1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dRef = fStore.collection("Advertisement").document(s+"");                        dRef.delete();
                        StorageReference bookReference = storageReference .child("Advertisement/"+s+"/mainImage.jpg");
                        bookReference.delete();
                        Intent intent = new Intent(Adv_Profile.this, MainActivity.class);
                        startActivity(intent);
                    }
                });}

            }});

        storageReference= FirebaseStorage.getInstance().getReference();
        if (s != null) {
            StorageReference bookReference = storageReference .child("Advertisement/"+s+"/mainImage.jpg");

            bookReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(Adv_Profile.this).load(uri).into(img);
                }
            });
        }

            }
}