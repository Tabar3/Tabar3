package com.example.tabar3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class Charity_Info extends AppCompatActivity {
    TextView txtName,txtDes,Date,tGoal;
    FirebaseFirestore fStore;
    DocumentReference dRef;
    StorageReference storageReference;
    ImageView imgInfo;
    private RecyclerView mRecyclerView;
    ListenerRegistration AdvListener;
    CampAdabter campAdabter;
    ListenerRegistration ItemListListener;
    ImageView imgadd,imgPho,imgLoc,imgVi,imgvii;
    String s,s2;
    FirebaseAuth mAuth;
    String id;
    Button chA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_info);
        fStore = FirebaseFirestore.getInstance();
        txtName=findViewById(R.id.InfoChartxt);
        txtDes=findViewById(R.id.InfoCharGoal);
        imgInfo =findViewById(R.id.InfoCharImg);
        imgPho=findViewById(R.id.ph);
        imgLoc=findViewById(R.id.lo);
        chA=findViewById(R.id.charAcc);
        imgVi=findViewById(R.id.vi);
        imgvii=findViewById(R.id.vii);
        Date=findViewById(R.id.InfoCharDate);
        tGoal=findViewById(R.id.InfoCharG);
        mAuth = FirebaseAuth.getInstance();
        id =mAuth.getCurrentUser().getUid();
        Intent intent = getIntent();
        s= intent.getStringExtra("CharitiesInfo");
        s2=intent.getStringExtra("CharN");
        dRef = fStore.collection("Charities").document(s);
        dRef.get().addOnSuccessListener((documentSnapshot) -> {
            if (documentSnapshot != null && documentSnapshot.exists()) {
                txtName.setText(documentSnapshot.getString("charityName"));
                txtDes.setText(documentSnapshot.getString("charityDes"));
                Date.setText(documentSnapshot.getString("charityDate"));
                tGoal.setText(documentSnapshot.getString("charityGoal"));
                dRef = fStore.collection("Admin").document(id);
                dRef.get().addOnSuccessListener((documentSnapshot5) -> {
                    if (documentSnapshot5 != null && documentSnapshot5.exists()) {
                        if(!(documentSnapshot.getBoolean("accept"))){
                        chA.setVisibility(View.VISIBLE);}

                    }});
                chA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fStore = FirebaseFirestore.getInstance();

                        DocumentReference documentReference = fStore.collection("Charities").document(s);
                        documentReference.update("accept", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Charity_Info.this, "mabrooooooook", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(Charity_Info.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                                Toast.makeText(Charity_Info.this,s, Toast.LENGTH_LONG).show();
                                Log.d("myTag", e.getMessage());

                            }
                        });
                    }
                });
                imgPho.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+documentSnapshot.getString("charityPhone")));
                        startActivity(intent);
                    }
                });
                imgLoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl(documentSnapshot.getString("charityLoc"));                    }
                });
                if(s.equals(id)){
                    imgVi.setVisibility(View.VISIBLE);
                imgVi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Charity_Info.this, Edit_Charity_Info.class);
                        intent.putExtra("EditCharitiesInfo", s);
                        startActivity(intent);

                    }
                });}
                imgvii.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Charity_Info.this, tabar3Ch.class);
                        intent.putExtra("CharId", s);
                        startActivity(intent);

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
                    Glide.with(Charity_Info.this).load(uri).into(imgInfo);
                }
            });
        }

        setViews();
        setFirebase();
    }

    private void gotoUrl(String charityLoc) {
        Uri uri = Uri.parse(charityLoc);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }

    private void setViews() {
        mRecyclerView = findViewById(R.id.recyclerView6);
        imgadd = findViewById(R.id.addCamp);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(s.equals(id)){
            imgadd.setVisibility(View.VISIBLE);
        imgadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Charity_Info.this, Charity_Campaign.class);
                i.putExtra("CharId3", s);
                startActivity(i);
            }
        });}
    }

    @Override
    public void onStart() {
        super.onStart();
        setFirebase();
    }
    private void setFirebase() {
        FirebaseApp.initializeApp(this);
        fStore = FirebaseFirestore.getInstance();

        Query query = fStore.collection("Charities").document(s).collection("Campaign");
        // Query query = fStore.collection("Advertisement");
        //  CollectionReference documentReference2 = fStore.collection("Book");
        ItemListListener = query.addSnapshotListener((documentSnapshots, error) -> {
            Log.d("tag", "yaaaaaaaaaaaaa raaaaaaaab");


            CampAdabter.setOnItemClickListener(new CampAdabter.ClickListener() {
                @Override
                public void onItemClick(int position, View v, List<Camp_Item> Items) {
                    Camp_Item ItemC = Items.get(position);
                    if (ItemC.getCampId() != null) {
                        Intent intent = new Intent(Charity_Info.this, Camp_Info.class);
                        intent.putExtra("Camp", ItemC.getCampId());
                        intent.putExtra("Char", s);
                        intent.putExtra("CharN", s2);
                        startActivity(intent);


                    } else
                        Toast.makeText(Charity_Info.this, "Nooot Dooonee", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onItemLongClick(int position, View v, List<Camp_Item> productItems) {

                }
            });
        });
        getQuery(query);
    }


    private void getQuery(Query query) {
        query.get().addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {

                campAdabter=new CampAdabter(Objects.requireNonNull(task.getResult()).toObjects(Camp_Item.class),s);
               mRecyclerView.setAdapter(campAdabter);
            }
        });
    }


    public void onStop() {
        super.onStop();
        if (AdvListener != null)
            AdvListener.remove();
    }
}
