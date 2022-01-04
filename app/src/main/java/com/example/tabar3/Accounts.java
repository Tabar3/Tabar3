package com.example.tabar3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.Objects;

public class Accounts extends AppCompatActivity {
    TextView txtName,txtDes;
    FirebaseFirestore fStore;
    DocumentReference dRef;
    StorageReference storageReference;
    ImageView imgInfo;
    private RecyclerView mRecyclerView;
    ListenerRegistration AdvListener;
    CampAdabter campAdabter;
    ListenerRegistration ItemListListener;
    ImageView imgadd,imgPho,imgLoc,imgVi;
    String s;
    FirebaseAuth mAuth;
    String id;
    Button usA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#0060FF"));
        actionBar.setBackgroundDrawable(colorDrawable);

        fStore = FirebaseFirestore.getInstance();
        txtName=findViewById(R.id.InfoChartxt);
        txtDes=findViewById(R.id.InfoCharGoal);
        imgInfo =findViewById(R.id.InfoCharImg);
        imgPho=findViewById(R.id.ph);
        imgLoc=findViewById(R.id.lo);
        usA=findViewById(R.id.UserAcc);
        imgVi=findViewById(R.id.vi);
        mAuth = FirebaseAuth.getInstance();
        id =mAuth.getCurrentUser().getUid();
        Intent intent = getIntent();
        s= intent.getStringExtra("UserInfo");
        dRef = fStore.collection("Users").document(s);
        dRef.get().addOnSuccessListener((documentSnapshot) -> {
            if (documentSnapshot != null && documentSnapshot.exists()) {
                txtName.setText(documentSnapshot.getString("UserName"));
                dRef = fStore.collection("Admin").document(id);
                dRef.get().addOnSuccessListener((documentSnapshot5) -> {
                    if (documentSnapshot5 != null && documentSnapshot5.exists()) {
                        if(!(documentSnapshot.getBoolean("accept"))){
                            usA.setVisibility(View.VISIBLE);}

                    }});
                usA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fStore = FirebaseFirestore.getInstance();

                        DocumentReference documentReference = fStore.collection("Users").document(s);
                        documentReference.update("accept", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Accounts.this, "mabrooooooook", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(Accounts.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                                Toast.makeText(Accounts.this,s, Toast.LENGTH_LONG).show();
                                Log.d("myTag", e.getMessage());

                            }
                        });
                    }
                });
                if(s.equals(id)){
                    imgVi.setVisibility(View.VISIBLE);
                    imgVi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Accounts.this, Edit_User_Info.class);
                            intent.putExtra("EditUserInfo", s);
                            startActivity(intent);

                        }
                    });}
                imgPho.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+documentSnapshot.getString("UserPhone")));
                        startActivity(intent);
                    }
                });
                imgLoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl(documentSnapshot.getString("UserLoc"));                    }
                });
                /*if(s.equals(id)){
                    imgVi.setVisibility(View.VISIBLE);
                    imgVi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Accounts.this, Edit_Charity_Info.class);
                            intent.putExtra("EditCharitiesInfo", s);
                            startActivity(intent);

                        }
                    });}*/
            }
        });
        storageReference= FirebaseStorage.getInstance().getReference();
        if (s != null) {
            StorageReference bookReference = storageReference .child("User/"+s+"/mainImage.jpg");

            bookReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(Accounts.this).load(uri).into(imgInfo);
                }
            });
        }

    }

    private void gotoUrl(String charityLoc) {
        Uri uri = Uri.parse(charityLoc);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }


    @Override
    public void onStart() {
        super.onStart();

    }


    public void onStop() {
        super.onStop();
        if (AdvListener != null)
            AdvListener.remove();
    }
}
