package com.example.tabar3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
<<<<<<< HEAD
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
=======
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
>>>>>>> daniaabuhmiad
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
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
=======
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Accounts extends AppCompatActivity {
    TextView Name,Email,Phone,Location;
    FirebaseFirestore FStore;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DocumentReference dRef;
    ImageView profileImg;
    String CollId;
    private String Id1 ,Id2;
>>>>>>> daniaabuhmiad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
<<<<<<< HEAD
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
=======
        Name=findViewById(R.id.userN);
        Email=findViewById(R.id.userEmail);
        Phone=findViewById(R.id.userPhone);
        Location=findViewById(R.id.userLoc);
        profileImg=findViewById(R.id.ProfileImg);
        mAuth=FirebaseAuth.getInstance();
        FStore=FirebaseFirestore.getInstance();
        user=mAuth.getCurrentUser();
        if (FirebaseAuth.getInstance().getCurrentUser()==null){
            Toast.makeText(this,"عذرا يجب تسجيل الدخول!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {
            DocumentReference docRef = FStore.collection("Users").document(mAuth.getCurrentUser().getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            if (user.getUid().toString().equals(document.getString("UserId"))){
                            Email.setText(document.getString("UserEmail"));
                            Name.setText(document.getString("UserName"));
                            Phone.setText(document.getString("UserPhone"));
                            Location.setText(document.getString("UserLoc"));}
                            else{
                                DocumentReference docRef = FStore.collection("Charities").document(mAuth.getCurrentUser().getUid());
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document != null) {
                                                if (user.getUid().toString().equals(document.getString("charityId")))
                                                {
                                                    Email.setText(document.getString("charityEmail"));
                                                    Name.setText(document.getString("charityName"));
                                                    Phone.setText(document.getString("charityPhone"));
                                                    Location.setText(document.getString("charityLoc"));
                                                }
                                            } else {
                                                Log.d("LOGGER", "No such document");
                                            }
                                        } else {
                                            Log.d("LOGGER", "get failed with ", task.getException());
                                        }
                                    }
                                });
                            }
                        } else {
                            Log.d("LOGGER", "No such document");
                        }
                    } else {
                        Log.d("LOGGER", "get failed with ", task.getException());
                    }
                }
            });
    }
}}
>>>>>>> daniaabuhmiad
