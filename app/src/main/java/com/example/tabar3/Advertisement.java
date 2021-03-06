package com.example.tabar3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.List;
import java.util.Objects;


public class Advertisement extends Fragment {
    private RecyclerView mRecyclerView;
    ListenerRegistration AdvListener;
    FirebaseAuth mAuth;
    DocumentReference dRef;
    View v;
    FirebaseFirestore fStore;
    ListenerRegistration ItemListListener;
    AdvAdabter advAdabter;
    ImageView imgadd;
    item i;
    String strtext;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.fragment_advertisement, container, false);
        FirebaseApp.initializeApp(getActivity());
        return  v;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setViews();
        setFirebase();
    }

    private void setViews() {
        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        imgadd = requireView().findViewById(R.id.addAdv);
        mRecyclerView = requireView().findViewById(R.id.recyclerView2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        String id =mAuth.getCurrentUser().getUid();
        dRef = fStore.collection("Charities").document(id);
        dRef.get().addOnSuccessListener((documentSnapshot) -> {
            if (documentSnapshot != null && documentSnapshot.exists()) {
                if(documentSnapshot.getBoolean("accept")){
                imgadd.setVisibility(View.VISIBLE);
                imgadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), Add_Adv.class);
                        startActivity(i);
                    }
                });
            }}
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
        FirebaseApp.initializeApp(requireActivity());
        fStore = FirebaseFirestore.getInstance();
        Query query = fStore.collection("Advertisement");
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
                        Intent intent = new Intent(getActivity(), Adv_Profile.class);
                        intent.putExtra("AdvId1", ItemC.getAdvId());
                        startActivity(intent);


                    }else
                        Toast.makeText(requireActivity(),"Nooot Dooonee", Toast.LENGTH_SHORT).show();
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