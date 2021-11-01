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

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodItemHolder> {
    @NonNull
    @NotNull
    List<Cat_Item> ItmCat;
    private Context mContext;
    private static FoodAdapter.ClickListener clickListener;
    FirebaseFirestore fStore;
    StorageReference storageReference;


    public FoodAdapter(@NonNull List<Cat_Item> itmCat) {
        this.ItmCat = itmCat;
        fStore = FirebaseFirestore.getInstance();
    }


    @Override
    public int getItemCount() {
        return ItmCat.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public FoodItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(mContext).inflate(R.layout.fragment_categories__for__charitie, parent, false);
        storageReference= FirebaseStorage.getInstance().getReference();

        return new FoodItemHolder(v);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull FoodItemHolder holder, int position) {

        Cat_Item itm = ItmCat.get(position);
        String ItemId = itm.getFoodId();

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
                    holder.tname.setText(itm.getFoodDes());


                } else {
                    holder.tname.setText("no name");

                }
            });
        }

        else {
            holder.tname.setText("nooo name");

        }


    }




    public class FoodItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tname;
        ImageView img;


        public FoodItemHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            img = itemView.findViewById(R.id.imgItem2);
            tname = itemView.findViewById(R.id.AdvmDes);


        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v,ItmCat);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v, ItmCat);
            return false;
        }
    }

    public static void setOnItemClickListener(FoodAdapter.ClickListener clickListener ) {
       FoodAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v, List<Cat_Item> Items);

        void onItemLongClick(int position, View v, List<Cat_Item> Items);
    }

}

