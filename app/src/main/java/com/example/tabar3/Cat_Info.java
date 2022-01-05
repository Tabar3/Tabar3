package com.example.tabar3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class Cat_Info extends AppCompatActivity {
    TextView txtCN,txtCD1,txtCD2,t,t1;
    FirebaseFirestore fStore;
    DocumentReference dRef,dRef2,dR3,d;
    StorageReference storageReference;
    ImageView imgInfo,delete;
    ImageView imgVi,img;
    Button booked;
    String id;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_info);
        txtCN=findViewById(R.id.txtCatN);
        txtCD1=findViewById(R.id.txtCatD1);
        txtCD2=findViewById(R.id.txtCatD2);
        delete = findViewById(R.id.delete);
        imgInfo=findViewById(R.id.InfoCatImg);
        imgVi=findViewById(R.id.vi9);
        booked=findViewById(R.id.booked);
        img=findViewById(R.id.InfoUImg);
        t=findViewById(R.id.t);
        t1=findViewById(R.id.t1);
        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        id =mAuth.getCurrentUser().getUid();

        Intent intent = getIntent();
        String s= intent.getStringExtra("CatId");
        String s2= intent.getStringExtra("UserId");
        dRef = fStore.collection("Users").document(s2).collection("category").document(s);
        dRef.get().addOnSuccessListener((documentSnapshot) -> {
            if (documentSnapshot != null && documentSnapshot.exists()) {
                if(documentSnapshot.getBoolean("booked")){
                    booked.setText("تم الحجز");

                }

                txtCN.setText(documentSnapshot.getString("Des"));
                txtCD1.setText(documentSnapshot.getString("typeCat"));
                dRef2=fStore.collection("Users").document(s2);
                dRef2.get().addOnSuccessListener((documentSnapshot3) -> {
                    if (documentSnapshot3 != null && documentSnapshot3.exists()) {
                        txtCD2.setText(documentSnapshot3.getString("UserName"));
                        txtCD2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Cat_Info.this, Accounts.class);
                                intent.putExtra("UserInfo",documentSnapshot3.getString("UserId"));
                                startActivity(intent);
                            }
                        });

                    }});
                dRef = fStore.collection("Admin").document(id);
                dRef.get().addOnSuccessListener((documentSnapshot4) -> {
                    if (documentSnapshot4 != null && documentSnapshot4.exists()) {
                        delete.setVisibility(View.VISIBLE);
                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dRef = fStore.collection("Users").document(s2).collection("category").document(s);
                                dRef.delete();
                                StorageReference bookReference = storageReference .child("Categoty/"+s+"/mainImage.jpg");
                                bookReference.delete();
                                Intent intent = new Intent(Cat_Info.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                    }});
                if (s2.equals(id)){
                    booked.setVisibility(View.GONE);
                    t.setVisibility(View.VISIBLE);
                    t1.setVisibility(View.VISIBLE);
                    t1.setText(documentSnapshot.getString("bookedName"));
                    if (documentSnapshot.getBoolean("booked")){
                    dRef = fStore.collection("Users").document(documentSnapshot.getString("bookedId"));
                    dRef.get().addOnSuccessListener((documentSnapshot2) -> {
                        if (documentSnapshot2 != null && documentSnapshot2.exists()) {
                            t1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Cat_Info.this, Accounts.class);
                                    intent.putExtra("UserInfo",documentSnapshot.getString("bookedId"));
                                    startActivity(intent);
                                }
                            });


                        }else
                        {
                            Intent intent2 = new Intent(Cat_Info.this, Charity_Info.class);
                            intent2.putExtra("CharitiesInfo",documentSnapshot.getString("bookedId"));
                            startActivity(intent2);

                        }});}
                    imgVi.setVisibility(View.VISIBLE);
                imgVi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Cat_Info.this, Edit_Cat_Info.class);
                        intent.putExtra("EditCatInfo", s);
                        intent.putExtra("UserId", s2);
                        intent.putExtra("type", documentSnapshot.getString("typeCat"));
                        startActivity(intent);

                    }
                });
                    delete.setVisibility(View.VISIBLE);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dRef = fStore.collection("Users").document(s2).collection("category").document(s);
                        dRef.delete();
                        StorageReference bookReference = storageReference .child("Categoty/"+s+"/mainImage.jpg");
                        bookReference.delete();
                        Intent intent = new Intent(Cat_Info.this, MainActivity.class);
                        startActivity(intent);
                    }
                });}
                dR3 = fStore.collection("Charities").document(id);
                dR3.get().addOnSuccessListener((documentSnapshot3) -> {
                    if (documentSnapshot3 != null && documentSnapshot3.exists()) {
                        if(documentSnapshot.getString("show").equals("2") ){
                            booked.setVisibility(View.GONE);
                        }
                        booked.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String, Object> itemsCat = new HashMap<>();
                                itemsCat.put("catId", s);
                                itemsCat.put("UserId", s2);
                                itemsCat.put("Des", documentSnapshot.getString("Des"));
                                itemsCat.put("typeCat",  documentSnapshot.getString("typeCat"));

                                DocumentReference documentReference = fStore.collection("Charities")
                                        .document(id).collection("category").document(s);
                                documentReference.set(itemsCat).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        DocumentReference documentReference = fStore.collection("Users").document(s2).collection("category").document(s);
                                        documentReference.update("bookedName",documentSnapshot3.getString("charityName"),"bookedId",id).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(Cat_Info.this, "mabrooooooook", Toast.LENGTH_LONG).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull @NotNull Exception e) {
                                                Toast.makeText(Cat_Info.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                                                Toast.makeText(Cat_Info.this,s, Toast.LENGTH_LONG).show();
                                                Log.d("myTag", e.getMessage());

                                            }
                                        });

                                        booked.setText("تم الحجز");
                                        Toast.makeText(Cat_Info.this, "mabrooooooook", Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull @NotNull Exception e) {
                                        Toast.makeText(Cat_Info.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                                        Toast.makeText(Cat_Info.this, s, Toast.LENGTH_LONG).show();
                                        Log.d("myTag", e.getMessage());

                                    }
                                });

                                DocumentReference documentReference2 = fStore.collection("Users")
                                        .document(s2).collection("category").document(s);
                                documentReference2.update("booked",true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(Cat_Info.this, "mabrooooooook", Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull @NotNull Exception e) {
                                        Toast.makeText(Cat_Info.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                                        Toast.makeText(Cat_Info.this, s, Toast.LENGTH_LONG).show();
                                        Log.d("myTag", e.getMessage());

                                    }
                                });
                            }
                        });

                    }
                else {
                        if(documentSnapshot.getString("show").equals("1")){
                            booked.setVisibility(View.GONE);
                        }
                        booked.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Map<String, Object> itemsCat = new HashMap<>();
                                itemsCat.put("catId", s);
                                itemsCat.put("UserId", s2);
                                itemsCat.put("Des", documentSnapshot.getString("Des"));
                                itemsCat.put("typeCat",  documentSnapshot.getString("typeCat"));

                                DocumentReference documentReference = fStore.collection("Users")
                                        .document(id).collection("catBooked").document(s);
                                documentReference.set(itemsCat).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        DocumentReference dr2  = fStore.collection("Users").document(id);
                                        dr2.get().addOnSuccessListener((documentSnapshot4) -> {
                                        DocumentReference documentReference = fStore.collection("Users").document(s2).collection("category").document(s);
                                        documentReference.update("bookedName",documentSnapshot4.getString("UserName"),"bookedId",id).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(Cat_Info.this, "mabrooooooook", Toast.LENGTH_LONG).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull @NotNull Exception e) {
                                                Toast.makeText(Cat_Info.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                                                Toast.makeText(Cat_Info.this,s, Toast.LENGTH_LONG).show();
                                                Log.d("myTag", e.getMessage());

                                            }
                                        });});
                                        booked.setText("تم الحجز");
                                        Toast.makeText(Cat_Info.this, "mabrooooooook", Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull @NotNull Exception e) {
                                        Toast.makeText(Cat_Info.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                                        Toast.makeText(Cat_Info.this, s, Toast.LENGTH_LONG).show();
                                        Log.d("myTag", e.getMessage());

                                    }
                                });

                                DocumentReference documentReference2 = fStore.collection("Users")
                                        .document(s2).collection("category").document(s);
                                documentReference2.update("booked",true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(Cat_Info.this, "mabrooooooook", Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull @NotNull Exception e) {
                                        Toast.makeText(Cat_Info.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                                        Toast.makeText(Cat_Info.this, s, Toast.LENGTH_LONG).show();
                                        Log.d("myTag", e.getMessage());

                                    }
                                });
                            }
                        });

                    }

                });

            }});

        storageReference= FirebaseStorage.getInstance().getReference();
        if (s != null) {
            StorageReference bookReference = storageReference .child("Categoty/"+s+"/mainImage.jpg");

            bookReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(Cat_Info.this).load(uri).into(imgInfo);
                }
            });
        }
        if (s2 != null) {
            StorageReference bookReference = storageReference .child("User/"+s2+"/mainImage.jpg");

            bookReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(Cat_Info.this).load(uri).into(img);
                }
            });
        }
        dRef2=fStore.collection("Users").document(s2);
        dRef2.get().addOnSuccessListener((documentSnapshot3) -> {
            if (documentSnapshot3 != null && documentSnapshot3.exists()) {
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Cat_Info.this, Accounts.class);
                        intent.putExtra("UserInfo", documentSnapshot3.getString("UserId"));
                        startActivity(intent);
                    }});}
                });
            
    }
}