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

public class CampAdabter extends RecyclerView.Adapter<CampAdabter.CampItemHolder> {
    @NonNull
    @NotNull
    List<Camp_Item> ItmCam;
    //List<item>itemCha;
    String s;
    private Context mContext;
    private static CampAdabter.ClickListener clickListener;
    FirebaseFirestore fStore;
    StorageReference storageReference;


    public CampAdabter(List<Camp_Item> Items,String s) {
        this.ItmCam= Items;
       // this.itemCha=itemCha;
        this.s=s;
        fStore = FirebaseFirestore.getInstance();
    }

    @Override
    public int getItemCount() {
        return ItmCam.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public CampAdabter.CampItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_camp_item, parent, false);
        storageReference= FirebaseStorage.getInstance().getReference();

        return new CampAdabter.CampItemHolder(v);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CampAdabter.CampItemHolder holder, int position) {
        Camp_Item itm = ItmCam.get(position);
        String ItemId = itm.getCampId();
       // item i = itemCha.get(position);
       // String i2 = i.getCharityId();
        if (ItemId != null) {

            StorageReference bookReference = storageReference.child("Campaign/"+ItemId+"/mainImage.jpg");
            bookReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(mContext).load(uri).into(holder.img);
                }
            });

            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Charities").document(s).collection("Campaign").document(ItemId);
            documentReference.get().addOnSuccessListener((documentSnapshot) -> {
                if (documentSnapshot != null) {
                    holder.tname.setText(itm.getCampName());
                    holder.tD.setText(itm.getCampDes1());

                } else {
                    holder.tname.setText("no name");

                }
            });
        }

        else {
            holder.tname.setText("nooo name");

        }


    }
    public class CampItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tname,tD;
        ImageView img;


        public CampItemHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            img = itemView.findViewById(R.id.imgItem6);
            tname = itemView.findViewById(R.id.CampN);
            tD = itemView.findViewById(R.id.CampD);


        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v,ItmCam);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v, ItmCam);
            return false;
        }
    }

    public static void setOnItemClickListener(CampAdabter.ClickListener clickListener) {
        CampAdabter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v, List<Camp_Item> Items);

        void onItemLongClick(int position, View v, List<Camp_Item> Items);
    }

}
