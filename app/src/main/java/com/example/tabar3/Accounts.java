package com.example.tabar3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
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