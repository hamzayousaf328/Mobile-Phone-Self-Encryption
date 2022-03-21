package com.example.yawarkhan.mobileselfencryption;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GalleryActivity extends AppCompatActivity {
    String realPath[];
    TextView txtSDK;
    Button uploadImage, downloadImage;
    TextView txtUriPath,txtRealPath;
    ImageView imageView;
    DataInputStream reader;
    DataOutputStream writer;
    String path;
    private Uri[] fileUriArray;
    String filePathUriToString;
    ProgressDialog progressDialog;

    ListView lv;
    MyListAdapter listAdapter;


    private String[] imageListServer;


    File file;
    File[] listFile;
    String[] filePathStrings;
    String[] fileNameStrings;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    final String databasePathImages="Encrypted_Images";
    String[] countryNames = {"Greece","Spain"};
    int[] countryFlags = {R.drawable.flag_1, R.drawable.flag_2};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference(databasePathImages);
        progressDialog=new ProgressDialog(GalleryActivity.this);







        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                //i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 1);

            }
        });
        uploadImage = (Button) findViewById(R.id.uploadImages);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageNamesFromDatabase(1);


            }
        });
        downloadImage = (Button)findViewById(R.id.downloadImages);
        downloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageNamesFromDatabase(2);
            }
        });

        file = new File(Environment.getExternalStorageDirectory() + File.separator + "EncryptedImages");
        file.mkdirs();
        if (file.isDirectory()) {
            listFile = file.listFiles();
            if(listFile == null){
                filePathStrings = new String[0];
                fileNameStrings = new String[0];
                fileUriArray = new Uri[0];

                for (int i = 0; i < 0; i++) {
                    filePathStrings[i] = listFile[i].getAbsolutePath();
                    fileNameStrings[i] = listFile[i].getName();
                    fileUriArray[i] = Uri.fromFile(listFile[i]);
                }
            }
            else {
                filePathStrings = new String[listFile.length];
                fileNameStrings = new String[listFile.length];
                fileUriArray = new Uri[listFile.length];

                for (int i = 0; i < listFile.length; i++) {
                    filePathStrings[i] = listFile[i].getAbsolutePath();
                    fileNameStrings[i] = listFile[i].getName();
                    fileUriArray[i] = Uri.fromFile(listFile[i]);
                }
            }
        }


        //String[] empty1 = {};
        //myTask mt = new myTask();
        //mt.execute();

        //lv = (ListView)findViewById(R.id.listView1);
        //listAdapter = new MyListAdapter(GalleryActivity.this, filePathStrings, fileNameStrings);
        //lv.setAdapter(listAdapter);
        //generateListContent();
        //lv.setAdapter(new MyListAdapter(this, R.layout.listview_items, data));

        Button encrypt = (Button) findViewById(R.id.enc);
        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(realPath[0] == null){
                    Toast.makeText(GalleryActivity.this, "please choose first", Toast.LENGTH_SHORT).show();
                }
                else{
                    enc3(realPath);
                    //Toast.makeText(GalleryActivity.this, realPath[0]+"", Toast.LENGTH_SHORT).show();
                    File a = new File(realPath[0]);
                    a.delete();
                    Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    scanIntent.setData(Uri.fromFile(a));
                    sendBroadcast(scanIntent);
                }

            }
        });
        ui();

    }

    public boolean checkNamesOnServer(String name){
        boolean flag = false;
        for(int i = 0; i < imageListServer.length; i++){
            //System.out.println("FileName: "+name);
            //System.out.println("FileNameServer: "+imageListServer[i]);
            if(name.equals(imageListServer[i])){
                flag = true;
            }
        }
        System.out.println(flag);
        return flag;
    }
    public boolean checkNamesOnPhone(String name){
        boolean flag = false;
        for(int i = 0; i < fileNameStrings.length; i++){
            //System.out.println("FileName: "+name);
           // System.out.println("FileNameServer: "+fileNameStrings[i]);
            if(name.equals(fileNameStrings[i])){
                flag = true;
            }
        }

        return flag;
    }

    public void getImageNamesFromDatabase(final int choice){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imageListServer = new String[(int)dataSnapshot.getChildrenCount()];
                int i = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //ImageUpload class require default constructor
                    ImageUpload img = snapshot.getValue(ImageUpload.class);
                    imageListServer[i] = img.getName();
                    //Toast.makeText(GalleryActivity.this, imageListServer[i]+"Length"+imageListServer.length, Toast.LENGTH_SHORT).show();
                    i++;
                }
                i = 0;
                System.out.println("GOGOGO");
                if(choice == 1){
                    for(int j = 0; j < fileUriArray.length; j++){

                        boolean isPresentOnServer = checkNamesOnServer(fileNameStrings[j]);
                        if(isPresentOnServer == false){
                            System.out.println("Not Present: "+fileNameStrings[j]);
                            uploadImage(fileNameStrings[j], fileUriArray[j]);
                        }
                    }
                }
                if(choice == 2){
                    for(int j = 0; j < imageListServer.length; j++){

                        boolean isPresentOnPhone = checkNamesOnPhone(imageListServer[j]);
                        if(isPresentOnPhone == false){
                            System.out.println("Not Present: "+imageListServer[j]);
                            try {
                                downloadImage(imageListServer[j]);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void ui(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lv = (ListView)findViewById(R.id.listView1);
                        listAdapter = new MyListAdapter(GalleryActivity.this, filePathStrings, fileNameStrings);
                        lv.setAdapter(listAdapter);
                    }
                });
            }
        }).start();

    }

    public void generateListContent(){
        for(int i=0; i<55; i++){
            //data.add("This is row number "+i);
        }
    }
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri photoUri = data.getData();
            if (photoUri != null) {
                path = photoUri.toString();
            }
        }
    }*/
    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {

        if(resCode == Activity.RESULT_OK && data != null){
            if(data.getClipData() == null){
                realPath = new String[1];
                // SDK < API11
                if (Build.VERSION.SDK_INT < 11)
                    realPath[0] = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());

                    // SDK >= 11 && SDK < 19
                else if (Build.VERSION.SDK_INT < 19)
                    realPath[0] = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());

                    // SDK > 19 (Android 4.4)
                else
                    realPath[0] = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
            }
            else{
                realPath = new String[data.getClipData().getItemCount()];
                for(int i = 0; i < data.getClipData().getItemCount(); i++) {
                    // SDK < API11
                    if (Build.VERSION.SDK_INT < 11)
                        realPath[i] = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getClipData().getItemAt(i).getUri());

                        // SDK >= 11 && SDK < 19
                    else if (Build.VERSION.SDK_INT < 19)
                        realPath[i] = RealPathUtil.getRealPathFromURI_API11to18(this, data.getClipData().getItemAt(i).getUri());

                        // SDK > 19 (Android 4.4)
                    else
                        realPath[i] = RealPathUtil.getRealPathFromURI_API19(this, data.getClipData().getItemAt(i).getUri());
                }
            }




            //setTextViews(Build.VERSION.SDK_INT, data.getData().getPath(),realPath);
            //enc();

        }

    }
    private void uploadImage(final String fileName, final Uri filePath) {
        if(filePath != null)
        {

            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("Encrypted_Images/"+ fileName);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImageUpload imageUpload = new ImageUpload(fileName, filePath.toString());
                            String imageId=databaseReference.push().getKey();
                            databaseReference.child(imageId).setValue(imageUpload);

                            progressDialog.dismiss();
                            Toast.makeText(GalleryActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(GalleryActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
    private void downloadImage(final String fileName) throws IOException {
        progressDialog.setTitle("Downloading...");
        progressDialog.show();
        StorageReference ref = storageReference.child("Encrypted_Images/"+ fileName);
        final File storagePath = new File("/storage/emulated/0/EncryptedImages/"+fileName);
        //final File localFile = File.createTempFile("sam", null , storagePath);
        ref.getFile(storagePath).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(GalleryActivity.this, "Downloaded: "+fileName, Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(GalleryActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                        .getTotalByteCount());
                progressDialog.setMessage("Downloaded "+(int)progress+"%");
            }
        });
    }




    public void enc3(String[] pathIn){
        for(int i=0; i<pathIn.length;i++){
            File file1 = new File(pathIn[i]);
            String filename = file1.getName();
            try{
                initialize(pathIn[i], "/storage/emulated/0/EncryptedImages/"+filename);
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
    public void abctask(){
        lv = (ListView)findViewById(R.id.listView1);
        listAdapter = new MyListAdapter(GalleryActivity.this, filePathStrings, fileNameStrings);
        lv.setAdapter(listAdapter);
    }


}
