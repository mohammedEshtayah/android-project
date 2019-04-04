package com.example.android_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ViewBook extends RecyclerView.Adapter< ViewBook.ViewHolder > {
    private List<ListBooks> list;
    private Context context;
    public DB db;

    public  ViewBook(List<ListBooks> list , Context context){
        db=new DB();
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public  ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewbooks,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBook.ViewHolder holder, int position) {
        ListBooks lists=list.get(position);

        holder.Titel.setText(lists.getTitel());
        holder.Abstract.setText(lists.getAbstract());
       holder.Like.setText(Integer.toString(lists.getLike()));
        holder.Unlike.setText(Integer.toString(lists.getUnlike()));
        holder.NoCopie.setText(Integer.toString(lists.getNoCopie()));
        holder.Price.setText("$"+Integer.toString(lists.getPrice()));
        if(lists.getImage()!=null)
       holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(lists.getImage(), 0,lists.getImage().length));
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private   sharedPreference SP  ;
        public TextView  Titel,Abstract,Like,Unlike,NoCopie,Price;
        public Button BLike,BunLike;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            Titel=(TextView)itemView.findViewById(R.id.BtitelBook);
            Abstract=(TextView)itemView.findViewById(R.id.BabstractBook);
            Like=(TextView)itemView.findViewById(R.id.noLike);
            Unlike=(TextView)itemView.findViewById(R.id.noUnlike);
            NoCopie=(TextView)itemView.findViewById(R.id.noCopie);
            imageView=(ImageView)itemView.findViewById(R.id.imageBookk);
            Price=(TextView)itemView.findViewById(R.id.Price);
            BLike=(Button) itemView.findViewById(R.id.Blike);
            BunLike=(Button) itemView.findViewById(R.id.Bunlike);

            SP=new sharedPreference(context);

BLike.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        int position= getAdapterPosition();
        ListBooks lists=list.get(position);
        db.db.execSQL("update books set NoLike="+lists.getLike()+"+1 ");
     Like.setText(Integer.toString(lists.getLike()+1));


    }
});
BunLike.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        int position= getAdapterPosition();
        ListBooks lists=list.get(position);
        db.db.execSQL("update books set NoUnlike="+lists.getLike()+"+1 ");
        Unlike.setText(Integer.toString(lists.getUnlike()+1));
    }
});
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position= getAdapterPosition();
                    ListBooks lists=list.get(position);

                    Intent intent=new Intent();
                    intent.putExtra("Abstract",lists.getAbstract());
                    intent.putExtra("Titel",lists.getTitel());
                    intent.putExtra("NoCopie",lists.getNoCopie());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("price",lists.getPrice());

                    if(SP.SP.getString("user",null).equals("Library Nablus")) {

                        intent.setSelector(new Intent(context,update_delete_books.class));
                        context.startActivity(intent);
                        Activity activity = new Activity();
                        activity.finish();
                    } else {
                        intent.setSelector(new Intent(context,ToBuy.class));
                        context.startActivity(intent);
                        Activity activity = new Activity();
                        activity.finish();
                    }
                }
            });

        }
    }
}
