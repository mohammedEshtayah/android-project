package com.example.android_project;

public class ListBooks {
    private String Titel,Abstract;
    private int Like,Unlike,NoCopie,Price;
    private byte[] Image;

    public ListBooks(String Titel,byte[] image ,String Abstract,int Like,int Unlike,int NoCopie,int price){
        this.Titel=Titel;
        this.Abstract=Abstract;
        this.Like=Like;
        this.Unlike=Unlike;
        this.NoCopie=NoCopie;
        this.Image=image;
        this.Price=price;

    }
    public String getTitel(){
        return Titel;
    }
    public String getAbstract(){
        return Abstract;
    }
    public int getLike(){
        return Like;
    }
    public int getUnlike(){
        return Unlike;
    }
    public int getNoCopie(){
        return NoCopie;
    }
    public byte[] getImage(){   return Image; }
    public int getPrice(){
        return Price;
    }

}