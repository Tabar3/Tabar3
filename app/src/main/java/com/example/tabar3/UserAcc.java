package com.example.tabar3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.List;
import java.util.Objects;

public class UserAcc extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    ListenerRegistration CharitiesListener;
    View v;
    FirebaseFirestore fStore;
    ListenerRegistration ItemListListener;
    ItemAdabter itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_acc);

        setViews();
        setFirebase();
    }
    private void setViews() {
        mRecyclerView =findViewById(R.id.rv2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    private void setFirebase() {
        FirebaseApp.initializeApp(this);
        fStore = FirebaseFirestore.getInstance();
        Query query = fStore.collection("Users").whereEqualTo("accept",false);
        //  CollectionReference documentReference2 = fStore.collection("Book");
        ItemListListener = query.addSnapshotListener((documentSnapshots, error) -> {
            Log.d("tag","yaaaaaaaaaaaaa raaaaaaaab");
            itemAdapter = new ItemAdabter(Objects.requireNonNull(documentSnapshots).toObjects(item.class));
            mRecyclerView.setAdapter(itemAdapter);
            itemAdapter.setOnItemClickListener(new ItemAdabter.ClickListener() {
                @Override
                public void onItemClick(int position, View v, List<item> CharItems) {
                    item ItemC = CharItems.get(position);
                    if (ItemC.getUserName() != null) {
                        Intent intent = new Intent(UserAcc.this, Accounts.class);
                        intent.putExtra("UserInfo", ItemC.getUserId());
                        startActivity(intent);


                    }else
                        Toast.makeText(UserAcc.this,"Nooot Dooonee", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onItemLongClick(int position, View v, List<item> productItems) {

                }
            });
        });
    }



    public void onStop() {
        super.onStop();
        if (CharitiesListener!=null)
            CharitiesListener.remove();
    }

}