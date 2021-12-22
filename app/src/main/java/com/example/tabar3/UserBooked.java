package com.example.tabar3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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
import java.util.List;
import java.util.Objects;

public class UserBooked extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    ListenerRegistration AdvListener;
    DocumentReference dRef;
    FirebaseAuth mAuth;
    List<Cat_Item> ItmCat;
    List<item> itemList;
    int a;
    List<Cat_Item> CitemList;
    Context mContext;
    View v;
    FirebaseFirestore fStore;
    ListenerRegistration ItemListListener;
    ChategoryAdapter CatAdabter;
    ImageView imgadd;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booked);
        FirebaseApp.initializeApp(this);
        CitemList = new ArrayList<>();
        itemList = new ArrayList<>();
        FirebaseApp.initializeApp(this);
        fStore = FirebaseFirestore.getInstance();
        setViews();
        setFirebase();
    }

    private void setViews() {
        mRecyclerView = findViewById(R.id.rVH2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onStart() {
        super.onStart();
        //setProduct();
        id = mAuth.getCurrentUser().getUid();
        setFirebase();
    }

    String s;
    Query query;
    private void setFirebase() {
        FirebaseApp.initializeApp(this);
        fStore = FirebaseFirestore.getInstance();
        Intent i = getIntent();
        mAuth = FirebaseAuth.getInstance();
        id = mAuth.getCurrentUser().getUid();


        query = fStore.collection("Users").document(id).collection("catBooked");



        ItemListListener = query.addSnapshotListener((documentSnapshots, error) -> {
            Log.d("tag", "Hiiiiistoooooyyy");

            CatAdabter.setOnItemClickListener(new ChategoryAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View v, List<Cat_Item> Items) {
                    Cat_Item ItemC = Items.get(position);
                    if (ItemC.getDes() != null) {
                        Intent intent = new Intent(UserBooked.this, Cat_Info.class);
                        intent.putExtra("CatId", ItemC.getCatId());
                        intent.putExtra("UserId", ItemC.getUserId());
                        startActivity(intent);


                    } else
                        Toast.makeText(UserBooked.this, "Nooot Dooonee", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onItemLongClick(int position, View v, List<Cat_Item> productItems) {

                }
            });
        });

        getQuery(query);

    }
    private void getQuery(Query query) {
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {

                CatAdabter = new ChategoryAdapter(Objects.requireNonNull(task.getResult()).toObjects(Cat_Item.class),"C");
                mRecyclerView.setAdapter(CatAdabter);
            }
        });
    }


    public void onStop() {
        super.onStop();
        if (AdvListener!=null)
            AdvListener.remove();}
}