package com.example.tabar3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;


public class viAdabter extends RecyclerView.Adapter<viAdabter.viAdabterHolder> {
    @NonNull
    @NotNull
    List<mo_Item> ItmAd;
    private Context mContext;
    private static viAdabter.ClickListener clickListener;
    FirebaseFirestore fStore;
    StorageReference storageReference;


    public viAdabter(List<mo_Item> Items) {
        this.ItmAd= Items;

        fStore = FirebaseFirestore.getInstance();
    }

    @Override
    public int getItemCount() {
        return ItmAd.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public viAdabterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_mo_item, parent, false);
        storageReference=FirebaseStorage.getInstance().getReference();

        return new viAdabterHolder(v);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viAdabterHolder holder, int position) {
        mo_Item itm = ItmAd.get(position);
        String ItemId = itm.getMoId();

       if (ItemId != null) {

           fStore.collection("Charities").document(itm.getCharId()).get().addOnSuccessListener(documentSnapshot -> {
               if (documentSnapshot != null && documentSnapshot.exists()) {
               }
           });

           holder.tname.setText(itm.moName);
           holder.tnum.setText(itm.moNum);
       }

    }
    public class viAdabterHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tname,tnum;
        ImageView img;


        public viAdabterHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            tname = itemView.findViewById(R.id.moDes);
            tnum = itemView.findViewById(R.id.monum);

        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v,ItmAd);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v, ItmAd);
            return false;
        }
    }

    public static void setOnItemClickListener(viAdabter.ClickListener clickListener) {
        viAdabter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v, List<mo_Item> Items);

        void onItemLongClick(int position, View v, List<mo_Item> Items);
    }

    }
