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

import java.util.List;
import java.util.Objects;

public class Adv2 extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    ListenerRegistration AdvListener;
    FirebaseAuth mAuth;
    DocumentReference dRef;
    View v;
    FirebaseFirestore fStore;
    ListenerRegistration ItemListListener;
    AdvAdabter advAdabter;
    ImageView imgadd;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv2);
        setViews();
        setFirebase();
    }

    private void setViews() {
        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        imgadd =findViewById(R.id.addAdv);
        mRecyclerView = findViewById(R.id.recyclerView2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        String id =mAuth.getCurrentUser().getUid();
        dRef = fStore.collection("Charities").document(id);
        dRef.get().addOnSuccessListener((documentSnapshot) -> {
            if (documentSnapshot != null && documentSnapshot.exists()) {
                imgadd.setVisibility(View.VISIBLE);
                imgadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Adv2.this, Add_Adv.class);
                        startActivity(i);
                    }
                });
            }
            else
                imgadd.setVisibility(View.GONE);
        });




    }

    @Override
    public void onStart() {
        super.onStart();
        setFirebase();
    }
    private void setFirebase() {
        FirebaseApp.initializeApp(this);
        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        id =mAuth.getCurrentUser().getUid();

        Query query = fStore.collection("Advertisement").whereEqualTo("charId", id);
        //  CollectionReference documentReference2 = fStore.collection("Book");
        ItemListListener = query.addSnapshotListener((documentSnapshots, error) -> {
            Log.d("tag","yaaaaaaaaaaaaa raaaaaaaab");
            advAdabter = new AdvAdabter(Objects.requireNonNull(documentSnapshots).toObjects(Adv_Item.class));
            mRecyclerView.setAdapter(advAdabter);
            advAdabter.setOnItemClickListener(new AdvAdabter.ClickListener() {
                @Override
                public void onItemClick(int position, View v, List<Adv_Item> Items) {
                    Adv_Item ItemC = Items.get(position);
                    if (ItemC.getAdvDes() != null) {
                        Intent intent = new Intent(Adv2.this, Adv_Profile.class);
                        intent.putExtra("AdvId1", ItemC.getAdvId());
                        startActivity(intent);


                    }else
                        Toast.makeText(Adv2.this,"Nooot Dooonee", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onItemLongClick(int position, View v, List<Adv_Item> productItems) {

                }
            });
        });
    }

    public void onStop() {
        super.onStop();
        if (AdvListener!=null)
            AdvListener.remove();}
}
