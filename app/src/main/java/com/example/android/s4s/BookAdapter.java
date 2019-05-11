//Rahul Gite

package com.example.android.s4s;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {

    private ArrayList<Book> pBooks;
    Context mContext;
    FirebaseUser currentFirebaseUser;
    StorageReference storageReference;
    private LayoutInflater mInflator;
    // int k;
    String book_id;

    public BookAdapter(Context context, ArrayList<Book> pBooks) {
        super(context,0,pBooks);
        this.mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        Book local_book = getItem(position);

        TextView bookName = listItemView.findViewById(R.id.book_title);
        bookName.setText(local_book.getmBookname());

        TextView bookID = listItemView.findViewById(R.id.book_id);
        bookID.setText(local_book.getBook_Id());

        TextView bookAuthor = listItemView.findViewById(R.id.book_author);
        bookAuthor.setText(local_book.getmAuthorname());

        TextView bookPrice = listItemView.findViewById(R.id.book_price);
        bookPrice.setText(local_book.getmPrice());

        TextView wishlist = listItemView.findViewById(R.id.text_wishlist);
        wishlist.setText("Add");

        final ImageView bookImage = listItemView.findViewById(R.id.item_image);

        //not sure if "k" is position here(Comment)

//        k=position;          //(Code)
//


        //yaha par "k" me us particular book ka "index" jayega
        //ye commented h jab k mil jaye to uncomment kardena

        //(Code)

        book_id = local_book.getBook_Id();

        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("Front page images");
        StorageReference mstorage = storageReference.child(currentFirebaseUser.getUid() + book_id + "." + "jpg");


        try {

            mstorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String imageURL = uri.toString();
                    Glide.with(mContext.getApplicationContext()).load(imageURL).into(bookImage);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    //Handle any errors
                    // Toast.makeText(BookAdapter.this,exception.getMessage(),Toast.LENGTH_LONG).show();

                }
            });
        } catch (NullPointerException e) {
            // Toast.makeText(BookAdapter.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


        ///

        ImageView itemwish = listItemView.findViewById(R.id.add_wishlist);
        itemwish.setImageResource(R.drawable.ic_add_shopping_cart);

        ImageView ratings1 = listItemView.findViewById(R.id.book_ratings1);
        ratings1.setImageResource(R.drawable.book_ratings);

        itemwish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Wishlist.class);
                getContext().startActivity(i);
            }
        });


        return listItemView;
    }
}

//Rahul Gite