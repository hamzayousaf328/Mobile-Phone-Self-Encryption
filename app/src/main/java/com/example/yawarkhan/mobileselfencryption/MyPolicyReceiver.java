package com.example.yawarkhan.mobileselfencryption;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MyPolicyReceiver extends DeviceAdminReceiver {
    private static final String PREFIX = MyPolicyReceiver.class.getSimpleName() + ": ";
    public static final String ACTION_DISABLED = "device_admin_action_disabled";
    public static final String ACTION_ENABLED = "device_admin_action_enabled";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AdminApp.logger(PREFIX, "action: " + intent.getAction(), Log.DEBUG);
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        AdminApp.logger(PREFIX, "onEnabled()", Log.INFO);
        SharedPreferences sharedPreferences = context.getSharedPreferences("admin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("policy", "enabled");
        editor.apply();
        LocalBroadcastManager.getInstance(context).sendBroadcast(
                new Intent(ACTION_ENABLED));


    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        AdminApp.logger(PREFIX, "onDisabled()", Log.INFO);
        SharedPreferences sharedPreferences = context.getSharedPreferences("admin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("policy", "disabled");
        editor.apply();

        LocalBroadcastManager.getInstance(context).sendBroadcast(
                new Intent(ACTION_DISABLED));
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        AdminApp.logger(PREFIX, "onDisableRequested()", Log.INFO);
//        return super.onDisableRequested(context, intent);
        return null;
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        super.onPasswordSucceeded(context, intent);
        AdminApp.logger(PREFIX, "onPasswordSucceeded()", Log.INFO);
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        super.onPasswordFailed(context, intent);
        AdminApp.logger(PREFIX, "onPasswordFailed()", Log.INFO);
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        super.onPasswordChanged(context, intent);
        AdminApp.logger(PREFIX, "onPasswordChanged()", Log.INFO);
    }

    @Override
    public void onPasswordExpiring(Context context, Intent intent) {
        super.onPasswordExpiring(context, intent);
        AdminApp.logger(PREFIX, "onPasswordExpiring()", Log.INFO);
    }

    @Override
    public void onLockTaskModeEntering(Context context, Intent intent, String pkg) {
        super.onLockTaskModeEntering(context, intent, pkg);
        AdminApp.logger(PREFIX, "onLockTaskModeEntering()", Log.INFO);
    }

    @Override
    public void onLockTaskModeExiting(Context context, Intent intent) {
        super.onLockTaskModeExiting(context, intent);
        AdminApp.logger(PREFIX, "onLockTaskModeExiting()", Log.INFO);
    }

    @Override
    public void onProfileProvisioningComplete(Context context, Intent intent) {
        super.onProfileProvisioningComplete(context, intent);
        AdminApp.logger(PREFIX, "onProfileProvisioningComplete()", Log.INFO);
    }
}