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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.List;
import java.util.Objects;

public class Categories_For_Charitie extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    ListenerRegistration AdvListener;
    View v;
    FirebaseFirestore fStore;
    ListenerRegistration ItemListListener;
    ChategoryAdapter CatAdabter;
    ImageView imgadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_for_charitie);
        FirebaseApp.initializeApp(this);
        setViews();
        setFirebase();

    }

    private void setViews() {
        mRecyclerView = findViewById(R.id.recyclerView4);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onStart() {
        super.onStart();
        setFirebase();
    }
    String s;
    private void setFirebase() {
        FirebaseApp.initializeApp(this);
        fStore = FirebaseFirestore.getInstance();
        Intent i =getIntent();

        Query query;
        if(i.getStringExtra("category").equals("food")){
            query = fStore.collection("Users").document(
                "dQ8ZL6YfV8qAOnv6ILYa").collection("food_calegory");
            s="food";

        }
        else if(i.getStringExtra("category").equals("clo")){
            query = fStore.collection("Users").document(
                    "dQ8ZL6YfV8qAOnv6ILYa").collection("clothe_calegory");
            s="clo";
        }
        else if(i.getStringExtra("category").equals("tool")){
           query = fStore.collection("Users").document(
                    "dQ8ZL6YfV8qAOnv6ILYa").collection("tool_calegory");
            s="tool";

        }
        else if(i.getStringExtra("category").equals("sar")){
             query = fStore.collection("Users").document(
                    "dQ8ZL6YfV8qAOnv6ILYa").collection("serves_calegory");
            s="sar";

        }
        else{
             query = fStore.collection("Users").document(
                    "dQ8ZL6YfV8qAOnv6ILYa").collection("other_calegory");
            s="other";


        }




       // Query query = fStore.collection("Advertisement");
        //  CollectionReference documentReference2 = fStore.collection("Book");
        ItemListListener = query.addSnapshotListener((documentSnapshots, error) -> {
            Log.d("tag", "yaaaaaaaaaaaaa raaaaaaaab");


            CatAdabter.setOnItemClickListener(new ChategoryAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View v, List<Cat_Item> Items) {
                    Cat_Item ItemC = Items.get(position);
                    if (ItemC.getFoodDes() != null) {
                        Intent intent = new Intent(Categories_For_Charitie.this, Categories.class); //تغير لاسم الاكتيفيتي الجديد تبع معلومات الاعلان
                        intent.putExtra("", ItemC.getCatId());
                        startActivity(intent);


                    } else
                        Toast.makeText(Categories_For_Charitie.this, "Nooot Dooonee", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onItemLongClick(int position, View v, List<Cat_Item> productItems) {

                }
            });
        });
        getQuery(query);
    }
    private void getQuery(Query query) {
        query.get().addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {
                   CatAdabter = new ChategoryAdapter(Objects.requireNonNull(task.getResult()).toObjects(Cat_Item.class), s);
                    mRecyclerView.setAdapter(CatAdabter);
            }
        });
    }


    public void onStop() {
        super.onStop();
        if (AdvListener != null)
            AdvListener.remove();
    }
}
