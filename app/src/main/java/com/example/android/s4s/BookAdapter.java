package com.example.android.s4s;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {

    private ArrayList<Book> pBooks;
    Context mContext;
    FirebaseUser currentFirebaseUser;
    StorageReference storageReference;
    private LayoutInflater mInflator;
    int k;

    public BookAdapter(Context context, ArrayList<Book> pBooks) {
        super(context,0,pBooks);
        this.mContext = context;

    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        Book local_book = getItem(position);

        TextView bookName = (TextView) listItemView.findViewById(R.id.book_title);
        bookName.setText(local_book.getmBookname());

        TextView bookAuthor = (TextView) listItemView.findViewById(R.id.book_author);
        bookAuthor.setText(local_book.getmAuthorname());

        TextView bookPrice = (TextView) listItemView.findViewById(R.id.book_price);
        bookPrice.setText(local_book.getmPrice());

        TextView wishlist = (TextView) listItemView.findViewById(R.id.text_wishlist);
        wishlist.setText(local_book.getWish());

        final ImageView bookImage = (ImageView) listItemView.findViewById(R.id.item_image);

        //not sure if "k" is position here(Comment)

//        k=position;          //(Code)
//


        //yaha par "k" me us particular book ka "index" jayega
        //ye commented h jab k mil jaye to uncomment kardena

        //(Code)

//        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        storageReference = FirebaseStorage.getInstance ().getReference ("Front page images");
//        StorageReference mstorage =  storageReference.child (currentFirebaseUser.getUid()+String.valueOf(k)+"."+"jpg");
//
//
//        try {
//
//            mstorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    String imageURL = uri.toString();
//                    Glide.with(mContext.getApplicationContext()).load(imageURL).into(bookImage);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    // Handle any errors
////                    Toast.makeText(BookAdapter.this, exception.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            });
//        } catch (NullPointerException e) {
////            Toast.makeText(BookAdapter.this, e.getMessage(), Toast.LENGTH_LONG).show();
//        }


        ///

        ImageView itemwish = (ImageView) listItemView.findViewById(R.id.add_wishlist);
        itemwish.setImageResource(local_book.getMaddtoCartId());

        ImageView ratings1 = (ImageView) listItemView.findViewById(R.id.book_ratings1);
        ratings1.setImageResource(local_book.getMgetRatingsId());


        return listItemView;
    }
}