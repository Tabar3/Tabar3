package com.example.tabar3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.ListenerRegistration;

public class Charities extends Fragment {
    private RecyclerView mRecyclerView;
    ListenerRegistration CharitiesListener;
    View v;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_charities, container, false);
        FirebaseApp.initializeApp(getActivity());
        return  v;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setViews();
        //setFirebase();
    }

    private void setViews() {
        mRecyclerView = requireView().findViewById(R.id.recyclerView1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
    }

    @Override
    public void onStart() {
        super.onStart();
       // setFirebase();
    }

    public void onStop() {
        super.onStop();
        if (CharitiesListener!=null)
            CharitiesListener.remove();
    }

}