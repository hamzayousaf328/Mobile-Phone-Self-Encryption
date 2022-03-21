package com.example.yawarkhan.mobileselfencryption;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Yawar Khan on 2/8/2018.
 */
public class MyListAdapter extends ArrayAdapter<String> {


    String[] names;
    int[] flags;
    String[] filePath;
    String[] fileName;
    Context mContext;
    DataInputStream reader;
    DataOutputStream writer;

    public MyListAdapter(Context context, String[] fpath, String[] fname) {
        super(context, R.layout.listview_items);
        filePath = fpath;
        fileName = fname;
        mContext = context;
    }
    @Override
    public int getCount(){
        return fileName.length;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_items, parent, false);
            viewHolder.thumbnail = (ImageView)convertView.findViewById(R.id.imageViewItem);
            viewHolder.thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    enc2(filePath[position]);
                    Intent intent = new Intent();
                    intent.setAction(android.content.Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(new File("/storage/emulated/0/abc/abcde.jpg")), "image/png");
                    mContext.startActivity(intent);
                }
            });
            viewHolder.title = (TextView)convertView.findViewById(R.id.textViewItem);
            viewHolder.button = (Button)convertView.findViewById(R.id.buttonItem);
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dec2(fileName[position]);
                    File file = new File("/storage/emulated/0/EncryptedImages/"+fileName[position]);
                    file.delete();

                    Toast.makeText(getContext(), "Button was clicked"+position+fileName[position], Toast.LENGTH_SHORT).show();
                }
            });
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        //Bitmap bmp = BitmapFactory.decodeFile(filePath[position]);


        //Bitmap bmp = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile("/storage/emulated/0/abc/abcde.jpg"),64,64);
        //Bitmap bmp = BitmapFactory.decodeFile("/storage/emulated/0/abc/abcde.jpg");
        //viewHolder.thumbnail.setImageBitmap(bmp);
        //viewHolder.thumbnail.setImageResource(flags[position]);
        viewHolder.thumbnail.setImageResource(R.drawable.image_icon);
        viewHolder.title.setText(fileName[position]);

        return convertView;
    }
    static class ViewHolder{
        ImageView thumbnail;
        TextView title;
        Button button;
    }
    public void enc2(String pathIn){
        try{
            initialize(pathIn, "/storage/emulated/0/abc/abcde.jpg");
            encrypt();
            //initialize("/storage/emulated/0/Encrypted/abcde.jpg", "/storage/emulated/0/Decrypted/de.jpg");
            //encrypt();
        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage()+"NOTFOUNFEX");

        }
        catch(IOException e){
            System.out.println(e.getMessage()+"IOEX");
        }
    }


    public void dec2(String pathIn){
        try{
            initialize("/storage/emulated/0/EncryptedImages/"+pathIn, "/storage/emulated/0/DecryptedImages/"+pathIn);
            encrypt();
            MediaScannerConnection.scanFile(
                    MyListAdapter.this.getContext(),
                    new String[]{"/storage/emulated/0/DecryptedImages/"+pathIn},
                    null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.v("grokkingandroid",
                                    "file " + path + " was scanned seccessfully: " + uri);
                        }
                    });
            //initialize("/storage/emulated/0/Encrypted/abcde.jpg", "/storage/emulated/0/Decrypted/de.jpg");
            //encrypt();
        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage()+"NOTFOUNFEX");

        }
        catch(IOException e){
            System.out.println(e.getMessage()+"IOEX");
        }
    }
    public void initialize(String inputFilePath, String outputFilePath) throws FileNotFoundException{
        reader = new DataInputStream(new FileInputStream(new File(inputFilePath)));
        writer = new DataOutputStream(new FileOutputStream(new File(outputFilePath)));
    }
    public void encrypt() throws IOException {
        int readBytes = 0;
        RC4Cipher rc4Encryption = new RC4Cipher("123abcde");
        byte[] buffer = new byte[2048];
        do {
            readBytes = reader.read(buffer, 0, 2048);
            buffer = rc4Encryption.rc4(buffer);
            if (readBytes > 0) {
                writer.write(buffer, 0, readBytes);
                writer.flush();
            }
            //System.out.println(k++);

        } while (readBytes > 0);
    }
}
