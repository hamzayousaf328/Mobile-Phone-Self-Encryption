package com.example.yawarkhan.mobileselfencryption;

import android.os.Environment;

import java.io.File;

public class Folders {
    public static File DCIM = new File(Environment.getExternalStorageDirectory()
            + "/DCIM");
    public static File DOWNLOAD = new File(Environment.getExternalStorageDirectory()
            + "/Download");

    public static File PICTURES = new File(Environment.getExternalStorageDirectory()
            + "/Pictures");

    public static File IMAGES= new File(Environment.getExternalStorageDirectory()
            + "/Images");

    public static File SCREENSHOTS = new File(Environment.getExternalStorageDirectory()
            + "/Screenshots");

    public static File BLUETOOTH = new File(Environment.getExternalStorageDirectory()
            + "/Bluetooth");

    public static File DOWNLOADS = new File(Environment.getExternalStorageDirectory()
            + "/Downloads");


}
