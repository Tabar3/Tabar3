package com.example.tabar3;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.FirebaseApp;


public class Don extends Fragment {
    View v;
    Button f,c,t,s,o;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_don, container, false);
        FirebaseApp.initializeApp(getActivity());
        return  v;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //setViews();
        //setFirebase();
        f=requireView().findViewById(R.id.food);
        c=requireView().findViewById(R.id.cl);
        t=requireView().findViewById(R.id.tools);
        s=requireView().findViewById(R.id.sar);
        o=requireView().findViewById(R.id.other);

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag","yaaaaaaaaaaaaa raaaaaaaab");
                color();
                f.setBackground(getContext().getDrawable(R.drawable.s2));
                f.setTextColor(Color.parseColor("#FFFFFF"));
                replaceFragment(new Don2(),"food");
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag","yaaaaaaaaaaaaa raaaaaaaab");
                color();
                c.setBackground(getContext().getDrawable(R.drawable.s2));
                c.setTextColor(Color.parseColor("#FFFFFF"));
                replaceFragment(new Don2(),"clo");

            }
        });
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag","yaaaaaaaaaaaaa raaaaaaaab");
                color();
                t.setBackground(getContext().getDrawable(R.drawable.s2));
                t.setTextColor(Color.parseColor("#FFFFFF"));
                replaceFragment(new Don2(),"tool");
            }
        });
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag","yaaaaaaaaaaaaa raaaaaaaab");
                color();
                s.setBackground(getContext().getDrawable(R.drawable.s2));
                s.setTextColor(Color.parseColor("#FFFFFF"));
                replaceFragment(new Don2(),"sar");
            }
        });
        o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag","yaaaaaaaaaaaaa raaaaaaaab");
                color();
                o.setBackground(getContext().getDrawable(R.drawable.s2));
                o.setTextColor(Color.parseColor("#FFFFFF"));
                replaceFragment(new Don2(),"other");
            }
        });
    }

    private void replaceFragment(Fragment fragment,String FRAGMENT_TAG) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Bundle arguments = new Bundle();
        arguments.putString( "catT" , FRAGMENT_TAG);
        fragment.setArguments(arguments);
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment2,fragment);
        fragmentTransaction.commit();

    }
    private void color(){
        f.setBackground(getContext().getDrawable(R.drawable.s1));
        f.setTextColor(Color.parseColor("#0060FF"));
        c.setBackground(getContext().getDrawable(R.drawable.s1));
        c.setTextColor(Color.parseColor("#0060FF"));
        t.setBackground(getContext().getDrawable(R.drawable.s1));
        t.setTextColor(Color.parseColor("#0060FF"));
        s.setBackground(getContext().getDrawable(R.drawable.s1));
        s.setTextColor(Color.parseColor("#0060FF"));
        o.setBackground(getContext().getDrawable(R.drawable.s1));
        o.setTextColor(Color.parseColor("#0060FF"));


    }


}