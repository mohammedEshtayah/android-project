package com.example.android_project;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ViewBuy extends RecyclerView.Adapter< ViewBuy.ViewHolder > {
    private List<ListBooks> list;
    private Context context;

    public  ViewBuy(List<ListBooks> list , Context context){

        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewBuy.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewbuy,parent,false);

        return new ViewBuy.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBuy.ViewHolder holder, int position) {
        ListBooks lists=list.get(position);
        holder.Titel.setText(lists.getTitel());
        holder.Abstract.setText(lists.getAbstract());
        holder.Like.setText(Integer.toString(lists.getLike()));
        holder.Unlike.setText(Integer.toString(lists.getUnlike()));
        holder.NoCopie.setText(Integer.toString(lists.getNoCopie()));
        holder.imageBook.setImageBitmap(BitmapFactory.decodeByteArray(lists.getImage(), 0,lists.getImage().length));
    holder.Buy.setText("BUY   $"+lists.getPrice());}

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView  Titel,Abstract,Like,Unlike,NoCopie;
        public ImageView imageBook;
        public Button Buy;
        public DB database=new DB();

        public ViewHolder(final View itemView) {
            super(itemView);

            Titel=(TextView)itemView.findViewById(R.id.BtitelBook);
            Abstract=(TextView)itemView.findViewById(R.id.BabstractBook);
            Like=(TextView)itemView.findViewById(R.id.BnoLike);
            Unlike=(TextView)itemView.findViewById(R.id.BnoUnlike);
            NoCopie=(TextView)itemView.findViewById(R.id.BnoCopie);
            Buy=(Button)itemView.findViewById(R.id.buy);
            imageBook=(ImageView)itemView.findViewById(R.id.imageBookk);

            Buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position= getAdapterPosition();


                    ListBooks lists=list.get(position);
                    Toast.makeText(context,"buy",Toast.LENGTH_LONG).show();
                   database.db.execSQL("DELETE FROM buy WHERE name_book='"+Titel.getText().toString()+"'");
                    list.remove(position);
                   notifyItemRemoved(position);
                    notifyItemRangeChanged(position,list.size());
                }
            });
        }
    }
}
