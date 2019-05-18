//Rahul Gite
/***package com.example.android.s4s;

import android.content.Context;
import android.location.Location;
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
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context context;
    private ArrayList<Book> items;

    public RecyclerAdapter(Context context,ArrayList<Book> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View row = inflater.inflate(R.layout.list_item, viewGroup ,false);

        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {


        final Book book = items.get(i);

        viewHolder.bookName.setText(book.getmBookname());
        viewHolder.bookAuthor.setText(book.getmAuthorname());
        viewHolder.bookPrice.setText(book.getmPrice());
        //viewHolder.setWishlist(book.getWish());
        viewHolder.bookId.setText(book.getBook_Id());
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

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView bookName;
        TextView bookAuthor;
        TextView bookPrice;
        TextView wishlist;
        TextView bookId;
        ImageView bookImage;
        ImageView itemwish;


        public ViewHolder(View itemView) {

            super(itemView);

            bookName = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author);
            bookPrice = itemView.findViewById(R.id.book_price);
            wishlist = itemView.findViewById(R.id.text_wishlist);
            bookImage = itemView.findViewById(R.id.item_image);
            itemwish = itemView.findViewById(R.id.add_wishlist);
            bookId = itemView.findViewById(R.id.book_id);


        }
    }
}***/


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

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context context;
    List<Book> MainImageUploadInfoList;


    public RecyclerAdapter(Context context, List<Book> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Book book = MainImageUploadInfoList.get(position);

        holder.bookName.setText(book.getmBookname());
        holder.bookAuthor.setText(book.getmAuthorname());
        holder.bookPrice.setText(book.getmPrice());
        holder.bookId.setText(book.getBook_Id());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/"+ book.getBook_Id());
        GlideApp.with(context)
                .load(storageReference)
                .into(holder.bookImage);
    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public TextView bookName;
        public TextView bookAuthor;
        public TextView bookPrice;
        public TextView wishlist;
        public TextView bookId;
        public ImageView bookImage;
        public ImageView itemwish;


        public ViewHolder(View itemView) {

            super(itemView);

            bookName = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author);
            bookPrice = itemView.findViewById(R.id.book_price);
            wishlist = itemView.findViewById(R.id.text_wishlist);
            bookImage = itemView.findViewById(R.id.item_image);
            itemwish = itemView.findViewById(R.id.add_wishlist);
            bookId = itemView.findViewById(R.id.book_id);

        }
    }
}



