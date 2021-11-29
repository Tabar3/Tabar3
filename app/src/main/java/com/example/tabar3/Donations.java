package com.example.tabar3;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

public class Donations extends Fragment {
    private RecyclerView mRecyclerView;
    ListenerRegistration CharitiesListener;
    DocumentReference dRef;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    View v;
    Button btnF , btnC , btnT , btnS , btnO;

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            v= inflater.inflate(R.layout.fragment_donations, container, false);
            FirebaseApp.initializeApp(getActivity());
            return  v;
        }


        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

            btnF=getView().findViewById(R.id.food);
            btnC=getView().findViewById(R.id.cl);
            btnT=getView().findViewById(R.id.tools);
            btnS=getView().findViewById(R.id.sar);
            btnO=getView().findViewById(R.id.other);

            setViews();
           // setFirebase();
        }

    private void setViews() {
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        String id =mAuth.getCurrentUser().getUid();

        btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dRef = fStore.collection("Users").document(id);
                dRef.get().addOnSuccessListener((documentSnapshot) -> {
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        Intent i1;
                        i1 = new Intent(getActivity(),Categories.class);
                        i1.putExtra("category","food");
                        startActivity(i1);

                    }
                    else
                    { Intent i1;
                        i1 = new Intent(getActivity(),Categories_For_Charitie.class);
                        i1.putExtra("category","food");
                        startActivity(i1);}
                });

        }
    });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dRef = fStore.collection("Users").document(id);
                dRef.get().addOnSuccessListener((documentSnapshot) -> {

                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        Intent i1;
                        i1 = new Intent(getActivity(),Categories.class);
                        i1.putExtra("category","clo");
                        startActivity(i1);

                    }
                    else
                    { Intent i1;
                        i1 = new Intent(getActivity(),Categories_For_Charitie.class);
                        i1.putExtra("category","clo");
                        startActivity(i1);}
                });

            }
        });
        btnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dRef = fStore.collection("Users").document(id);
                dRef.get().addOnSuccessListener((documentSnapshot) -> {

                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        Intent i1;
                        i1 = new Intent(getActivity(),Categories.class);
                        i1.putExtra("category","tool");
                        startActivity(i1);

                    }
                    else
                    { Intent i1;
                        i1 = new Intent(getActivity(),Categories_For_Charitie.class);
                        i1.putExtra("category","tool");
                        startActivity(i1);}
                });

            }
        });

        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dRef = fStore.collection("Users").document(id);
                dRef.get().addOnSuccessListener((documentSnapshot) -> {

                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        Intent i1;
                        i1 = new Intent(getActivity(),Categories.class);
                        i1.putExtra("category","sar");
                        startActivity(i1);

                    }
                    else
                    { Intent i1;
                        i1 = new Intent(getActivity(),Categories_For_Charitie.class);
                        i1.putExtra("category","sar");
                        startActivity(i1);}
                });

            }

        });
        btnO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dRef = fStore.collection("Users").document(id);
                dRef.get().addOnSuccessListener((documentSnapshot) -> {

                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        Intent i1;
                        i1 = new Intent(getActivity(),Categories.class);
                        i1.putExtra("category","other");
                        startActivity(i1);

                    }
                    else
                    { Intent i1;
                        i1 = new Intent(getActivity(),Categories_For_Charitie.class);
                        i1.putExtra("category","other");
                        startActivity(i1);}
                });

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();

        //setFirebase();
    }

    public void onStop() {
        super.onStop();
        if (CharitiesListener!=null)
            CharitiesListener.remove();

    }
}