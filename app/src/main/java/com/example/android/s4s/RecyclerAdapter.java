//Rahul Gite
package com.example.android.s4s;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Item> {

    Context context;
    private ArrayList<Book> items;

    public RecyclerAdapter(Context context,ArrayList<Book> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Item onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View row = inflater.inflate(R.layout.list_item, viewGroup ,false);

        return new Item(row);
    }

    @Override
    public void onBindViewHolder(@NonNull Item viewHolder, int i) {


        final Book book = items.get(i);

        viewHolder.setBookName(book.getmBookname());
        viewHolder.setBookAuthor(book.getmAuthorname());
        viewHolder.setBookPrice(book.getmPrice());
        //viewHolder.setWishlist(book.getWish());
        viewHolder.setBookId(book.getBook_Id());
        //viewHolder.setItemwish(book.getMaddtoCartId());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/"+ book.getBook_Id());
        GlideApp.with(context)
                .load(storageReference)
                .into(viewHolder.bookImage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Item extends RecyclerView.ViewHolder {

        TextView bookName;
        TextView bookAuthor;
        TextView bookPrice;
        TextView wishlist;
        TextView bookId;
        ImageView bookImage;
        ImageView itemwish;


        public Item(@NonNull View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author);
            bookPrice = itemView.findViewById(R.id.book_price);
            wishlist = itemView.findViewById(R.id.text_wishlist);
            bookImage = itemView.findViewById(R.id.item_image);
            itemwish = itemView.findViewById(R.id.add_wishlist);
            bookId = itemView.findViewById(R.id.book_id);


        }

        public void setBookName(String name) {
            bookName.setText(name);
        }

        public void setBookAuthor(String author) {
            bookAuthor.setText(author);
        }

        public void setBookPrice(String price) {
            bookPrice.setText(price);
        }

        public void setBookId(String bookid) { bookId.setText(bookid);}

        public void setWishlist(String wish) {

            wishlist.setText(wish);
        }

        public void setBookImage(int imageid) {
            bookImage.setImageResource(imageid);
        }

        public void setItemwish(int wishid) {
            itemwish.setImageResource(wishid);
        }
    }


}

//Rahul Gite
