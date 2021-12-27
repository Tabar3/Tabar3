package com.example.tabar3;

import android.view.ViewGroup;
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

import de.hdodenhof.circleimageview.CircleImageView;


public class ItemAdabter extends RecyclerView.Adapter<ItemAdabter.ItemHolder> {
    @NonNull
    @NotNull
    List<item> ItmA;
    private Context mContext;
    private static ItemAdabter.ClickListener clickListener;
    FirebaseFirestore fStore;
    StorageReference storageReference;


    public ItemAdabter(List<item> Items) {
        this.ItmA= Items;

        fStore = FirebaseFirestore.getInstance();
    }

    @Override
    public int getItemCount() {
        return ItmA.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        storageReference=FirebaseStorage.getInstance().getReference();

        return new ItemHolder(v);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        item itm = ItmA.get(position);
        String ItemId = itm.getCharityId();
        String ItemUId = itm.getUserId();
       if (ItemId != null) {
               StorageReference bookReference = storageReference.child("Charities/" + ItemId + "/mainImage.jpg");
               bookReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {
                       Glide.with(mContext).load(uri).into(holder.img);
                   }
               });

               DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Charities").document(ItemId);
               documentReference.get().addOnSuccessListener((documentSnapshot) -> {
                   if (documentSnapshot != null && documentSnapshot.exists()) {
                       holder.tname.setText(itm.getCharityName());
                       holder.tDes.setText(itm.getCharityDes());
                       holder.tD.setText(itm.getCharityDate());
                   } else {
                       holder.tname.setText("no name");

                   }
               });
           } else if(ItemUId !=null){
               StorageReference bookReference = storageReference.child("User/" + ItemUId + "/mainImage.jpg");
               bookReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {
                       Glide.with(mContext).load(uri).into(holder.img);
                   }
               });

               DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(ItemUId);
               documentReference.get().addOnSuccessListener((documentSnapshot) -> {
                   if (documentSnapshot != null && documentSnapshot.exists()) {
                       holder.tname.setText(itm.getUserName());
                       holder.tDes.setText(itm.getUserPhone());
                       holder.tD.setVisibility(View.GONE);

                   } else {
                       holder.tname.setText("no name");

                   }
               });

           }
           else {
               holder.tname.setText("nooo name");

           }

       }


    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tname,tDes,tD;
        ImageView img;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            img = itemView.findViewById(R.id.imgItem);
            tname = itemView.findViewById(R.id.itemName);
            tDes = itemView.findViewById(R.id.itemDes);
            tD = itemView.findViewById(R.id.itemDeta);

        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v, ItmA);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v, ItmA);
            return false;
        }
    }

    public static void setOnItemClickListener(ItemAdabter.ClickListener clickListener) {
        ItemAdabter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v, List<item> Items);

        void onItemLongClick(int position, View v, List<item> Items);
    }

    }
