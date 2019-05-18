//Sai Girish
package com.example.android.s4s;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class RecyclerViewAdapterSales extends RecyclerView.Adapter<RecyclerViewAdapterSales.ViewHolder> {

    Context context;
    List<SalesDetails> MainImageUploadInfoList;


    public RecyclerViewAdapterSales(Context context, List<SalesDetails> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items_old_transactions, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SalesDetails salesDetails = MainImageUploadInfoList.get(position);

        holder.SaleNameTextView.setText(salesDetails.getBookName() + "\n");

        holder.SaleNumberTextView.setText("Book Name: " + salesDetails.getBookName() + "\nAuthor Name: " + salesDetails.getAuthorsName() + "\nEdition: " + salesDetails.getBookEdition() + "\nPublisher: " + salesDetails.getPublisher() + "\nPrice: " + salesDetails.getPrice());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/"+ salesDetails.getBook_Id());
        GlideApp.with(context)
                .load(storageReference)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView SaleNameTextView;
        public TextView SaleNumberTextView;
        public ImageView img;


        public ViewHolder(View itemView) {

            super(itemView);

            SaleNameTextView = itemView.findViewById(R.id.book_number);

            SaleNumberTextView = itemView.findViewById(R.id.book_description);

            img = itemView.findViewById(R.id.imageView1);

        }
    }
}

//Sai Girish