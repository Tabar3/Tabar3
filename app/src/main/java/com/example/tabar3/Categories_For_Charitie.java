package com.example.tabar3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Categories_For_Charitie extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    ListenerRegistration AdvListener;
    FirebaseAuth mAuth;
    List<Cat_Item> ItmCat;
    List<item>itemList;
    int a;
    List<Cat_Item>CitemList;
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
        setContentView(R.layout.activity_categories_for_charitie);
        FirebaseApp.initializeApp(this);
        CitemList=new ArrayList<>();
        itemList=new ArrayList<>();
        FirebaseApp.initializeApp(this);

        fStore = FirebaseFirestore.getInstance();
        setViews();
        setFirebase();
        //setProduct();


    }

    private void setViews() {
        mRecyclerView = findViewById(R.id.recyclerView4);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onStart() {
        super.onStart();
        //setProduct();
        setFirebase();
    }
    String s;
    item UserItems;
    private void setFirebase() {
        FirebaseApp.initializeApp(this);
        fStore = FirebaseFirestore.getInstance();
        Intent i =getIntent();
        mAuth = FirebaseAuth.getInstance();
        //id =mAuth.getCurrentUser().getUid();


        fStore.collection("Users")
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null && task.getResult().size() != 0) {

                itemList = task.getResult().toObjects(item.class);
                UserItems = itemList.get(0);
                Log.d("tag",itemList.size()+"");
        Query query;
                if(i.getStringExtra("category").equals("food")){
                    query = fStore.collection("Users").document(UserItems.getUserId())
                            .collection("category").whereEqualTo("booked", false).whereEqualTo("typeCat", "food_category");
                    s="food";

                }
                else if(i.getStringExtra("category").equals("clo")){
                    query = fStore.collection("Users").document(UserItems.getUserId())
                            .collection("category").whereEqualTo("booked", false).whereEqualTo("typeCat", "clothe_category");
                    s="clo";
                }
                else if(i.getStringExtra("category").equals("tool")){
                    query = fStore.collection("Users").document(UserItems.getUserId())
                            .collection("category").whereEqualTo("booked", false).whereEqualTo("typeCat", "tool_category");
                    s="tool";

                }
                else if(i.getStringExtra("category").equals("sar")){
                    query = fStore.collection("Users").document(UserItems.getUserId())
                            .collection("category").whereEqualTo("booked", false).whereEqualTo("typeCat", "serves_category");
                    s="sar";

                }
                else{
                    query = fStore.collection("Users").document(UserItems.getUserId())
                            .collection("category").whereEqualTo("booked", false).whereEqualTo("typeCat", "other_category");
                    s="other";


                }




       // Query query = fStore.collection("Advertisement");
        //  CollectionReference documentReference2 = fStore.collection("Book");
      /* ItemListListener = query.addSnapshotListener((documentSnapshots, error) -> {
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
        });*/
                a = 1;
                CitemList.clear();
        getQuery(query);
            }
        });
    }
    private void getQuery2(Query query) {
        query.get().addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {
                   //CatAdabter = new ChategoryAdapter(Objects.requireNonNull(task.getResult()).toObjects(Cat_Item.class));
                   // mRecyclerView.setAdapter(CatAdabter);
            }
        });
    }


    public void onStop() {
        super.onStop();
        if (AdvListener != null)
            AdvListener.remove();
    }

   /* private void setProduct() {
        fStore.collection("Users")
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null && task.getResult().size() != 0) {

                itemList = task.getResult().toObjects(item.class);
                item UserItems = itemList.get(0);
                Log.d("tag",itemList.size()+"");
                Query query;
                query = fStore.collection("Users").document(UserItems.getUserId())
                        .collection("food_calegory").whereEqualTo("booked", false);;
                a = 1;
                CitemList.clear();
                getQuery(query);
            }
        });

    }*/

    private void getQuery(Query query) {
        Intent i =getIntent();
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {

                CitemList.addAll(task.getResult().toObjects(Cat_Item.class));
                if (a < itemList.size()) {
                   // Toast.makeText(this, itemList.size()+"   "+a+"/"+itemList.get(a).getUserId(), Toast.LENGTH_SHORT).show();
                    item UserItems = itemList.get(a);
                    Query query2;
                   // query2 = fStore.collection("Users").document(UserItems.getUserId()).collection("food_calegory");
                    if(i.getStringExtra("category").equals("food")){
                        query2 = fStore.collection("Users").document(UserItems.getUserId())
                                .collection("category").whereEqualTo("booked", false).whereEqualTo("typeCat", "food_category");
                        s="food";

                    }
                    else if(i.getStringExtra("category").equals("clo")){
                        query2 = fStore.collection("Users").document(UserItems.getUserId())
                                .collection("category").whereEqualTo("booked", false).whereEqualTo("typeCat", "clothe_category");
                        s="clo";
                    }
                    else if(i.getStringExtra("category").equals("tool")){
                        query2 = fStore.collection("Users").document(UserItems.getUserId())
                                .collection("category").whereEqualTo("booked", false).whereEqualTo("typeCat", "tool_category");
                        s="tool";

                    }
                    else if(i.getStringExtra("category").equals("sar")){
                        query2 = fStore.collection("Users").document(UserItems.getUserId())
                                .collection("category").whereEqualTo("booked", false).whereEqualTo("typeCat", "serves_category");
                        s="sar";

                    }
                    else{
                        query2 = fStore.collection("Users").document(UserItems.getUserId())
                                .collection("category").whereEqualTo("booked", false).whereEqualTo("typeCat", "other_category");
                        s="other";


                    }


                    a++;
                    getQuery(query2);
                } else if(a==itemList.size()) {
                    Collections.shuffle(CitemList);
                    CatAdabter = new ChategoryAdapter(CitemList,"U");

                    mRecyclerView.setAdapter(CatAdabter);
                    CatAdabter.setOnItemClickListener(new ChategoryAdapter.ClickListener() {
                        @Override
                        public void onItemClick(int position, View v, List<Cat_Item> productItems) {
                            Cat_Item productItems1 = productItems.get(position);
                            Intent intent = new Intent(Categories_For_Charitie.this,Cat_Info.class);
                            intent.putExtra("CatId", productItems1.getCatId());
                            intent.putExtra("UserId", productItems1.getUserId());

                            startActivity(intent);
                        }

                        @Override
                        public void onItemLongClick(int position, View v, List<Cat_Item> productItems) {

                        }
                    });
                }
            }
            else
                Toast.makeText(this, "noooooooooooooo", Toast.LENGTH_SHORT).show();
        });
    }
}
