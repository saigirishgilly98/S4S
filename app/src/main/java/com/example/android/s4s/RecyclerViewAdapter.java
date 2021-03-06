//Sai Girish
package com.example.android.s4s;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<StudentDetails> MainImageUploadInfoList;
    String latitudeA,longitudeA,latitudeB,longitudeB;


    public RecyclerViewAdapter(Context context, List<StudentDetails> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final StudentDetails studentDetails = MainImageUploadInfoList.get(position);

        holder.StudentNameTextView.setText(studentDetails.getBookName() + "\n");

        holder.StudentNumberTextView.setText("Book Name: " + studentDetails.getBookName() + "\nAuthor Name: " + studentDetails.getAuthorsName() + "\nEdition: " + studentDetails.getBookEdition() + "\nPublisher: " + studentDetails.getPublisher() + "\nPrice: " + studentDetails.getPrice());

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference messageRef = database.getReference("Seller");
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                Query queryRef = messageRef.child(currentFirebaseUser.getUid())
                        .orderByChild("Book_Id")
                        .equalTo(studentDetails.getBook_Id());
                queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            messageRef.child(currentFirebaseUser.getUid()).child(studentDetails.getBook_Id()).child("Flag").setValue("0");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                Toast.makeText(getApplicationContext(), "Book is removed from MyOrders" ,
                        Toast.LENGTH_SHORT).show();
            }
        });

        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, payment.class);
                intent.putExtra("bookid",studentDetails.getBook_Id());
                context.startActivity(intent);
            }
        });

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(mAuth.getCurrentUser().getUid());


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                latitudeA = dataSnapshot.child("latitude").getValue().toString();
                longitudeA = dataSnapshot.child("longitude").getValue().toString();
                System.out.println("Latitude A = "+latitudeA);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        Location locationA = new Location("point A");

        try {
            double latA = Double.parseDouble(latitudeA);
            double lngA = Double.parseDouble(longitudeA);
            System.out.println("LatA = "+latA);
            locationA.setLatitude(latA);
            locationA.setLongitude(lngA);
        }catch (Exception e){}


        DatabaseReference sellerRef = FirebaseDatabase.getInstance().getReference("Seller");


        sellerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    latitudeB = ds.child(studentDetails.getBook_Id()).child("Latitude").getValue().toString();
                    longitudeB = ds.child(studentDetails.getBook_Id()).child("Longitude").getValue().toString();
                    System.out.println("Latitude B = "+latitudeB);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Location locationB = new Location("point B");
        try {
            double latB = Double.parseDouble(latitudeB);
            double lngB = Double.parseDouble(longitudeB);
            System.out.println("LatB = " + latB);
            locationB.setLatitude(latB);
            locationB.setLongitude(lngB);
        }catch (Exception e){}

        try {
            float distance = locationA.distanceTo(locationB);
            holder.BookDistanceTextView.setText("Distance: " + distance + "m");
            System.out.println("Distance = " + distance);
        }catch (Exception e){}

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/"+ studentDetails.getBook_Id());
        GlideApp.with(context)
                .load(storageReference)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView StudentNameTextView;
        public TextView StudentNumberTextView;
        public TextView BookDistanceTextView;
        private Button pay, remove;
        private ImageView map;
        public ImageView img;


        public ViewHolder(View itemView) {

            super(itemView);

            StudentNameTextView = itemView.findViewById(R.id.book_number);

            StudentNumberTextView = itemView.findViewById(R.id.book_description);

            BookDistanceTextView = itemView.findViewById(R.id.book_distance);

            img = itemView.findViewById(R.id.imageView1);

            pay = itemView.findViewById(R.id.button_pay);
            remove = itemView.findViewById(R.id.button_remove);
            map = itemView.findViewById(R.id.google_maps);

            map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, maps.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}

//Sai Girish