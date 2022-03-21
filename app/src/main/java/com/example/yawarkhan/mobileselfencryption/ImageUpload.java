package com.example.yawarkhan.mobileselfencryption;

/**
 * Created by Yawar Khan on 4/28/2018.
 */
public class ImageUpload {
    public String name;
    public String url;

    public String getName(){
        return name;
    }
    public String getUrl(){
        return url;
    }

    public ImageUpload(String name, String url){
        this.name = name;
        this.url = url;
    }
    public ImageUpload(){

    }
}
