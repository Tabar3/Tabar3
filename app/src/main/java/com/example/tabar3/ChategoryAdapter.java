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

public class ChategoryAdapter extends RecyclerView.Adapter<ChategoryAdapter.FoodItemHolder> {
    @NonNull
    @NotNull
    List<Cat_Item> ItmCat;
    List<item> Itm2;
    private Context mContext;
    private static ChategoryAdapter.ClickListener clickListener;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    String typeCat;
    String idC;
    public ChategoryAdapter(){}

    public ChategoryAdapter(@NonNull List<Cat_Item> itmCat) {
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
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_cat_item, parent, false);
        storageReference= FirebaseStorage.getInstance().getReference();

        return new FoodItemHolder(v);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull FoodItemHolder holder, int position) {

        Cat_Item itm = ItmCat.get(position);
        String ItemId = itm.getCatId();


        fStore.collection("Users").document(itm.getUserId()).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot != null && documentSnapshot.exists()){}
               // itm.setFoodName(documentSnapshot.getString("foodName"));

        });

            holder.tname.setText(itm.getDes());



        StorageReference bookReference = storageReference.child("Categoty/"+ItemId+"/mainImage.jpg");
        bookReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(mContext).load(uri).into(holder.img);
            }
        });




       /* Cat_Item itm = ItmCat.get(position);
        String ItemId = itm.getCatId();
        item item = Itm2.get(position);
        String i=item.getUserId();
        Toast.makeText(mContext,""+ItemId,Toast.LENGTH_LONG).show();
        if (ItemId != null) {

            StorageReference bookReference = storageReference.child("Categoty/"+ItemId+"/mainImage.jpg");
            bookReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(mContext).load(uri).into(holder.img);
                }
            });
            if(typeCat.equals("food")){
            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(i);
            documentReference.get().addOnSuccessListener((documentSnapshot) -> {
                if (documentSnapshot != null) {

                    DocumentReference documentReference2 = FirebaseFirestore.getInstance().collection("Users").document(i).collection("food_calegory").document(ItemId);

                    holder.tname.setText(itm.getFoodDes());
                } else {
                    holder.tname.setText("no name");

                }
            });
        }
        else if(typeCat.equals("clo")){
            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(i).collection("clothe_calegory").document(ItemId);
            documentReference.get().addOnSuccessListener((documentSnapshot) -> {
                if (documentSnapshot != null) {
                    holder.tname.setText(itm.getClotheDes());


                } else {
                    holder.tname.setText("no name");

                }
            });
        }
        else if(typeCat.equals("tool")) {
                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(i).collection("tool_calegory").document(ItemId);
                documentReference.get().addOnSuccessListener((documentSnapshot) -> {
                    if (documentSnapshot != null) {
                        holder.tname.setText(itm.getToolDes());


                    } else {
                        holder.tname.setText("no name");

                    }
                });

            }

            else if(typeCat.equals("sar")) {
                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(i).collection("serves_calegory").document(ItemId);
                documentReference.get().addOnSuccessListener((documentSnapshot) -> {
                    if (documentSnapshot != null) {
                        holder.tname.setText(itm.getToolDes());


                    } else {
                        holder.tname.setText("no name");

                    }
                });

            }
            else if(typeCat.equals("other")) {
                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(i).collection("other_calegory").document(ItemId);
                documentReference.get().addOnSuccessListener((documentSnapshot) -> {
                    if (documentSnapshot != null) {
                        holder.tname.setText(itm.getToolDes());


                    } else {
                        holder.tname.setText("no name");

                    }
                });

            }

        }
        else {
            holder.tname.setText("nooo name");

        }*/



    }




    public class FoodItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tname;
        ImageView img;


        public FoodItemHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            img = itemView.findViewById(R.id.imgItem32);
            tname = itemView.findViewById(R.id.charDes);


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

    public static void setOnItemClickListener(ChategoryAdapter.ClickListener clickListener ) {
       ChategoryAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v, List<Cat_Item> Items);

        void onItemLongClick(int position, View v, List<Cat_Item> Items);
    }

}

