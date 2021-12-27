package com.example.tabar3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class tabar3Ch extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    ListenerRegistration AdvListener;
    FirebaseAuth mAuth;
    DocumentReference dRef;
    View v;
    FirebaseFirestore fStore;
    ListenerRegistration ItemListListener;
    viAdabter viAdabter;
    ImageView imgadd;
    List<item>itemList;
    List<mo_Item>CitemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabar3_ch);
        CitemList=new ArrayList<>();
        itemList=new ArrayList<>();
        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        imgadd = findViewById(R.id.addvi);
        mRecyclerView = findViewById(R.id.rv3);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        String id =mAuth.getCurrentUser().getUid();
        Intent intent =getIntent();
        String s= intent.getStringExtra("CharId");
        dRef = fStore.collection("Charities").document(s);
        dRef.get().addOnSuccessListener((documentSnapshot) -> {
            if (documentSnapshot != null && documentSnapshot.exists()) {
                if (s.equals(id)) {
                    imgadd.setVisibility(View.VISIBLE);
                    imgadd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(tabar3Ch.this, Add_vi.class);
                            startActivity(i);
                        }
                    });
                } else
                    imgadd.setVisibility(View.GONE);
            }
            else
                Toast.makeText(tabar3Ch.this,"waaaaww",Toast.LENGTH_LONG).show();
        });
        setFirebase();
    }

    public void onStart() {
        super.onStart();
        setFirebase();
    }

    item UserItems;
    int a;
    private void setFirebase() {
        FirebaseApp.initializeApp(this);
        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        String id =mAuth.getCurrentUser().getUid();
        fStore = FirebaseFirestore.getInstance();



        fStore.collection("Charities")
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null && task.getResult().size() != 0) {

                itemList = task.getResult().toObjects(item.class);
                UserItems = itemList.get(0);
                Log.d("tag", itemList.size() + "");
                Query query;
                query = fStore.collection("Charities").document(UserItems.getCharityId())
                        .collection("Bank");

                a = 1;
                CitemList.clear();
                getQuery(query);

            }
        });

    }


    public void onStop() {
        super.onStop();
        if (AdvListener!=null)
            AdvListener.remove();}

    private void getQuery(Query query) {
        Intent i =getIntent();
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {

                CitemList.addAll(task.getResult().toObjects(mo_Item.class));
                if (a < itemList.size()) {
                    // Toast.makeText(this, itemList.size()+"   "+a+"/"+itemList.get(a).getUserId(), Toast.LENGTH_SHORT).show();
                    item UserItems = itemList.get(a);
                    Query query2;
                    // query2 = fStore.collection("Users").document(UserItems.getUserId()).collection("food_calegory");
                    query2 = fStore.collection("Charities").document(UserItems.getCharityId())
                            .collection("Bank");


                    a++;
                    getQuery(query2);
                } else if(a==itemList.size()) {
                    Collections.shuffle(CitemList);
                    viAdabter = new viAdabter(CitemList);

                    mRecyclerView.setAdapter(viAdabter);
                }
            }
            else
                Toast.makeText(this, "noooooooooooooo", Toast.LENGTH_SHORT).show();
        });
    }
}
