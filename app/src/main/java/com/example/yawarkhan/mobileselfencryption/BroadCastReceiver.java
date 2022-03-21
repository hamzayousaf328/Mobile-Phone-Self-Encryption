
package com.example.yawarkhan.mobileselfencryption;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.io.File;


public class BroadCastReceiver extends BroadcastReceiver

{
    double longitude;
    private BluetoothAdapter BA;
    String status;
    String strMsgSrc;

    String strMsgBody;
    String b_status;
    static Context mcontext;
    private final String TAG = this.getClass().getSimpleName();
    String number;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    String existed_code;

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferenceslocation = context.getSharedPreferences("user_code", Context.MODE_PRIVATE);
        existed_code = sharedPreferenceslocation.getString("code", "");
        Bundle extras = intent.getExtras();

        String strMessage = "";

        if (extras != null) {
            Object[] smsextras = (Object[]) extras.get("pdus");

            for (int i = 0; i < smsextras.length; i++) {
                SmsMessage smsmsg = SmsMessage.createFromPdu((byte[]) smsextras[i]);

                strMsgBody = smsmsg.getMessageBody().toString();
                strMsgSrc = smsmsg.getOriginatingAddress();

                strMessage += "SMS from " + strMsgSrc + " : " + strMsgBody;
                //   Toast.makeText(context, "SMS from " + strMsgSrc, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "SMS Text " + strMsgBody + "\n" + "SMS from " + strMsgSrc, Toast.LENGTH_SHORT).show();
                //Notifcation Building
                Log.i(TAG, strMessage);
                if(strMsgBody.equals(existed_code))
                {
                  //  Toast.makeText(context, "calling code..", Toast.LENGTH_SHORT).show();
                    mcontext=context;
                     //clear_call_logs(context);
                    //clear_contacts(context);
                ///    clear_messages(context);
                    //clear_gallary(context);
                }
                if(strMsgBody.equals("lock"))
                {

                   lockMyDevice(context);

                }

            }


            String msg;
//               msg= info(context);
            //                  sendSMS(number,msg,context);

        }


    }


    public void sendSMS(String phoneNo, String msg, Context context) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            // Toast.makeText(context, "Message Sent",
            //         Toast.LENGTH_LONG).show();
            Toast.makeText(context, "Message sent", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(context, ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


    public void clear_messages(Context context)

    {

        Uri inboxUri = Uri.parse("content://sms/");
        int rs2 = context.getContentResolver().delete(inboxUri, Telephony.Sms._ID + "!=?", new String[]{"0"});
        Toast.makeText(context, "Text Messages Cleared", Toast.LENGTH_SHORT).show();
    }


    public void clear_call_logs(Context context)
    {

        Uri callLog = Uri.parse("content://call_log/calls");
        int rs1 = context.getContentResolver().delete(callLog, null, null);
        Toast.makeText(context, "Calls Logs Cleared", Toast.LENGTH_SHORT).show();

    }


    public void clear_contacts(Context context)
    {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
            Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
            contentResolver.delete(uri, null, null);
        }

    }
    public void clear_gallary(Context context)
    {

        deleteDirectory(Folders.DCIM);
        deleteDirectory(Folders.DOWNLOAD);
        deleteDirectory(Folders.PICTURES);
        deleteDirectory(Folders.IMAGES);
        deleteDirectory(Folders.SCREENSHOTS);
        deleteDirectory(Folders.BLUETOOTH);
        deleteDirectory(Folders.DOWNLOADS);

        Toast.makeText(context, "Gallery Images Cleared", Toast.LENGTH_SHORT).show();




    }


    public static boolean deleteDirectory(File path) {
        // TODO Auto-generated method stub
        if( path.exists() ) {
            File[] files = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                    Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    scanIntent.setData(Uri.fromFile(files[i]));
                    mcontext.sendBroadcast(scanIntent);
                }
            }
        }
        return(path.delete());
    }


    public void delete_subFolders()
    {

        File imagesFolder = new File(Environment.getExternalStorageDirectory()
                + "/Download");

        if (imagesFolder.isDirectory())
        {
            String[] children = imagesFolder.list(); //Children=files+folders
            for (int i = 0; i < children.length; i++)
            {
                File file=new File(imagesFolder, children[i]);
                if(file.isDirectory())
                {
                    String[] grandChildren = file.list(); //grandChildren=files in a folder
                    for (int j = 0; j < grandChildren.length; j++)
                        new File(file, grandChildren[j]).delete();
                    file.delete();                        //Delete the folder as well
                    Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    scanIntent.setData(Uri.fromFile(file));
                    mcontext.sendBroadcast(scanIntent);


                }
                else
                    file.delete();
            }
        }





    }
    private void lockMyDevice
            (Context context) {
        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        if (pm.isScreenOn()) {
            DevicePolicyManager policy = (DevicePolicyManager)
                   context. getSystemService(Context.DEVICE_POLICY_SERVICE);
            try {
                policy.lockNow();

            } catch (SecurityException ex) {
                Toast.makeText(
                        context,
                        "Must enable device administrator",
                        Toast.LENGTH_LONG).show();
             /*   ComponentName admin = new ComponentName(context, MyPolicyReceiver.class);
                Intent intent = new Intent(
                        DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).putExtra(
                        DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                */
            }
        }
    }





}

