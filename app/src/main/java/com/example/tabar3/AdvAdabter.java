package com.example.tabar3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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


public class AdvAdabter extends RecyclerView.Adapter<AdvAdabter.AdvItemHolder> {
    @NonNull
    @NotNull
    List<Adv_Item> ItmAd;
    private Context mContext;
    private static AdvAdabter.ClickListener clickListener;
    FirebaseFirestore fStore;
    StorageReference storageReference;


    public AdvAdabter(List<Adv_Item> Items) {
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
    public AdvItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(mContext).inflate(R.layout.adv_item, parent, false);
        storageReference=FirebaseStorage.getInstance().getReference();

        return new AdvItemHolder(v);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdvItemHolder holder, int position) {
        Adv_Item itm = ItmAd.get(position);
        String ItemId = itm.getAdvId();

       if (ItemId != null) {

            StorageReference bookReference = storageReference.child("Advertisement/"+ItemId+"/mainImage.jpg");
            bookReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(mContext).load(uri).into(holder.img);
                }
            });

            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Advertisement").document(ItemId);
            documentReference.get().addOnSuccessListener((documentSnapshot) -> {
                if (documentSnapshot != null) {
                    holder.tname.setText(itm.getAdvDes());


                } else {
                    holder.tname.setText("no name");

                }
            });
        }

        else {
            holder.tname.setText("nooo name");

        }


    }
    public class AdvItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tname;
        ImageView img;


        public AdvItemHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            img = itemView.findViewById(R.id.imgItem2);
            tname = itemView.findViewById(R.id.AdvmDes);


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

    public static void setOnItemClickListener(AdvAdabter.ClickListener clickListener) {
        AdvAdabter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v, List<Adv_Item> Items);

        void onItemLongClick(int position, View v, List<Adv_Item> Items);
    }

    }
