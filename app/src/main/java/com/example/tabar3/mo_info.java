package com.example.tabar3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class mo_info extends AppCompatActivity {
    TextView mon,monu,mot;
    FirebaseFirestore fStore;
    DocumentReference dRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mo_info);
        mon=findViewById(R.id.mon);
        monu=findViewById(R.id.monu);
        mot=findViewById(R.id.mot);
        fStore = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        String s= intent.getStringExtra("moId");
        String s1= intent.getStringExtra("Char");
        dRef = fStore.collection("Charities").document(s1).collection("Bank").document(s);
        dRef.get().addOnSuccessListener((documentSnapshot) -> {
            if (documentSnapshot != null && documentSnapshot.exists()) {

                mot.setText(documentSnapshot.getString("type"));
                mon.setText(documentSnapshot.getString("moName"));
                monu.setText(documentSnapshot.getString("moNum"));}});
    }
}