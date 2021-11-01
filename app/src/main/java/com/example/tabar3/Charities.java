package com.example.tabar3;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.List;
import java.util.Objects;

public class Charities extends Fragment {
    private RecyclerView mRecyclerView;
    ListenerRegistration CharitiesListener;
    View v;
    FirebaseFirestore fStore;
    ListenerRegistration ItemListListener;
    ItemAdabter itemAdapter;
    ImageView imgpro;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_charities, container, false);
        FirebaseApp.initializeApp(getActivity());
        return  v;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setViews();
        setFirebase();
    }

    private void setViews() {
        mRecyclerView = requireView().findViewById(R.id.recyclerView1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        imgpro = requireView().findViewById(R.id.proAdv);
        imgpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(requireContext(), Charities_Profile.class);
                startActivity(i);
            }
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
        Query query = fStore.collection("Charities");
        //  CollectionReference documentReference2 = fStore.collection("Book");
        ItemListListener = query.addSnapshotListener((documentSnapshots, error) -> {
            Log.d("tag","yaaaaaaaaaaaaa raaaaaaaab");
            itemAdapter = new ItemAdabter(Objects.requireNonNull(documentSnapshots).toObjects(item.class));
            mRecyclerView.setAdapter(itemAdapter);
             itemAdapter.setOnItemClickListener(new ItemAdabter.ClickListener() {
                @Override
              public void onItemClick(int position, View v, List<item> CharItems) {
                    item ItemC = CharItems.get(position);
                    if (ItemC.getCharityName() != null) {
                        Intent intent = new Intent(getActivity(), Charity_Info.class);
                        intent.putExtra("CharitiesInfo", ItemC.getCharityId());
                        startActivity(intent);


                    }else
                        Toast.makeText(requireActivity(),"Nooot Dooonee", Toast.LENGTH_SHORT).show();
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