package com.example.android_project;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ViewBook extends RecyclerView.Adapter< ViewBook.ViewHolder > {
    private List<ListBooks> list;
    private Context context;

    public  ViewBook(List<ListBooks> list , Context context){

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
       holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(lists.getImage(), 0,lists.getImage().length));
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView  Titel,Abstract,Like,Unlike,NoCopie,Price;
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



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position= getAdapterPosition();
                    ListBooks lists=list.get(position);

                    Intent Buy=new Intent(context,ToBuy.class);
                    Buy.putExtra("Abstract",lists.getAbstract());
                    Buy.putExtra("Titel",lists.getTitel());
                    Buy.putExtra("NoCopie",lists.getNoCopie());
                    Buy.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(Buy);

                }
            });
        }
    }
}
