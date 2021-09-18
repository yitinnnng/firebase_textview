package com.example.firebase_textview;

import android.content.Context;

public class TextString {
    private String z1;
    private String z2;
    public  Context context;

    public TextString (){}
    public TextString (){}    
    
    public TextString(Context context){
        this.context = context;
    }
    public TextString(String text_1, String text_2){
        this.z1 = text_1;
        this.z1 = text_2;

    }

    public  String getZ1(){
        return z1;
    }
    public  String getZ2(){
        return z2;
    }
}
