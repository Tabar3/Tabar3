package com.example.tabar3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Don2 extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_don2, container, false);
        FirebaseApp.initializeApp(getActivity());
        return  v;
        // Inflate the layout for this fragment
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Context context;
        CitemList=new ArrayList<>();
        itemList=new ArrayList<>();
        FirebaseApp.initializeApp(getContext());
        fStore = FirebaseFirestore.getInstance();
        setViews();
        setFirebase();

    }
    private void setViews() {
        mRecyclerView = requireView().findViewById(R.id.recyclerView7);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    String s;
    item UserItems;
    private void setFirebase() {
        fStore = FirebaseFirestore.getInstance();
        Bundle arguments = getArguments();
        String i = arguments.getString("catT");
        mAuth = FirebaseAuth.getInstance();
        //id =mAuth.getCurrentUser().getUid();


        fStore.collection("Users")
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null && task.getResult().size() != 0) {

                itemList = task.getResult().toObjects(item.class);
                UserItems = itemList.get(0);
                Log.d("tag",itemList.size()+"");
                Query query;
                if(i.equals("food")){
                    query = fStore.collection("Users").document(UserItems.getUserId())
                            .collection("category").whereEqualTo("booked", false).whereEqualTo("typeCat", "food_category");
                    s="food";

                }
                else if(i.equals("clo")){
                    query = fStore.collection("Users").document(UserItems.getUserId())
                            .collection("category").whereEqualTo("booked", false).whereEqualTo("typeCat", "clothe_category");
                    s="clo";
                }
                else if(i.equals("tool")){
                    query = fStore.collection("Users").document(UserItems.getUserId())
                            .collection("category").whereEqualTo("booked", false).whereEqualTo("typeCat", "tool_category");
                    s="tool";

                }
                else if(i.equals("sar")){
                    query = fStore.collection("Users").document(UserItems.getUserId())
                            .collection("category").whereEqualTo("booked", false).whereEqualTo("typeCat", "serves_category");
                    s="sar";

                }
                else{
                    query = fStore.collection("Users").document(UserItems.getUserId())
                            .collection("category").whereEqualTo("booked", false).whereEqualTo("typeCat", "other_category");
                    s="other";


                }
                a = 1;
                CitemList.clear();
                getQuery(query);
            }
        });
    }

    private void getQuery(Query query) {
        Bundle arguments = getArguments();
        String i = arguments.getString("catT");
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {

                CitemList.addAll(task.getResult().toObjects(Cat_Item.class));
                if (a < itemList.size()) {
                    // Toast.makeText(this, itemList.size()+"   "+a+"/"+itemList.get(a).getUserId(), Toast.LENGTH_SHORT).show();
                    item UserItems = itemList.get(a);
                    Query query2;
                    // query2 = fStore.collection("Users").document(UserItems.getUserId()).collection("food_calegory");
                    if(i.equals("food")){
                        query2 = fStore.collection("Users").document(UserItems.getUserId())
                                .collection("category").whereEqualTo("booked", false).whereEqualTo("typeCat", "food_category");
                        s="food";

                    }
                    else if(i.equals("clo")){
                        query2 = fStore.collection("Users").document(UserItems.getUserId())
                                .collection("category").whereEqualTo("booked", false).whereEqualTo("typeCat", "clothe_category");
                        s="clo";
                    }
                    else if(i.equals("tool")){
                        query2 = fStore.collection("Users").document(UserItems.getUserId())
                                .collection("category").whereEqualTo("booked", false).whereEqualTo("typeCat", "tool_category");
                        s="tool";

                    }
                    else if(i.equals("sar")){
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

                            Intent intent = new Intent(getContext(),Cat_Info.class);
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
                Toast.makeText(getContext(), "noooooooooooooo", Toast.LENGTH_SHORT).show();
        });
    }
}
