package com.example.yawarkhan.mobileselfencryption;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

public class BackGroundService extends Service {

        private BroadCastReceiver mSMSreceiver;
     //   private UninstallReceiver mUninstallreceiver;
        private IntentFilter mIntentFilter;
    private IntentFilter mUninstallIntentFilter;
        public BackGroundService() {
        }

        @Override
        public IBinder onBind(Intent intent) {
            // TODO: Return the communication channel to the service.
            throw new UnsupportedOperationException("Not yet implemented");
        }



    @Override
    public void onCreate() {
        super.onCreate();

        mSMSreceiver = new BroadCastReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mSMSreceiver, mIntentFilter);


        Toast.makeText(this,"Service Running",Toast.LENGTH_SHORT).show();
    }

    @Override
        public void onDestroy() {
            unregisterReceiver(mSMSreceiver);
            super.onDestroy();
        }
}
